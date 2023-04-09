package fr.naruse.api.particle;

import fr.naruse.api.MathUtils;
import fr.naruse.api.particle.version.VersionManager;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Particle {

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

    public void toSome(List<Player> playerSet) {
        playerSet.forEach(player -> this.sendPacket(player));
    }

    public Object packet() {
        return packet;
    }

    public Location location() {
        return location;
    }

    private void sendPacket(Player player){
        VersionManager.getVersion().sendPacket(player, this.packet);
    }



    public static Particle buildParticle(Location location, IParticle particle, float xOffset, float yOffset, float zOffset, int count, float speed) {
        return VersionManager.getVersion().buildParticle(location, particle, xOffset, yOffset, zOffset, count, speed);
    }

    public static Particle buildParticle(Location location, IParticle particle, float offsetX, float offsetY, float offsetZ, int amount){
        return VersionManager.getVersion().buildParticle(location, particle, offsetX, offsetY, offsetZ, amount, 0f);
    }

    public static IEnumParticle getEnumParticle(){
        return VersionManager.getVersion().getEnumParticle();
    }

}
