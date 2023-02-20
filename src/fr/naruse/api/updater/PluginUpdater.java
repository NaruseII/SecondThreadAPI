package fr.naruse.api.updater;

import fr.naruse.api.async.ThreadGlobal;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class PluginUpdater {

    public static void checkSpleef(JavaPlugin javaPlugin){
        checkPlugin(javaPlugin, "Spleef", "Spleef");
    }

    public static void checkDAC(JavaPlugin javaPlugin){
        checkPlugin(javaPlugin, "DeACoudre", "DeACoudre");
    }

    private static void checkPlugin(JavaPlugin javaPlugin, String pluginName, String gitHubName){
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if(plugin == null) {
            return;
        }

        String version = getVersion("https://raw.githubusercontent.com/NaruseII/"+gitHubName+"/master/src/plugin.yml");
        if(version.equals("error")){
            javaPlugin.getLogger().severe("Could not check online dependecies!");
            return;
        }else if(!version.equalsIgnoreCase(plugin.getDescription().getVersion())){
            ThreadGlobal.runSync(() -> Bukkit.getPluginManager().disablePlugin(plugin));
        }else{
            return;
        }

        File file = new File(javaPlugin.getDataFolder().getParentFile(), pluginName+".jar");
        if(file.exists()){
            file.delete();
        }
        if(!downloadFile("https://github.com/NaruseII/"+gitHubName+"/blob/master/out/artifacts/"+gitHubName+"/"+pluginName+".jar?raw=true", file)){
            javaPlugin.getLogger().severe("Unable to download "+pluginName);
            return;
        }

        ThreadGlobal.runSync(() -> {
            try {
                Plugin pl = Bukkit.getPluginManager().loadPlugin(file);
                Bukkit.getPluginManager().enablePlugin(pl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static String getVersion(String urlString){
        try{
            URL url = new URL(urlString);
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                if(line.startsWith("version")){
                    return line.split(": ")[1];
                }
            }
        }catch (Exception e){ }
        return "error";
    }

    private static boolean downloadFile(String host, File dest) {
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(host).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(dest);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
