package fr.naruse.bukkit;

import fr.naruse.api.main.APIInit;
import org.bukkit.plugin.java.JavaPlugin;

public class SecondThreadAPIPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        APIInit.init(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        APIInit.shutdown();
    }
}
