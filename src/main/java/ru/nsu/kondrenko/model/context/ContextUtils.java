package ru.nsu.kondrenko.model.context;

import ru.nsu.kondrenko.model.CommonUtils;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.IntPoint;

import java.util.List;

public class ContextUtils {
    public static Double2DPoint screenToRealBspline(IntPoint screenPoint, Context context) {
        return CommonUtils.screenToReal(
                screenPoint,
                context.getBSplineWidth(),
                context.getBSplineHeight(),
                context.getBSplineMinX(),
                context.getBSplineMaxX(),
                context.getBSplineMinY(),
                context.getBSplineMaxY()
        );
    }

    public static IntPoint realToScreenBSpline(Double2DPoint realPoint, Context context) {
        return CommonUtils.realToScreen(
                realPoint,
                context.getBSplineWidth(),
                context.getBSplineHeight(),
                context.getBSplineMinX(),
                context.getBSplineMaxX(),
                context.getBSplineMinY(),
                context.getBSplineMaxY()
        );
    }

    public static IntPoint realToScreenWireframe(Double2DPoint realPoint, Context context) {
        return CommonUtils.realToScreen(
                realPoint,
                context.getWireframeWidth(),
                context.getWireframeHeight(),
                context.getWireframeMinX(),
                context.getWireframeMaxX(),
                context.getWireframeMinY(),
                context.getWireframeMaxY()
        );
    }

    public static void normalizeContext(Context context) {
        final List<Double2DPoint> points = context.getPivotPoints();

        if (points.size() == 1) {
            return;
        }

        if (points.isEmpty()) {
            final double ratio = context.getHeightWidthRatio();
            context.setBSplineMinX(Constants.BSPLINE_START_MIN_X);
            context.setBSplineMaxX(Constants.BSPLINE_START_MAX_X);
            context.setBSplineMinY(Constants.BSPLINE_START_MIN_X * ratio);
            context.setBSplineMaxY(Constants.BSPLINE_START_MAX_X * ratio);
        } else {
            final Double2DPoint firstPoint = context.getPivotPoints().get(0);
            double minX = firstPoint.getX();
            double maxX = firstPoint.getX();
            double minY = firstPoint.getY();
            double maxY = firstPoint.getY();

            for (int i = 0; i < context.getPivotPoints().size(); i++) {
                final Double2DPoint point = context.getPivotPoints().get(i);
                minX = Double.min(point.getX(), minX);
                maxX = Double.max(point.getX(), maxX);
                minY = Double.min(point.getY(), minY);
                maxY = Double.max(point.getY(), maxY);
            }

            final double ratio = context.getHeightWidthRatio();
            final double xRange = maxX - minX;
            final double yRange = maxY - minY;

            if (xRange * context.getHeightWidthRatio() >= yRange) {
                final double newYRangeHalved = 0.5 * xRange * ratio;
                final double avgY = (maxY + minY) / 2;
                context.setBSplineMinX(minX);
                context.setBSplineMaxX(maxX);
                context.setBSplineMinY(-newYRangeHalved + avgY);
                context.setBSplineMaxY(newYRangeHalved + avgY);
            } else {
                final double newXRangeHalved = 0.5 * yRange / ratio;
                final double avgX = (maxX + minX) / 2;
                context.setBSplineMinX(-newXRangeHalved + avgX);
                context.setBSplineMaxX(newXRangeHalved + avgX);
                context.setBSplineMinY(minY);
                context.setBSplineMaxY(maxY);
            }

            final double offset = 2 * Constants.PIVOT_POINT_RADIUS * context.getBSplineXRange() / context.getBSplineWidth();
            context.setBSplineMinX(context.getBSplineMinX() - offset);
            context.setBSplineMaxX(context.getBSplineMaxX() + offset);
            context.setBSplineMinY(context.getBSplineMinY() - offset);
            context.setBSplineMaxY(context.getBSplineMaxY() + offset);
        }

        context.notifyBSplineListeners();
    }
}
