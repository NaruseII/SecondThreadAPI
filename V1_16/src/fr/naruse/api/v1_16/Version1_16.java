package fr.naruse.api.v1_16;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import fr.naruse.api.particle.version.IVersion;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Version1_16 implements IVersion {

    private final EnumParticle enumParticle = new EnumParticle();

    @Override
    public void sendPacket(Player player, Object packet) {
        if(!(packet instanceof Packet<?>)){
            System.err.println("Object packet isn't a valid packet");
            return;
        }

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.playerConnection.sendPacket((Packet<?>) packet);
    }

    @Override
    public IEnumParticle getEnumParticle() {
        return this.enumParticle;
    }

    @Override
    public Particle buildParticle(Location location, IParticle particle, float xOffset, float yOffset, float zOffset, int count, float speed) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                (net.minecraft.server.v1_16_R3.ParticleType) particle.getEnumParticle(), true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                xOffset, yOffset, zOffset,
                speed, count);
        return new Particle(packet, location);
    }

    @Override
    public void moveEntityToDestination(Entity entity, Location destination, double speed) {
        net.minecraft.server.v1_16_R3.Entity e = ((CraftEntity) entity).getHandle();
        EntityInsentient entityInsentient = (EntityInsentient) e;
        NavigationAbstract navigationAbstract = entityInsentient.getNavigation();
        PathEntity pathEntity = navigationAbstract.a(destination.getX(), destination.getY(), destination.getZ(), 1);
        if(pathEntity != null){
            navigationAbstract.a(pathEntity, speed);
        }
    }

}
