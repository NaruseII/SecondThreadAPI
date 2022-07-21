package fr.naruse.api;

import com.google.common.collect.Maps;
import fr.naruse.api.async.ThreadGlobal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.Map;

public class ScoreboardSign<T extends JavaPlugin> {

    private final Map<Integer, Score> scoreByIndex = Maps.newHashMap();

    protected final T pl;
    protected final Scoreboard scoreboard;
    protected final Objective objective;

    private boolean onlyOneScore = true;

    public ScoreboardSign(T pl, DisplaySlot displaySlot, String displayName) {
        this.pl = pl;

        this.scoreboard = pl.getServer().getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(displayName, "dummy");
        this.objective.setDisplaySlot(displaySlot);
    }

    public void disableOnlyOneScore(){
        this.onlyOneScore = false;
    }

    public void onReload() {

    }


    public void apply(Player p){
        p.setScoreboard(this.scoreboard);
    }

    public void reload(){
        if(!Bukkit.isPrimaryThread()){
            ThreadGlobal.runSync(() -> reload());
            return;
        }

        this.onReload();
    }

    public void removeScore(int index){
        if(scoreByIndex.containsKey(index)){
            Score score = scoreByIndex.get(index);
            scoreboard.resetScores(score.getEntry());
        }
    }

    public void setScore(String line, int index){
        this.setScore(line, index, false);
    }

    public void setScore(String line, int index, boolean center){
        Score score;
        if(this.onlyOneScore && scoreByIndex.containsKey(index)){
            score = scoreByIndex.get(index);
            if(score.getEntry().equals(line)){
                return;
            }
            scoreboard.resetScores(score.getEntry());
        }

        if(center){
            int maxLength = line.length();
            for (Score value : scoreByIndex.values()) {
                if(value.getEntry().length() > maxLength){
                    maxLength = value.getEntry().trim().length();
                }
            }
            if(maxLength > line.length()){
                int spaceToAdd = (int) ((maxLength-line.length())/2*1.2);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < spaceToAdd; i++) {
                    sb.append(' ');
                }
                line = sb+line+sb;
            }
        }

        score = objective.getScore(line);
        score.setScore(index);
        scoreByIndex.put(index, score);
    }

    public void addScore(String line){
        for (int i = 0; i < 15; i++) {
            if(!scoreByIndex.containsKey(i)){
                this.setScore(line, i);
            }
        }
    }

    public void clearLines(){
        for (String entry : this.scoreboard.getEntries()) {
            this.scoreboard.resetScores(entry);
        }
        this.scoreByIndex.clear();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }
}
