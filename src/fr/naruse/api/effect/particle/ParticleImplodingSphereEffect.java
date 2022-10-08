package fr.naruse.api.effect.particle;

import com.google.common.collect.Sets;
import fr.naruse.api.MathUtils;
import fr.naruse.api.async.CollectionManager;
import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.sender.ParticleSender;
import org.bukkit.Location;

import java.util.List;
import java.util.Set;

public class ParticleImplodingSphereEffect extends ParticleEffect {

    private final Location centerLocation;
    private final int startRadius;
    private final double particleDistance;
    private final int speedDivider;
    private final ParticleSender particleSender;
    private final IParticle particle;

    private Set<FollowingParticleEffect> followingParticleEffectSet = Sets.newHashSet();
    private boolean started = false;

    public ParticleImplodingSphereEffect(Location centerLocation, int startRadius, double particleDistance, int speedDivider, ParticleSender particleSender, IParticle particle) {
        this.centerLocation = centerLocation;
        this.startRadius = startRadius;
        this.particleDistance = particleDistance;
        this.speedDivider = speedDivider;
        this.particleSender = particleSender;
        this.particle = particle;

        this.init();
    }

    @Override
    protected void init() {
        CollectionManager.POOL_EXECUTOR.submit(() -> {
            List<Location> list = MathUtils.getSphere(this.centerLocation, this.startRadius, this.startRadius,
                    false, false, this.particleDistance);

            for (int i = 0; i < list.size(); i++) {
                Location location = list.get(i);

                FollowingParticleEffect followingParticleEffect = new FollowingParticleEffect(this.centerLocation, this.particle, this.particleSender, location, this.speedDivider);
                this.followingParticleEffectSet.add(followingParticleEffect);
            }

            if(this.started){
                this.start();
            }

        });
    }

    @Override
    public void start() {
        super.start();

        this.started = true;
        for (FollowingParticleEffect followingParticleEffect : this.followingParticleEffectSet) {
            followingParticleEffect.start();
        }
    }

    @Override
    protected void run() {

    }

    @Override
    public void kill() {
        this.killRunner();
        for (FollowingParticleEffect followingParticleEffect : this.followingParticleEffectSet) {
            followingParticleEffect.setDone(true);
        }
    }
}
