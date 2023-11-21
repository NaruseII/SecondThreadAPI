package fr.naruse.api.updater;

import com.rylinaux.plugman.PlugMan;
import com.rylinaux.plugman.api.PlugManAPI;
import fr.naruse.api.async.CollectionManager;
import fr.naruse.api.async.ThreadGlobal;
import fr.naruse.bukkit.SecondThreadAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class PluginUpdater {

    public static boolean downloadPlugManX(JavaPlugin javaPlugin) {
        String name = "PlugManX";
        Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
        if(plugin != null) {
            return false;
        }

        File file = new File(javaPlugin.getDataFolder().getParentFile(), name+".jar");
        if(!downloadFile("https://github.com/NaruseII/SecondThreadAPI/blob/master/out/artifacts/NaruseSpigotAPI/"+name+".jar?raw=true", file)){
            javaPlugin.getLogger().severe("Could not check online dependencies! "+name);
            return false;
        }

        CountDownLatch latch = new CountDownLatch(1);
        ThreadGlobal.runSync(() -> {
            try {
                Plugin pl = Bukkit.getPluginManager().loadPlugin(file);
                Bukkit.getPluginManager().enablePlugin(pl);

                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

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
            javaPlugin.getLogger().severe("Could not check online dependencies!");
            return;
        }else if(!version.equalsIgnoreCase(plugin.getDescription().getVersion())){
            ThreadGlobal.runSync(() -> {
                PlugMan.getInstance().getPluginUtil().disable(plugin);
                PlugMan.getInstance().getPluginUtil().unload(plugin);

                CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(() -> {
                    File file = new File(javaPlugin.getDataFolder().getParentFile(), pluginName+".jar");
                    if(file.exists()){
                        file.delete();
                    }
                    if(!downloadFile("https://github.com/NaruseII/"+gitHubName+"/blob/master/out/artifacts/"+gitHubName+"/"+pluginName+".jar?raw=true", file)){
                        javaPlugin.getLogger().severe("Unable to download "+pluginName);
                        return;
                    }

                    ThreadGlobal.runSync(() -> PlugMan.getInstance().getPluginUtil().load(pluginName));
                });
            });
        }
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