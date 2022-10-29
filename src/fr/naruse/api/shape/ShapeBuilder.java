package fr.naruse.api.shape;

import com.google.common.collect.Lists;
import fr.naruse.api.MathUtils;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class ShapeBuilder {

    private final List<Vector> vectorList = Lists.newArrayList();
    private final List<Integer> jumpedVectorList = Lists.newArrayList();
    private final Location startLocation;

    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;

    private double centeredX = 0;
    private double centeredY = 0;
    private double centeredZ = 0;

    private ShapeBuilder(ShapeBuilder clone) {
        this.startLocation = clone.startLocation.clone();
        for (Vector vector : clone.vectorList) {
            this.vectorList.add(vector.clone());
        }
        this.jumpedVectorList.addAll(clone.jumpedVectorList);
        this.rotationX = clone.rotationX;
        this.rotationY = clone.rotationY;
        this.rotationZ = clone.rotationZ;
    }

    public ShapeBuilder(Location startLocation) {
        this.startLocation = startLocation;
    }

    public static ShapeBuilder init(Location startLocation){
        return new ShapeBuilder(startLocation);
    }

    public ShapeBuilder center(){
        Location clone = this.startLocation.clone();
        Location farther = null;
        double squared = 0;

        for (Vector vector : this.vectorList) {
            clone.add(vector);

            double newSquared = MathUtils.distanceSquared(clone, this.startLocation);
            if(newSquared > squared){
                farther = clone.clone();
                squared = newSquared;
            }
        }

        if(farther != null){
            this.centeredX = (farther.getX() - this.startLocation.getX())/-2;
            this.centeredY = (farther.getY() - this.startLocation.getY())/-2;
            this.centeredZ = (farther.getZ() - this.startLocation.getZ())/-2;

            this.startLocation.setX(this.startLocation.getX() + this.centeredX);
            this.startLocation.setY(this.startLocation.getY() + this.centeredY);
            this.startLocation.setZ(this.startLocation.getZ() + this.centeredZ);
        }

        return this;
    }

    public ShapeBuilder rotate(double rotation, MathUtils.Axis axis){
        switch (axis) {
            case X:
                this.rotationX = rotation;
                break;
            case Y:
                this.rotationY = rotation;
                break;
            case Z:
                this.rotationZ = rotation;
                break;
        }
        return this;
    }

    public ShapeBuilder circle(double blocksRadius, int pointAmount, MathUtils.Axis axis){

        int degree = 0;
        int degreeToIncrement = 360/pointAmount;
        double perimeter = 2 * Math.PI * blocksRadius;
        double distanceBetweenPoint = perimeter / pointAmount;

        this.rotate(degree, axis);
        this.line(distanceBetweenPoint, axis.opposite());

        for (int i = 0; i < pointAmount; i++) {
            degree += degreeToIncrement;
            this.rotate(degree, axis);
            this.line(distanceBetweenPoint, axis.opposite());
        }

        return this;
    }

    public ShapeBuilder rotateAll(double degrees, MathUtils.Axis axis){
        for (Vector vector : this.vectorList) {
            Vector newVector = MathUtils.rotateVector(vector.clone(), degrees, axis);
            vector.copy(newVector);
        }
        return this;
    }

    public ShapeBuilder moveAll(double blockDistance){
        this.moveAll(blockDistance, MathUtils.Axis.X);
        this.moveAll(blockDistance, MathUtils.Axis.Y);
        return this.moveAll(blockDistance, MathUtils.Axis.Z);
    }

    public ShapeBuilder moveAll(double blockDistance, MathUtils.Axis axis){
        for (Vector vector : this.vectorList) {
            switch (axis) {
                case X:
                    vector.setX(vector.getX()+blockDistance);
                    break;
                case Y:
                    vector.setY(vector.getY()+blockDistance);
                    break;
                case Z:
                    vector.setZ(vector.getZ()+blockDistance);
                    break;
            }
        }
        return this;
    }

    public ShapeBuilder jump(double blockDistance, MathUtils.Axis axis){
        Vector vector = new Vector(axis == MathUtils.Axis.X ? blockDistance : 0,
                axis == MathUtils.Axis.Y ? blockDistance : 0,
                axis == MathUtils.Axis.Z ? blockDistance : 0);
        this.vectorList.add(vector);
        this.jumpedVectorList.add(this.vectorList.size()-1);
        return this;
    }

    public ShapeBuilder line(double blockDistance, MathUtils.Axis axis){
        Vector vector = new Vector(axis == MathUtils.Axis.X ? blockDistance : 0,
                axis == MathUtils.Axis.Y ? blockDistance : 0,
                axis == MathUtils.Axis.Z ? blockDistance : 0);

        if(this.rotationX != 0){
            vector = MathUtils.rotateVector(vector, this.rotationX, MathUtils.Axis.X);
            this.rotationX = 0;
        }
        if(this.rotationY != 0){
            vector = MathUtils.rotateVector(vector, this.rotationY, MathUtils.Axis.Y);
            this.rotationY = 0;
        }
        if(this.rotationZ != 0){
            vector = MathUtils.rotateVector(vector, this.rotationZ, MathUtils.Axis.Z);
            this.rotationZ = 0;
        }

        this.vectorList.add(vector);
        return this;
    }

    public List<Location> build(int amountPerBlock){
        List<Location> list = Lists.newArrayList();

        Location location = this.startLocation.clone();
        list.add(location.clone());

        for (int o = 0; o < this.vectorList.size(); o++) {
            Vector vector = this.vectorList.get(o);
            Vector minimalVector = vector.clone().multiply(1d/amountPerBlock);

            for (int i = 0; i < Math.max(amountPerBlock, 1); i++) {
                location.add(minimalVector);

                if(!this.jumpedVectorList.contains(o)){
                    list.add(location.clone());
                }
            }

        }

        return list;
    }

    public ShapeBuilder clone(){
        return new ShapeBuilder(this);
    }
}
