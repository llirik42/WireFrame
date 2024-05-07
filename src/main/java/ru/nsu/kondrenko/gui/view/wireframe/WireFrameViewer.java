package ru.nsu.kondrenko.gui.view.wireframe;

import ru.nsu.kondrenko.gui.view.SwingUtils;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.ContextUtils;
import ru.nsu.kondrenko.model.context.WireframeListener;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.Double4DPoint;
import ru.nsu.kondrenko.model.dto.IntPoint;
import ru.nsu.kondrenko.model.wireframe.WireframeScreenPoint;
import ru.nsu.kondrenko.model.wireframe.WireframeUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class WireFrameViewer extends JPanel implements WireframeListener {
    private final Context context;

    private static final Stroke AXES_STROKE = new BasicStroke(Constants.AXES_STROKE);
    private static final Color X_AXIS_COLOR = Color.RED;
    private static final Color Y_AXIS_COLOR = Color.GREEN;
    private static final Color Z_AXIS_COLOR = Color.BLUE;

    @Override
    public void onWireframeChange(Context context) {
        repaint();
    }

    public WireFrameViewer(Context context,
                           MouseListener mouseListener,
                           MouseMotionListener mouseMotionListener,
                           MouseWheelListener mouseWheelListener) {
        this.context = context;
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseMotionListener);
        addMouseWheelListener(mouseWheelListener);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                final int newWidth = e.getComponent().getWidth();
                final int newHeight = e.getComponent().getHeight();
                final double ratio = 1.0 * newHeight / newWidth;
                context.setWireframeWidth(newWidth);
                context.setWireframeHeight(newHeight);
                context.setWireframeMinY(context.getWireframeMinX() * ratio);
                context.setWireframeMaxY(context.getWireframeMaxX() * ratio);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawXYZ((Graphics2D) graphics);

        final List<Double2DPoint> bSplinePoints = context.getBSplinePoints();
        final int bSplinePointsNumber = bSplinePoints.size();

        if (bSplinePointsNumber == 0) {
            return;
        }

        final int generatricesNumber = context.getGeneratricesNumber();
        final int circleSegmentsNumber = context.getCircleSegmentsNumber();

        final List<Double4DPoint> generatricesPoints = context.getGeneratricesPoints();
        final List<Double4DPoint> circlesPoints = context.getCirclesPoints();

        final List<List<Double4DPoint>> normalized = WireframeUtils.normalizePoints(
                generatricesPoints,
                circlesPoints
        );
        final List<Double4DPoint> normalizedGeneratricesPoints = normalized.get(0);
        final List<Double4DPoint> normalizedCirclesPoints = normalized.get(1);

        final int allSegmentsNumber = generatricesNumber * circleSegmentsNumber;

        for (int i = 0; i < context.getGeneratricesNumber(); i++) {
            final WireframeScreenPoint startWireframePoint = ContextUtils.calculateScreenPoint(
                    normalizedGeneratricesPoints.get(i * bSplinePointsNumber),
                    context
            );
            drawF(
                    graphics,
                    startWireframePoint,
                    normalizedGeneratricesPoints,
                    i * bSplinePointsNumber,
                    bSplinePointsNumber
            );
        }

        int stop = bSplinePointsNumber / context.getPolylinesNumber();
        if (context.getPolylinesNumber() > 1) {
            stop++;
        }

        for (int i = 0; i < stop; i++) {
            final WireframeScreenPoint startWireframePoint = ContextUtils.calculateScreenPoint(
                    normalizedCirclesPoints.get(allSegmentsNumber * i),
                    context
            );
            final float distance = startWireframePoint.distanceToCamera();
            final IntPoint lastPoint = drawF(
                    graphics,
                    startWireframePoint,
                    normalizedCirclesPoints,
                    allSegmentsNumber * i,
                    allSegmentsNumber
            );
            graphics.setColor(new Color(
                    distance,
                    distance,
                    distance
            ));
            SwingUtils.drawLine(
                    graphics,
                    lastPoint,
                    startWireframePoint.screenPoint()
            );
        }
    }

    private void drawXYZ(Graphics2D graphics2D) {
        final List<IntPoint> axesPoints = WireframeUtils.calculateAxesPoints(
                context.getRotationMatrix(),
                context.getWireframeWidth(),
                context.getWireframeHeight()
        );

        final Color oldColor = graphics2D.getColor();
        final Stroke oldStroke = graphics2D.getStroke();

        final IntPoint centerPoint = axesPoints.get(0);
        final IntPoint xPoint = axesPoints.get(1);
        final IntPoint yPoint = axesPoints.get(2);
        final IntPoint zPoint = axesPoints.get(3);

        graphics2D.setStroke(AXES_STROKE);
        graphics2D.setColor(X_AXIS_COLOR);
        SwingUtils.drawLine(
                graphics2D,
                centerPoint,
                xPoint
        );

        graphics2D.setColor(Y_AXIS_COLOR);
        SwingUtils.drawLine(
                graphics2D,
                centerPoint,
                yPoint
        );

        graphics2D.setColor(Z_AXIS_COLOR);
        SwingUtils.drawLine(
                graphics2D,
                centerPoint,
                zPoint
        );

        graphics2D.setStroke(oldStroke);
        graphics2D.setColor(oldColor);
    }

    private IntPoint drawF(Graphics graphics, WireframeScreenPoint startPoint, List<Double4DPoint> points, int offset, int stop) {
        IntPoint prevPointOnScreen = startPoint.screenPoint();

        for (int j = 1; j < stop; j++) {
            final WireframeScreenPoint currentPoint = ContextUtils.calculateScreenPoint(
                    points.get(offset + j),
                    context
            );

            IntPoint currentPointsOnScreen = currentPoint.screenPoint();

            graphics.setColor(new Color(
                    currentPoint.distanceToCamera(),
                    currentPoint.distanceToCamera(),
                    currentPoint.distanceToCamera()
            ));
            SwingUtils.drawLine(
                    graphics,
                    prevPointOnScreen,
                    currentPointsOnScreen
            );
            prevPointOnScreen = currentPointsOnScreen;
        }

        return prevPointOnScreen;
    }
}
