package fr.naruse.api;

import com.google.common.collect.Sets;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class ParticleUtils {

    private static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
    public static final double DOUBLE_VERSION;
    static {
        String version = VERSION.replace("v", "").replace("R1", "").replace("R2", "").replace("R3", "").replace("R4", "").replace("_", ".");
        DOUBLE_VERSION = Double.valueOf(version.trim().substring(0, version.length()-2));
    }

    public static Particle buildParticle(Location location, Object particle, float xOffset, float yOffset, float zOffset, int count, float speed){
        try{
            Object packet;
            if(DOUBLE_VERSION >= 1.13){
                Constructor constructor;

                if(DOUBLE_VERSION >= 1.17){
                    constructor = getNMSClass("network.protocol.game.PacketPlayOutWorldParticles").getConstructor(getNMSClass("core.particles.ParticleParam"), boolean.class, double.class,
                            double.class, double.class, float.class, float.class, float.class, float.class, int.class);
                }else{
                    constructor = getNMSClass("PacketPlayOutWorldParticles").getConstructor(getNMSClass("ParticleParam"), boolean.class, double.class,
                            double.class, double.class, float.class, float.class, float.class, float.class, int.class);
                }

                packet = constructor.newInstance(particle, true, location.getX(), location.getY(), location.getZ(), xOffset, yOffset, zOffset, speed, count);
            }else{
                Constructor constructor = getNMSClass("PacketPlayOutWorldParticles").getConstructor(getNMSClass("EnumParticle"), boolean.class, float.class,
                        float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
                packet = constructor.newInstance(particle, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), xOffset, yOffset, zOffset, speed, count, new int[0]);
            }

            return new Particle(packet, location);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static Particle buildParticle(Location location, Object particle, float offsetX, float offsetY, float offsetZ, int amount){
        return buildParticle(location, particle, offsetX, offsetY, offsetZ, amount, 0f);
    }

    private static Class<?> getNMSClass(String nmsClassString) {
        try{
            if(DOUBLE_VERSION < 1.17){
                String name = "net.minecraft.server." + VERSION + nmsClassString;
                Class<?> nmsClass = Class.forName(name);
                return nmsClass;
            }else{
                String name = "net.minecraft." + nmsClassString;
                Class<?> nmsClass = Class.forName(name);
                return nmsClass;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromName(String particleName) {
        try {
            Class particleClass;
            if(DOUBLE_VERSION >= 1.13){
                if(DOUBLE_VERSION >= 1.17){
                    particleClass = getNMSClass("core.particles.Particles");
                }else{
                    particleClass = getNMSClass("Particles");
                }
            }else{
                particleClass = getNMSClass("EnumParticle");
            }

            if(DOUBLE_VERSION >= 1.17) {
                Class clazz = Class.forName("fr.naruse.api.v1_17.ParticleType");
                Map<String, String> map = (Map<String, String>) clazz.getDeclaredField("FIELD_NAME_FOR_PARTICLE").get(null);

                if(map.containsKey(particleName)){
                    particleName = map.get(particleName);
                }
            }else if(DOUBLE_VERSION > 1.13) {
                switch (particleName){
                    case "EXPLOSION_LARGE": particleName = "EXPLOSION"; break;
                    case "SMOKE_LARGE": particleName =  "LARGE_SMOKE"; break;
                    case "SMOKE_NORMAL": particleName =  "SMOKE"; break;
                    case "EXPLOSION_HUGE": particleName =  "EXPLOSION_EMITTER"; break;
                    case "TOWN_AURA": particleName =  "TOTEM_OF_UNDYING"; break;
                    case "SPELL_WITCH": particleName =  "WITCH"; break;
                }
            }

            Field field = particleClass.getDeclaredField(particleName);
            field.setAccessible(true);
            return field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final class Particle {
        private final Object packet;
        private final Location location;

        public Particle(Object packet, Location location) {
            this.packet = packet;
            this.location = location;
        }

        public void toAll() {
            Bukkit.getOnlinePlayers().forEach(player -> this.sendPacket(player));
        }

        public void toNearbyFifty() {
            this.toNearby(50, 50, 50);
        }

        public void toNearby(int x, int y, int z) {
            MathUtils.getNearbyPlayers(location, x, y, z).forEach(player -> this.sendPacket(player));
        }

        public void toOne(Player player) {
            this.sendPacket(player);
        }

        public void toSome(Player... players) {
            Arrays.stream(players).forEach(player -> this.sendPacket(player));
        }

        public void toSome(Set<Player> playerSet) {
            playerSet.forEach(player -> this.sendPacket(player));
        }

        public Object packet() {
            return packet;
        }

        public Location location() {
            return location;
        }

        private void sendPacket(Player p){
            try{
                Object craftPlayer = p.getClass().getMethod("getHandle").invoke(p);
                Object connection = craftPlayer.getClass().getField("playerConnection").get(craftPlayer);
                connection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(connection, this.packet);
            }catch (Exception e){
                try{
                    Object craftPlayer = p.getClass().getMethod("getHandle").invoke(p);
                    Object connection = craftPlayer.getClass().getField("b").get(craftPlayer);
                    if(DOUBLE_VERSION >= 1.18){
                        connection.getClass().getMethod("a", getNMSClass("network.protocol.Packet")).invoke(connection, this.packet);
                    }else{
                        connection.getClass().getMethod("sendPacket", getNMSClass("network.protocol.Packet")).invoke(connection, this.packet);
                    }
                }catch (Exception ee){
                    ee.printStackTrace();
                }
            }
        }
    }

    public static final class ParticleSender<E> {
        private final SendType sendType;
        private final Object[] objects;

        public ParticleSender(SendType sendType, Object... objects) {
            this.sendType = sendType;
            this.objects = objects;
        }

        public static ParticleSender buildToAll() {
            return new ParticleSender(SendType.TO_ALL);
        }

        public static ParticleSender buildToNearby(int x, int y, int z) {
            return new ParticleSender(SendType.TO_NEARBY, x, y, z);
        }

        public static ParticleSender buildToNearbyFifty() {
            return new ParticleSender(SendType.TO_NEARBY, 50, 50, 50);
        }

        public static ParticleSender buildToOne(Player player) {
            return new ParticleSender(SendType.TO_ONE, player);
        }

        public static ParticleSender buildToSome(Set<Player> set) {
            return new ParticleSender(SendType.TO_SOME_SET, set);
        }

        public static ParticleSender buildToSome(Player... players) {
            return new ParticleSender(SendType.TO_SOME_ARRAY, players);
        }

        public void send(Particle particle) {
            switch (sendType) {
                case TO_ALL:
                    particle.toAll();
                    break;
                case TO_ONE:
                    particle.toOne((Player) objects[0]);
                    break;
                case TO_NEARBY:
                    particle.toNearby((int) objects[0], (int) objects[1], (int) objects[2]);
                    break;
                case TO_SOME_SET:
                    particle.toSome((Set<Player>) objects[0]);
                    break;
                case TO_SOME_ARRAY:
                    particle.toSome((Player[]) objects[0]);
                    break;
            }
        }

        public SendType sendType() {
            return sendType;
        }

        public Object[] objects() {
            return objects;
        }

    }

    public static class Buffer {

        private Set<Particle> particleSet = Sets.newHashSet();

        public Buffer buildParticle(Location location, Object particle, float xOffset, float yOffset, float zOffset, int count, float speed){
            this.particleSet.add(ParticleUtils.buildParticle(location, particle, xOffset, yOffset, zOffset, count, speed));
            return this;
        }

        public Buffer buildParticle(Location location, Object particle, float offsetX, float offsetY, float offsetZ, int amount){
            this.particleSet.add(ParticleUtils.buildParticle(location, particle, offsetX, offsetY, offsetZ, amount));
            return this;
        }

        public void send(ParticleSender sender){
            for (Particle particle : this.particleSet) {
                sender.send(particle);
            }
        }

    }

    public enum SendType {

        TO_ALL,
        TO_NEARBY,
        TO_ONE,
        TO_SOME_SET,
        TO_SOME_ARRAY

    }

}
