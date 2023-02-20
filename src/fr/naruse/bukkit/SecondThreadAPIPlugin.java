package fr.naruse.bukkit;

import fr.naruse.api.APIInit;
import fr.naruse.api.async.CollectionManager;
import fr.naruse.api.updater.PluginUpdater;
import org.bukkit.plugin.java.JavaPlugin;

public class SecondThreadAPIPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        APIInit.init(this);
        new Metrics(this, 17784);

        this.getServer().getScheduler().runTaskTimerAsynchronously(this, () -> this.checkPlugins(), 20*60*60, 20*60*20);
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.addLater(() -> this.checkPlugins(), 1000*10);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        APIInit.shutdown();
    }


    private void checkPlugins(){
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(() -> {
            PluginUpdater.checkSpleef(this);
            PluginUpdater.checkDAC(this);
        });
    }
}
