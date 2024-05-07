package ru.nsu.kondrenko.model;

import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.IntPoint;

public final class CommonUtils {
    private CommonUtils() {
    }

    public static Double2DPoint screenToReal(IntPoint screenPoint,
                                             int screenWidth,
                                             int screenHeight,
                                             double realMinX,
                                             double realMaxX,
                                             double realMinY,
                                             double realMaxY) {
        final double xRelative = 1.0 * screenPoint.getX() / screenWidth;
        final double yRelative = 1.0 * screenPoint.getY() / screenHeight;
        final double x = realMinX + xRelative * (realMaxX - realMinX);
        final double y = realMinY + (realMaxY - realMinY) * (1 - yRelative);
        return new Double2DPoint(x, y);
    }

    public static IntPoint realToScreen(Double2DPoint realPoint,
                                        int screenWidth,
                                        int screenHeight,
                                        double realMinX,
                                        double realMaxX,
                                        double realMinY,
                                        double realMaxY) {
        final double xRange = realMaxX - realMinX;
        final double yRange = realMaxY - realMinY;
        final double xRelative = (realPoint.getX() - realMinX) / xRange;
        final double yRelative = (realPoint.getY() - realMinY) / yRange;
        final int x = (int) Math.round(xRelative * screenWidth);
        final int y = (int) Math.round((1 - yRelative) * screenHeight);
        return new IntPoint(x, y);
    }
}
