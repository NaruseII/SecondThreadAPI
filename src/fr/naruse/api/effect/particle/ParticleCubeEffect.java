package fr.naruse.api.effect.particle;

import com.google.common.collect.Lists;
import fr.naruse.api.MathUtils;
import fr.naruse.api.effect.RotationData;
import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import org.bukkit.Location;

import java.util.List;

public class ParticleCubeEffect extends ParticleEffect {

    private final Location center;
    private final int particleAmountPerLine;
    private final double radius;
    private final RotationData rotationData;
    private final IParticle particle;

    private Location location;
    private List<Location> cubeShapeLocationList = Lists.newArrayList();
    private MathUtils.ShapeBuilder shapeBuilder;

    public ParticleCubeEffect(Location center, double radius, int particleAmountPerLine, RotationData rotationData, IParticle particle) {
        this.center = center;
        this.rotationData = rotationData;
        this.particleAmountPerLine = particleAmountPerLine;
        this.radius = radius;
        this.particle = particle;

        this.init();
    }

    @Override
    protected void init() {
        this.location = this.center.clone();

        this.shapeBuilder = MathUtils.ShapeBuilder.init(this.location.clone())
                .line(this.radius, MathUtils.Axis.X)
                .line(this.radius, MathUtils.Axis.Z)
                .line(-this.radius, MathUtils.Axis.X)
                .line(-this.radius, MathUtils.Axis.Z)

                .line(this.radius, MathUtils.Axis.Y)

                .line(this.radius, MathUtils.Axis.X)
                .line(this.radius, MathUtils.Axis.Z)
                .line(-this.radius, MathUtils.Axis.X)
                .line(-this.radius, MathUtils.Axis.Z)

                .jump(this.radius, MathUtils.Axis.X)
                .line(-this.radius, MathUtils.Axis.Y)
                .jump(this.radius, MathUtils.Axis.Z)
                .line(this.radius, MathUtils.Axis.Y)
                .jump(-this.radius, MathUtils.Axis.X)
                .line(-this.radius, MathUtils.Axis.Y);
    }

    @Override
    protected void run() {
        MathUtils.ShapeBuilder shapeBuilder1 = this.shapeBuilder.clone();

        int degree = this.rotationData.getCalculatedDegree();

        for (MathUtils.Axis axis : this.rotationData.getRotationAxis()) {
            shapeBuilder1.rotateAll(degree, axis);
        }

        shapeBuilder1.center();

        this.cubeShapeLocationList = shapeBuilder1.build(this.particleAmountPerLine);

        for (Location location1 : this.cubeShapeLocationList) {
            Particle.buildParticle(location1, this.particle, 0, 0, 0, 1).toAll();
        }
    }

    @Override
    public void kill() {
        this.killRunner();
    }


}
