package fr.naruse.api.v1_19;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import fr.naruse.api.particle.version.IVersion;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Version1_19 implements IVersion {

    private final EnumParticle enumParticle = new EnumParticle();

    @Override
    public void sendPacket(Player player, Object packet) {
        if(!(packet instanceof Packet<?>)){
            System.err.println("Object packet isn't a valid packet");
            return;
        }

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        entityPlayer.b.a((Packet<?>) packet);
    }

    @Override
    public IEnumParticle getEnumParticle() {
        return this.enumParticle;
    }

    @Override
    public Particle buildParticle(Location location, IParticle particle, float xOffset, float yOffset, float zOffset, int count, float speed) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                (ParticleType) particle.getEnumParticle(), true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                xOffset, yOffset, zOffset,
                speed, count);
        return new Particle(packet, location);
    }


}
