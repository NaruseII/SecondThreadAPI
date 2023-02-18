package fr.naruse.api.effect;

import com.google.common.collect.Lists;
import fr.naruse.api.MathUtils;

import java.util.List;

public class RotationData {

    private int tickInterval;
    private boolean axisX;
    private boolean axisY;
    private boolean axisZ;
    private boolean isNegativeRotation = false;
    private double degreeIncrement = 1;

    private double currentDegree = 0;
    private int tick;

    public RotationData setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
        return this;
    }

    public RotationData setDegreeIncrement(double degreeIncrement) {
        this.degreeIncrement = degreeIncrement;
        return this;
    }

    public RotationData setDegreeIncrement(int degreeIncrement) {
        this.degreeIncrement = degreeIncrement;
        return this;
    }

    public RotationData setNegativeRotation() {
        this.isNegativeRotation = true;
        return this;
    }

    public RotationData addRotationAxis(MathUtils.Axis... axis) {
        for (MathUtils.Axis a : axis) {
            switch (a) {
                case X:
                    this.axisX = true;
                    break;
                case Y:
                    this.axisY = true;
                    break;
                case Z:
                    this.axisZ = true;
                    break;
            }
        }

        return this;
    }

    public MathUtils.Axis[] getRotationAxis(){
        List<MathUtils.Axis> list = Lists.newArrayList();

        if(this.axisX){
            list.add(MathUtils.Axis.X);
        }
        if(this.axisY){
            list.add(MathUtils.Axis.Y);
        }
        if(this.axisZ){
            list.add(MathUtils.Axis.Z);
        }

        return list.toArray(new MathUtils.Axis[0]);
    }

    public double getCalculatedDegree(){
        if(this.tick == this.tickInterval){
            this.tick = 0;

            if(this.isNegativeRotation){
                if(this.currentDegree <= -1){
                    this.currentDegree = 360;
                }else{
                    this.currentDegree -= this.degreeIncrement;
                }
            }else{
                if(this.currentDegree >= 361){
                    this.currentDegree = 0;
                }else{
                    this.currentDegree += this.degreeIncrement;
                }
            }

        }else{
            this.tick++;
        }

        return this.currentDegree;
    }

    public double getCurrentDegree() {
        return this.currentDegree;
    }

    public double getDegreeIncrement() {
        return this.degreeIncrement;
    }
}
