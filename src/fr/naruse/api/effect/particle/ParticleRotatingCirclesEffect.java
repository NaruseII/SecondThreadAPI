package fr.naruse.api.effect.particle;

import com.google.common.collect.Lists;
import fr.naruse.api.MathUtils;
import fr.naruse.api.effect.RotationData;
import fr.naruse.api.particle.IParticle;
import org.bukkit.Location;

import java.util.List;

public abstract class ParticleRotatingCirclesEffect extends ParticleEffect implements IParticleEffect{

    private List<ParticleRotatingCircleEffect> effectList = Lists.newArrayList();

    public ParticleRotatingCirclesEffect() {

        this.init();
    }

    public abstract void initCircleEffect();

    protected void newCircleEffect(Location center, double radius, int particleAmount, MathUtils.Axis circleAxis,
                                   RotationData rotationData, IParticle particle){
        ParticleRotatingCircleEffect effect = new ParticleRotatingCircleEffect(center, radius, particleAmount, circleAxis, rotationData, particle);
        effect.start();
        this.effectList.add(effect);
    }

    @Override
    protected void init() {
        this.initCircleEffect();
    }

    @Override
    protected void run() {

    }

    @Override
    public void kill() {
        this.killRunner();
        for (ParticleRotatingCircleEffect effect : this.effectList) {
            effect.kill();
        }
    }

    @Override
    public void start() {

    }

    public List<ParticleRotatingCircleEffect> getEffectList() {
        return this.effectList;
    }
}

