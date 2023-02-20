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

        this.getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            System.out.println("checking");
            CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(() -> {
                PluginUpdater.checkSpleef(this);
                PluginUpdater.checkDAC(this);
            });
        }, 20*10, 20*10);//20*60*60, 20*60*20);

    }

    @Override
    public void onDisable() {
        super.onDisable();

        APIInit.shutdown();
    }
}
