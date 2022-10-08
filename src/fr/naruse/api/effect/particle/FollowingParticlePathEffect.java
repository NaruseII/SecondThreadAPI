package fr.naruse.api.effect.particle;

import fr.naruse.api.async.CollectionManager;
import org.bukkit.Location;

import java.util.List;

public class FollowingParticlePathEffect {

    private List<Location> locations;
    private final FollowingParticleEffect[] particles;
    private int currentIndex;
    private boolean reverse;

    private boolean isCancelled = false;

    public FollowingParticlePathEffect(List<Location> locations, FollowingParticleEffect[] particles, int startIndex, boolean reverse) {
        this(locations, particles, startIndex);
        this.reverse = reverse;
    }

    public FollowingParticlePathEffect(List<Location> locations, FollowingParticleEffect[] particles, int startIndex) {
        this.locations = locations;
        this.currentIndex = startIndex;
        this.particles = particles;
    }

    public FollowingParticlePathEffect start(){
        Runnable runnable = () -> {

            if(isCancelled){
                for (FollowingParticleEffect particle : this.particles) {
                    particle.setDone(true);
                }
                return;
            }

            boolean next = false;
            for (FollowingParticleEffect particle : particles) {
                if(particle.isOnTarget()){
                    next = true;
                }else{
                    next = false;
                }
            }

            this.start();

            if(!next){
                return;
            }

            if(this.reverse){
                this.currentIndex--;
                if(this.currentIndex < 0){
                    this.currentIndex = locations.size()-1;
                }
            }else{
                this.currentIndex++;
                if(this.currentIndex >= locations.size()){
                    this.currentIndex = 0;
                }
            }

            for (FollowingParticleEffect particle : this.particles) {
                particle.setLocationTarget(locations.get(this.currentIndex));
            }
        };
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
        return this;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}