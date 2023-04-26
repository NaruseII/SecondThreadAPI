package fr.naruse.api.effect.particle;

import com.google.common.collect.Sets;
import fr.naruse.api.MathUtils;
import fr.naruse.api.particle.IParticle;
import org.bukkit.Location;

import java.util.List;
import java.util.Set;

public class ParticleCycloneEffect extends ParticleEffect implements IParticleEffect{

    private final Location center;
    private final double radius;
    private final double particleDistance;
    private final double upSpeed;
    private final double spinningSpeed;
    private final double maxUp;
    private final int amount;
    private final IParticle particle;

    private final Set<ParticleCycloneLineEffect> effectSet = Sets.newHashSet();

    public ParticleCycloneEffect(Location center, double radius, double particleDistance, double upSpeed, double spinningSpeed,
                                     double maxUp, int amount, IParticle particle) {
        this.center = center;
        this.radius = radius;
        this.particleDistance = particleDistance;
        this.upSpeed = upSpeed;
        this.spinningSpeed = spinningSpeed;
        this.maxUp = maxUp;
        this.amount = amount;
        this.particle = particle;

        this.init();
    }

    @Override
    protected void init() {
        List<Location> locationList = MathUtils.getCircle(this.center, this.radius, this.amount);
        for (int i = 0; i < this.amount; i++) {
            ParticleCycloneLineEffect effect = new ParticleCycloneLineEffect(locationList.get(i),
                    this.particleDistance, this.upSpeed, this.spinningSpeed, this.maxUp, this.particle);
            this.effectSet.add(effect);
        }
    }

    @Override
    protected void run() {
        for (ParticleCycloneLineEffect effect : this.effectSet) {
            effect.start();
        }
    }

    @Override
    public void kill() {
        this.killRunner();
        for (ParticleCycloneLineEffect effect : this.effectSet) {
            effect.kill();
        }
    }
}
