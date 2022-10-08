package fr.naruse.api.particle.version;

import org.bukkit.plugin.java.JavaPlugin;

public class VersionManager {

    private static IVersion version;

    public static void init(JavaPlugin pl) {
        MinecraftVersion.setVersion(pl.getServer().getBukkitVersion());
        version = MinecraftVersion.getCurrentVersion().build();
        pl.getLogger().info("Current MinecraftVersion is "+MinecraftVersion.getCurrentVersion());
    }

    public static IVersion getVersion() {
        return version;
    }
}
