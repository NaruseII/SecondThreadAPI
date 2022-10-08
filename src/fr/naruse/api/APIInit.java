package fr.naruse.api;

import fr.naruse.api.async.CollectionManager;
import fr.naruse.api.async.ThreadGlobal;
import fr.naruse.api.particle.version.VersionManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class APIInit implements Listener {

    private static boolean addEntitiesInAsyncList = true;

    public static void init(JavaPlugin plugin){
        ThreadGlobal.launch(plugin);
        plugin.getServer().getPluginManager().registerEvents(new APIInit(), plugin);

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                CollectionManager.ASYNC_ENTITY_LIST.add(entity);
            }
        }

        VersionManager.init(plugin);
    }

    public static void shutdown(){
        ThreadGlobal.shutdown();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawn(EntitySpawnEvent e){
        if(e.getEntity() instanceof Player || e.isCancelled() || !addEntitiesInAsyncList){
            return;
        }

        CollectionManager.ASYNC_ENTITY_LIST.add(e.getEntity());
    }

    @EventHandler
    public void die(EntityDeathEvent e){
        if(e.getEntity() instanceof Player || !addEntitiesInAsyncList){
            return;
        }

        CollectionManager.ASYNC_ENTITY_LIST.remove(e.getEntity());
    }

    public static void disableEntityAsyncListAdd(){
        addEntitiesInAsyncList = false;
    }

}
