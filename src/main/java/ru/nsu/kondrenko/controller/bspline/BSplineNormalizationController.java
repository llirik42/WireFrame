package ru.nsu.kondrenko.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.Double2DPoint;
import ru.nsu.kondrenko.model.context.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class BSplineNormalizationController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (context.getPoints().isEmpty()) {
            final double ratio = context.getHeightWidthRatio();
            context.setMinX(Constants.START_MIN_X);
            context.setMaxX(Constants.START_MAX_X);
            context.setMinY(Constants.START_MIN_X * ratio);
            context.setMaxY(Constants.START_MAX_X * ratio);
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
                context.setMinX(minX);
                context.setMaxX(maxX);
                context.setMinY(minY * scale);
                context.setMaxY(maxY * scale);
            } else {
                final double newXRange = yRange / ratio;
                final double scale = newXRange / xRange;
                context.setMinX(minX * scale);
                context.setMaxX(maxX * scale);
                context.setMinY(minY);
                context.setMaxY(maxY);
            }

            final double offset = 2 * Constants.PIVOT_POINT_RADIUS * context.getXRange() / context.getWidth();

            context.setMinX(context.getMinX() - offset);
            context.setMaxX(context.getMaxX() + offset);
            context.setMinY(context.getMinY() - offset);
            context.setMaxY(context.getMaxY() + offset);
        }

        context.notifyBSplineListeners();
    }
}
