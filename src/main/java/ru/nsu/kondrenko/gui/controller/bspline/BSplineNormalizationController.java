package ru.nsu.kondrenko.gui.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.dto.Double2DPoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class BSplineNormalizationController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (context.getPoints().isEmpty()) {
            final double ratio = context.getHeightWidthRatio();
            context.setBSplineMinX(Constants.BSPLINE_START_MIN_X);
            context.setBSplineMaxX(Constants.BSPLINE_START_MAX_X);
            context.setBSplineMinY(Constants.BSPLINE_START_MIN_X * ratio);
            context.setBSplineMaxY(Constants.BSPLINE_START_MAX_X * ratio);
        } else {
            final Double2DPoint firstPoint = context.getPoints().get(0);
            double minX = firstPoint.getX();
            double maxX = firstPoint.getX();
            double minY = firstPoint.getY();
            double maxY = firstPoint.getY();

            for (int i = 0; i < context.getPoints().size(); i++) {
                final Double2DPoint point = context.getPoints().get(i);
                minX = Double.min(point.getX(), minX);
                maxX = Double.max(point.getX(), maxX);
                minY = Double.min(point.getY(), minY);
                maxY = Double.max(point.getY(), maxY);
            }

            final double ratio = context.getHeightWidthRatio();
            final double xRange = maxX - minX;
            final double yRange = maxY - minY;

            if (xRange * context.getHeightWidthRatio() >= yRange) {
                final double newYRange = xRange * ratio;
                final double scale = newYRange / yRange;
                context.setBSplineMinX(minX);
                context.setBSplineMaxX(maxX);
                context.setBSplineMinY(minY * scale);
                context.setBSplineMaxY(maxY * scale);
            } else {
                final double newXRange = yRange / ratio;
                final double scale = newXRange / xRange;
                context.setBSplineMinX(minX * scale);
                context.setBSplineMaxX(maxX * scale);
                context.setBSplineMinY(minY);
                context.setBSplineMaxY(maxY);
            }

            final double offset = 2 * Constants.PIVOT_POINT_RADIUS * context.getXRange() / context.getBSplineWidth();

            context.setBSplineMinX(context.getBSplineMinX() - offset);
            context.setBSplineMaxX(context.getBSplineMaxX() + offset);
            context.setBSplineMinY(context.getBSplineMinY() - offset);
            context.setBSplineMaxY(context.getBSplineMaxY() + offset);
        }

        context.notifyBSplineListeners();
    }
}