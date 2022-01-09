package fr.naruse.api;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FireworkUtils {

    public static void build(JavaPlugin javaPlugin, Location location, int duration) {
        new FireWorks(location, duration).runTaskTimer(javaPlugin, 0L, 5L);
    }

    public static void build(JavaPlugin javaPlugin, Player player, int duration) {
        build(javaPlugin, player.getLocation(), duration);
    }

    public static class FireWorks extends BukkitRunnable {
        private final Random random = new Random();
        private final Location location;
        private int duration;

        FireWorks(Location location, int duration) {
            this.location = location.clone().add(0, 2, 0);
            this.duration = duration;
        }

        @Override
        public void run() {
            if (this.duration < 0) {
                this.cancel();
            }

            this.spawnFirework(this.location);
            this.duration--;
        }

        private void spawnFirework(Location location) {
            Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            fwm.addEffect(FireworkEffect.builder()
                    .flicker(this.random.nextBoolean())
                    .withColor(this.getColor(this.random.nextInt(17)))
                    .withFade(this.getColor(this.random.nextInt(17)))
                    .with(this.getEffectType(this.random.nextInt(5)))
                    .trail(this.random.nextBoolean()).build());

            fwm.setPower(this.random.nextInt(1) + 1);
            fw.setFireworkMeta(fwm);
        }

        private FireworkEffect.Type getEffectType(int nbt) {
            switch (nbt) {
                case 0:
                    return FireworkEffect.Type.BALL;
                case 1:
                    return FireworkEffect.Type.BALL_LARGE;
                case 2:
                    return FireworkEffect.Type.STAR;
                case 3:
                    return FireworkEffect.Type.CREEPER;
                case 4:
                    return FireworkEffect.Type.BURST;
                default:
                    throw new IllegalArgumentException("Unknown effect type " + nbt);
            }
        }

        private Color getColor(int i) {
            Color c;

            switch (i) {
                case 1:
                    c = Color.AQUA;
                    break;
                case 2:
                    c = Color.BLACK;
                    break;
                case 3:
                    c = Color.BLUE;
                    break;
                case 4:
                    c = Color.FUCHSIA;
                    break;
                case 5:
                    c = Color.GRAY;
                    break;
                case 6:
                    c = Color.GREEN;
                    break;
                case 7:
                    c = Color.LIME;
                    break;
                case 8:
                    c = Color.MAROON;
                    break;
                case 9:
                    c = Color.NAVY;
                    break;
                case 10:
                    c = Color.OLIVE;
                    break;
                case 11:
                    c = Color.ORANGE;
                    break;
                case 12:
                    c = Color.PURPLE;
                    break;
                case 13:
                    c = Color.RED;
                    break;
                case 14:
                    c = Color.SILVER;
                    break;
                case 15:
                    c = Color.TEAL;
                    break;
                case 16:
                    c = Color.WHITE;
                    break;
                default:
                    c = Color.YELLOW;
            }

            return c;
        }
    }
}

