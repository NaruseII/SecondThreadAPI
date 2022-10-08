package fr.naruse.api.v1_13;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import fr.naruse.api.particle.version.IVersion;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.Packet;
import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Version1_13 implements IVersion {

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
                (net.minecraft.server.v1_13_R2.ParticleType) particle.getEnumParticle(), true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                xOffset, yOffset, zOffset,
                speed, count);
        return new Particle(packet, location);
    }


}
