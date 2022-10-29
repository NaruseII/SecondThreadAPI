package fr.naruse.api.shape;

import fr.naruse.api.MathUtils;
import org.bukkit.Location;

public class ShapeList {

    public static final ShapeBuilder createCubeShape(Location location, double radius){
        return ShapeBuilder.init(location.clone())
                .line(radius, MathUtils.Axis.X)
                .line(radius, MathUtils.Axis.Z)
                .line(-radius, MathUtils.Axis.X)
                .line(-radius, MathUtils.Axis.Z)

                .line(radius, MathUtils.Axis.Y)

                .line(radius, MathUtils.Axis.X)
                .line(radius, MathUtils.Axis.Z)
                .line(-radius, MathUtils.Axis.X)
                .line(-radius, MathUtils.Axis.Z)

                .jump(radius, MathUtils.Axis.X)
                .line(-radius, MathUtils.Axis.Y)
                .jump(radius, MathUtils.Axis.Z)
                .line(radius, MathUtils.Axis.Y)
                .jump(-radius, MathUtils.Axis.X)
                .line(-radius, MathUtils.Axis.Y);
    }

}
