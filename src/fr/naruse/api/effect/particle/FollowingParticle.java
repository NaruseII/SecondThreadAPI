package fr.naruse.api.effect.particle;

import fr.naruse.api.MathUtils;
import fr.naruse.api.ParticleUtils;
import fr.naruse.api.async.CollectionManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class FollowingParticle {

    private Entity target = null;
    private Location locationTarget = null;
    private final Object particleType;
    private final ParticleUtils.ParticleSender sender;
    private final Location start;
    private final int speed;

    private boolean isDone = false;
    private boolean stopOnTouchTarget = true;
    private boolean isOnTarget = false;

    public FollowingParticle(Object particleType, ParticleUtils.ParticleSender sender, Location start, int speed) {
        this.start = start;
        this.speed = speed;
        this.particleType = particleType;
        this.sender = sender;

        this.start.setX(MathUtils.offSet(start.getX(), 250));
        this.start.setY(MathUtils.offSet(start.getY(), 150));
        this.start.setZ(MathUtils.offSet(start.getZ(), 250));
    }

    public FollowingParticle(Entity target, Object particleType, ParticleUtils.ParticleSender sender, Location start, int speed) {
        this(particleType, sender, start, speed);
        this.target = target;
    }

    public FollowingParticle(Location target, Object particleType, ParticleUtils.ParticleSender sender, Location start, int speed) {
        this(particleType, sender, start, speed);
        this.locationTarget = target;
    }

    public void onAsyncParticleTouchTarget(Entity target) { }

    public FollowingParticle start(){
        Runnable runnable = () -> {
            if(this.isDone){
                return;
            }
            this.effect();
            this.start();
        };
        CollectionManager.SECOND_THREAD_RUNNABLE_SET.add(runnable);
        return this;
    }

    public void effect(){
        if(this.target != null && this.target.isDead()){
            this.isDone = true;
            return;
        }

        boolean skipAdd = false;

        if((this.target != null && this.target.getLocation().distanceSquared(this.start) < 0.49) || (this.locationTarget != null && this.locationTarget.distanceSquared(this.start) < 0.49)){
            if(stopOnTouchTarget){
                this.isDone = true;
                this.onAsyncParticleTouchTarget(target);
                return;
            }
            this.isOnTarget = true;
            skipAdd = true;
        }

        if(!skipAdd){
            double xToAdd = Math.abs(this.start.getX()-(this.target == null ? this.locationTarget.getX() : this.target.getLocation().getX()))/this.speed;
            double yToAdd = Math.abs(this.start.getY()-(this.target == null ? this.locationTarget.getY() : this.target.getLocation().getY()))/this.speed;
            double zToAdd = Math.abs(this.start.getZ()-(this.target == null ? this.locationTarget.getZ() : this.target.getLocation().getZ()))/this.speed;
            this.start.add(this.needToAddPositive(MathUtils.Axis.X) ? xToAdd : -xToAdd,
                    this.needToAddPositive(MathUtils.Axis.Y) ? yToAdd : -yToAdd,
                    this.needToAddPositive(MathUtils.Axis.Z) ? zToAdd : -zToAdd);
        }

        sender.send(ParticleUtils.buildParticle(this.start, particleType, 0, 0, 0, 1));
    }

    private boolean needToAddPositive(MathUtils.Axis axis){
        switch (axis){
            case X:
                return !(this.start.getX()-(this.target == null ? this.locationTarget.getX() : this.target.getLocation().getX()) > 0);
            case Y:
                return !(this.start.getY()-(this.target == null ? this.locationTarget.getY() : this.target.getLocation().getY()) > 0);
            default:
                return !(this.start.getZ()-(this.target == null ? this.locationTarget.getZ() : this.target.getLocation().getZ()) > 0);
        }
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isOnTarget() {
        return isOnTarget;
    }

    public void setLocationTarget(Location locationTarget) {
        this.locationTarget = locationTarget;
        this.isOnTarget = false;
    }

    public FollowingParticle setStopOnTouchTarget(boolean stopOnTouchTarget) {
        this.stopOnTouchTarget = stopOnTouchTarget;
        return this;
    }

    public void setStart(Location start){
        this.start.setX(MathUtils.offSet(start.getX(), 250));
        this.start.setY(MathUtils.offSet(start.getY(), 150));
        this.start.setZ(MathUtils.offSet(start.getZ(), 250));
    }
}
