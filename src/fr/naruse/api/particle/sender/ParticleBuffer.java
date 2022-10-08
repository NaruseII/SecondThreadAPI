package fr.naruse.api.particle.sender;

import com.google.common.collect.Sets;
import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import org.bukkit.Location;

import java.util.Set;

public class ParticleBuffer {

    private Set<Particle> particleSet = Sets.newHashSet();

    public ParticleBuffer buildParticle(Location location, IParticle particle, float xOffset, float yOffset, float zOffset, int count, float speed){
        this.particleSet.add(Particle.buildParticle(location, particle, xOffset, yOffset, zOffset, count, speed));
        return this;
    }

    public ParticleBuffer buildParticle(Location location, IParticle particle, float offsetX, float offsetY, float offsetZ, int amount){
        this.particleSet.add(Particle.buildParticle(location, particle, offsetX, offsetY, offsetZ, amount));
        return this;
    }

    public void send(ParticleSender sender){
        for (Particle particle : this.particleSet) {
            sender.send(particle);
        }
    }

}