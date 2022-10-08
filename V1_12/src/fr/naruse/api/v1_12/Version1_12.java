package fr.naruse.api.v1_12;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import fr.naruse.api.particle.version.IVersion;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Version1_12 implements IVersion {

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
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles((net.minecraft.server.v1_12_R1.EnumParticle) particle.getEnumParticle(), true,
                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                xOffset, yOffset, zOffset,
                speed, count,
                new int[0]);
        return new Particle(packet, location);
    }


}
