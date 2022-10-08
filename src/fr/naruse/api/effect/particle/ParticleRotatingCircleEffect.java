package fr.naruse.api.effect.particle;

import com.google.common.collect.Lists;
import fr.naruse.api.MathUtils;
import fr.naruse.api.effect.RotationData;
import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import org.bukkit.Location;

import java.util.List;

public class ParticleRotatingCircleEffect extends ParticleEffect {

    private final Location center;
    private final int particleAmount;
    private final double radius;
    private final RotationData rotationData;
    private final MathUtils.Axis circleAxis;
    private final IParticle particle;

    private Location location;
    private List<Location> cubeShapeLocationList = Lists.newArrayList();
    private MathUtils.ShapeBuilder shapeBuilder;

    public ParticleRotatingCircleEffect(Location center, double radius, int particleAmount, MathUtils.Axis circleAxis,
                                        RotationData rotationData, IParticle particle) {
        this.center = center;
        this.rotationData = rotationData;
        this.particleAmount = particleAmount;
        this.circleAxis = circleAxis;
        this.radius = radius;
        this.particle = particle;

        this.init();
    }

    @Override
    protected void init() {
        this.location = this.center.clone();

        this.shapeBuilder = MathUtils.ShapeBuilder.init(this.location)
                .circle(this.radius, this.particleAmount, this.circleAxis);
    }

    private int index = 0;

    @Override
    protected void run() {
        MathUtils.ShapeBuilder shapeBuilder1 = this.shapeBuilder.clone();

        int degree = this.rotationData.getCalculatedDegree();

        for (MathUtils.Axis axis : this.rotationData.getRotationAxis()) {
            shapeBuilder1.rotateAll(degree, axis);
        }

        shapeBuilder1.center();

        this.cubeShapeLocationList = shapeBuilder1.build(1);

        if(this.index >= this.cubeShapeLocationList.size()-1){
            this.index = 0;
        }

        Location location1 = this.cubeShapeLocationList.get(this.index);
        Particle.buildParticle(location1, this.particle, 0, 0, 0, 1).toAll();

        this.index ++;

    }

    @Override
    public void kill() {
        this.killRunner();
    }

}

