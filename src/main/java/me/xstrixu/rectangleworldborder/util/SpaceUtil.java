package me.xstrixu.rectangleworldborder.util;

import lombok.experimental.UtilityClass;
import me.xstrixu.rectangleworldborder.point.Point2D;

@UtilityClass
public class SpaceUtil {

    public static boolean isInsideSpace(Point2D firstCorner, Point2D secondCorner, Point2D point) {
        return point.getX() <= Math.max(firstCorner.getX(), secondCorner.getX()) && point.getX() >= Math.min(firstCorner.getX(), secondCorner.getX()) && point.getZ() <= Math.max(firstCorner.getZ(), secondCorner.getZ()) && point.getZ() >= Math.min(firstCorner.getZ(), secondCorner.getZ());
    }
}
