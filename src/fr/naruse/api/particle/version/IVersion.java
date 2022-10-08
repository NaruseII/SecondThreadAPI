package fr.naruse.api.particle.version;


import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import fr.naruse.api.particle.version.particle.IEnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IVersion {

    void sendPacket(Player player, Object packet);

    IEnumParticle getEnumParticle();

    Particle buildParticle(Location location, IParticle particle, float xOffset, float yOffset, float zOffset, int count, float speed);

    default Particle buildParticle(Location location, IParticle particle, float offsetX, float offsetY, float offsetZ, int amount){
        return buildParticle(location, particle, offsetX, offsetY, offsetZ, amount, 0f);
    }

}
