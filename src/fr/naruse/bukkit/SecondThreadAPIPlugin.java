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

        CollectionManager.SECOND_THREAD_RUNNABLE_SET.addLater(() -> PluginUpdater.checkSpleef(this), 1000);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        APIInit.shutdown();
    }
}
