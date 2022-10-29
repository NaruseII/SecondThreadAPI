package fr.naruse.api.effect.particle;

import com.google.common.collect.Lists;
import fr.naruse.api.MathUtils;
import fr.naruse.api.effect.RotationData;
import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import fr.naruse.api.shape.ShapeBuilder;
import fr.naruse.api.shape.ShapeList;
import org.bukkit.Location;

import java.util.List;

public class ParticleShapeEffect extends ParticleEffect {

    private final Location center;
    private final int particleAmountPerLine;
    private double radius;
    private final RotationData rotationData;
    private IParticle particle;

    private Location location;
    private List<Location> shapeLocationList = Lists.newArrayList();
    private ShapeBuilder shapeBuilder;

    public ParticleShapeEffect(Location center, double radius, int particleAmountPerLine, RotationData rotationData, IParticle particle) {
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

        this.calculateShape();
    }

    @Override
    protected void run() {
        ShapeBuilder shapeBuilder1 = this.shapeBuilder.clone();

        int degree = this.rotationData.getCalculatedDegree();

        for (MathUtils.Axis axis : this.rotationData.getRotationAxis()) {
            shapeBuilder1.rotateAll(degree, axis);
        }

        shapeBuilder1.center();

        this.shapeLocationList = shapeBuilder1.build(this.particleAmountPerLine);

        for (Location location1 : this.shapeLocationList) {
            Particle.buildParticle(location1, this.particle, 0, 0, 0, 1).toNearbyFifty();
        }
    }

    @Override
    public void kill() {
        this.killRunner();
    }

    public void calculateShape(){
        this.shapeBuilder = ShapeList.createCubeShape(this.location, this.radius);
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return this.radius;
    }

    public RotationData getRotationData() {
        return this.rotationData;
    }

    public Location getLocation() {
        return this.location.clone();
    }

    public ShapeBuilder getShapeBuilder() {
        return this.shapeBuilder;
    }

    public void setShapeBuilder(ShapeBuilder shapeBuilder) {
        this.shapeBuilder = shapeBuilder;
    }

    public List<Location> getShapeLocationList() {
        return this.shapeLocationList;
    }

    public IParticle getParticle() {
        return this.particle;
    }

    public void setParticle(IParticle particle) {
        this.particle = particle;
    }
}
