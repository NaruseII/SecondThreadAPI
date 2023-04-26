package fr.naruse.api.effect.particle;

import com.google.common.collect.Sets;
import fr.naruse.api.MathUtils;
import fr.naruse.api.async.CollectionManager;
import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.sender.ParticleSender;
import org.bukkit.Location;

import java.util.List;
import java.util.Set;

public class ParticleCircleAroundLocationEffect implements IParticleEffect{

    private final int circleAmount;
    private final int circleRadius;
    private final int speedDivider;
    private final IParticle[] enumParticles;
    private boolean reverse = false;

    private final Set<FollowingParticlePathEffect> loopingParticles = Sets.newHashSet();
    private boolean isCancelled = false;
    private Location location;

    public ParticleCircleAroundLocationEffect(Location location, int circleAmount, int circleRadius, int speedDivider, boolean reverse, IParticle... enumParticles) {
        this.location = location;
        this.circleAmount = circleAmount;
        this.circleRadius = circleRadius;
        this.speedDivider = speedDivider;
        this.enumParticles = enumParticles;

        this.reverse = reverse;

        this.init();
        this.play();
    }

    public ParticleCircleAroundLocationEffect(Location location, int circleAmount, int circleRadius, int speedDivider, IParticle... enumParticles) {
        this.location = location;
        this.circleAmount = circleAmount;
        this.circleRadius = circleRadius;
        this.speedDivider = speedDivider;
        this.enumParticles = enumParticles;

        this.init();
        this.play();
    }

    private void init() {
        List<Location> circle = MathUtils.getCircle(location, circleRadius, circleAmount);
        ParticleSender sender = ParticleSender.buildToAll();

        for (int i = 0; i < circle.size(); i++) {

            Location loc = circle.get(i);

            Set<FollowingParticleEffect> set = Sets.newHashSet();
            for (IParticle enumParticle : this.enumParticles) {
                set.add(new FollowingParticleEffect(loc.clone(), enumParticle, sender, location.clone(), speedDivider).setStopOnTouchTarget(false).start());
            }

            this.loopingParticles.add(new FollowingParticlePathEffect(circle, set.toArray(new FollowingParticleEffect[0]), i, reverse).start());
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
            if (isCancelled) {
                kill();
                return;
            }

            play();
        });
    }
}