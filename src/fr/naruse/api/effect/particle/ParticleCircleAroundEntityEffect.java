package fr.naruse.api.effect.particle;

import com.google.common.collect.Sets;
import fr.naruse.api.MathUtils;
import fr.naruse.api.async.CollectionManager;
import fr.naruse.api.particle.Particle;
import fr.naruse.api.particle.sender.ParticleSender;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Set;

public class ParticleCircleAroundEntityEffect implements IParticleEffect {

    private final Entity entity;
    private final ParticleSender particleSender;
    private final int circleAmount;
    private final int circleRadius;
    private final int speedDivider;

    private final Set<FollowingParticlePathEffect> loopingParticles = Sets.newHashSet();
    private boolean isCancelled = false;
    private Location location;

    public ParticleCircleAroundEntityEffect(Entity entity, ParticleSender particleSender, int circleAmount, int circleRadius, int speedDivider) {
        this.entity = entity;
        this.particleSender = particleSender;
        this.circleAmount = circleAmount;
        this.circleRadius = circleRadius;
        this.speedDivider = speedDivider;

        this.init();
        this.play();
    }

    private void init() {
        this.location = entity.getLocation();
        List<Location> circle = MathUtils.getCircle(entity.getLocation().add(0, 3, 0), circleRadius,  circleAmount);

        for (int i = 0; i < circle.size(); i++) {

            Location loc = circle.get(i);

            this.loopingParticles.add(new FollowingParticlePathEffect(circle, new FollowingParticleEffect[] {
                    new FollowingParticleEffect(loc, Particle.getEnumParticle().WITCH(), particleSender, entity.getLocation(), speedDivider).setStopOnTouchTarget(false).start()
                    , new FollowingParticleEffect(loc, Particle.getEnumParticle().CLOUD(), particleSender, entity.getLocation(), speedDivider).setStopOnTouchTarget(false).start()
            }, i).start());
        }
    }

    public void kill() {
        isCancelled = true;
        for (FollowingParticlePathEffect loopingParticle : loopingParticles) {
            loopingParticle.setCancelled(true);
        }
    }

    private void play() {
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(() -> {
            if(isCancelled || entity == null || entity.isDead()){
                kill();
                return;
            }

            if(location.distanceSquared(entity.getLocation()) != 0){
                for (FollowingParticlePathEffect loopingParticle : loopingParticles) {
                    loopingParticle.setCancelled(true);
                }
                loopingParticles.clear();
                init();
            }

            play();
        });
    }

}
