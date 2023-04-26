package fr.naruse.api.effect.particle;

import fr.naruse.api.particle.IParticle;
import fr.naruse.api.particle.Particle;
import org.bukkit.Location;

public class ParticleCycloneLineEffect extends ParticleEffect implements IParticleEffect {

    private final Location center;
    private final double particleDistance;
    private final double upSpeed;
    private final double spinningSpeed;
    private final double maxUp;
    private final IParticle particle;

    private Location location;
    private double X = 0;
    private double y = 0;
    private double scale = 1;
    private double scaleAdd = 0;

    public ParticleCycloneLineEffect(Location center, double particleDistance, double upSpeed, double spinningSpeed,
                                     double maxUp, IParticle particle) {
        this.center = center;
        this.particleDistance = particleDistance;
        this.upSpeed = upSpeed;
        this.spinningSpeed = spinningSpeed;
        this.maxUp = maxUp;
        this.particle = particle;

        this.init();
    }

    @Override
    protected void init() {
        this.location = this.center.clone();
        this.scaleAdd = (this.scale/(this.maxUp/this.upSpeed))*2;
    }

    @Override
    protected void run() {
        this.X += this.particleDistance;
        this.y += this.upSpeed;

        Location calculatedLocation = this.applyFunction(this.location);

        Particle.buildParticle(calculatedLocation, this.particle, 0, 0, 0, 1).toAll();

        if(calculatedLocation.getY() >= this.maxUp+this.center.getY()){
            this.kill();
        }

        if(calculatedLocation.getY() < this.maxUp/2+this.center.getY()){
            this.scale -= this.scaleAdd;
        }else{
            this.scale += this.scaleAdd;
        }

    }

    @Override
    public void kill() {
        this.killRunner();
    }

    private Location applyFunction(Location location) {
        double x = Math.cos(this.X)*this.spinningSpeed*this.scale;
        double z = Math.sin(this.X)*this.spinningSpeed*this.scale;

        return location.clone().add(x, this.y, z);
    }

}
