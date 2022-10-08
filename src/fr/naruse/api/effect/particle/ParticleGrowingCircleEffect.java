package fr.naruse.api.effect.particle;

import fr.naruse.api.MathUtils;
import fr.naruse.api.async.CollectionManager;
import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import org.bukkit.Location;
import org.bukkit.Material;

public class ParticleGrowingCircleEffect {

    private final Location center;
    private final double radiusAdd;
    private final double radiusEnd;
    private final int amountAdd;
    private final int particleCount;
    private final boolean closeToBlock;
    private final float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private final IParticle[] enumParticles;

    private double d = 0;
    private int amount = 4;

    public ParticleGrowingCircleEffect(Location center, double radiusAdd, double radiusEnd, int amountAdd, int particleCount, boolean closeToBlock, IParticle... enumParticles) {
        this(center, radiusAdd, radiusEnd, amountAdd, particleCount, closeToBlock, 0, 0, 0, enumParticles);
    }

    public ParticleGrowingCircleEffect(Location center, double radiusAdd, double radiusEnd, int amountAdd, int particleCount, boolean closeToBlock, float offsetX, float offsetY, float offsetZ, IParticle... enumParticles) {
        this.center = center;
        this.radiusAdd = radiusAdd;
        this.radiusEnd = radiusEnd;
        this.amountAdd = amountAdd;
        this.particleCount = particleCount;
        this.closeToBlock = closeToBlock;
        this.enumParticles = enumParticles;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;

        this.playCircleEffect();
    }

    private void playCircleEffect() {
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(() -> {
            if(d > radiusEnd){
                return;
            }
            for (Location location : MathUtils.getCircle(center, d, amount)) {

                if(closeToBlock){
                    location.add(0, 5, 0);
                    while (location.getBlock().getType() == Material.AIR){
                        if(location.getBlock().getRelative(0, -1, 0).getType() == Material.AIR){
                            location.add(0, -1, 0);
                        }else{
                            break;
                        }
                    }
                }

                for (IParticle enumParticle : this.enumParticles) {
                    Particle.buildParticle(location, enumParticle, offsetY, offsetX, offsetZ, particleCount, 0).toNearbyFifty();
                }
            }
            d += radiusAdd;
            amount += amountAdd;
            playCircleEffect();
        });
    }
}
