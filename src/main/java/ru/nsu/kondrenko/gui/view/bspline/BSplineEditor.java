package ru.nsu.kondrenko.gui.view.bspline;

import org.decimal4j.util.DoubleRounder;
import ru.nsu.kondrenko.gui.view.SwingUtils;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.BSplineListener;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.ContextUtils;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.IntPoint;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.util.List;

public class BSplineEditor extends JPanel implements BSplineListener {
    private static final int DIAMETER = Constants.PIVOT_POINT_RADIUS * 2;
    private static final int SPREAD = 5;
    private static final int AXES_POINTS_COUNT = 20;

    private final Context context;
    private final DoubleRounder rounder;

    public BSplineEditor(Context context,
                         MouseListener mouseListener,
                         MouseMotionListener mouseMotionListener,
                         MouseWheelListener mouseWheelListener,
                         ComponentListener componentListener) {
        this.context = context;
        this.rounder = new DoubleRounder(1);
        setBackground(Color.WHITE);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseMotionListener);
        addMouseWheelListener(mouseWheelListener);
        addComponentListener(componentListener);
        repaint();
    }

    @Override
    public void onBSplineContextChange(Context context) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCurvePoints(g);
        drawAxes(g);
        drawBSplinePointsConnection(g);
        drawBSpline(g);
    }

    private void drawCurvePoints(Graphics g) {
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = ContextUtils.realToScreenBSpline(
                    p,
                    context
            );
            g.drawOval(
                    mousePoint.getX() - Constants.PIVOT_POINT_RADIUS,
                    mousePoint.getY() - Constants.PIVOT_POINT_RADIUS,
                    DIAMETER,
                    DIAMETER
            );
        }
    }

    private void drawBSpline(Graphics g) {
        final List<Double2DPoint> bSplinePoints = context.getBSplinePoints();
        if (bSplinePoints.isEmpty()) {
            return;
        }

        IntPoint prevPoint = ContextUtils.realToScreenBSpline(
                bSplinePoints.get(0),
                context
        );

        for (int i = 1; i < bSplinePoints.size() - 1; i++) {
            final IntPoint currentPoint = ContextUtils.realToScreenBSpline(
                    bSplinePoints.get(i),
                    context
            );

            SwingUtils.drawLine(g, prevPoint, currentPoint);
            prevPoint = currentPoint;
        }
    }

    private void drawBSplinePointsConnection(Graphics g) {
        final List<Double2DPoint> points = context.getPoints();
        if (points.isEmpty()) {
            return;
        }

        final Color oldColor = g.getColor();
        g.setColor(Color.GREEN);
        IntPoint prevPoint = ContextUtils.realToScreenBSpline(
                points.get(0),
                context
        );

        for (int i = 0; i < points.size(); i++) {
            final IntPoint currentPoint = ContextUtils.realToScreenBSpline(
                    context.getPoints().get(i),
                    context
            );

            SwingUtils.drawLine(g, prevPoint, currentPoint);
            prevPoint = currentPoint;
        }

        g.setColor(oldColor);
    }

    private void drawAxes(Graphics g) {
        final IntPoint centerPoint = ContextUtils.realToScreenBSpline(
                new Double2DPoint(0, 0),
                context
        );
        final int yOfXAxes = centerPoint.getY();
        final int xOfYAxes = centerPoint.getX();
        g.drawLine(0, yOfXAxes, context.getBSplineWidth(), yOfXAxes);
        g.drawLine(xOfYAxes, 0, xOfYAxes, context.getBSplineHeight());

        final int width = getWidth();
        final int height = getHeight();
        final Graphics2D g2d = (Graphics2D) g;
        final int yCenter = centerPoint.getY();
        final int xCenter = centerPoint.getX();
        final int step = width / AXES_POINTS_COUNT;
        for (int x = xCenter + step; x <= width - step; x += step) {
            drawOXAxePoint(g2d, new IntPoint(x, yCenter));
        }
        for (int x = xCenter - step; x >= step; x -= step) {
            drawOXAxePoint(g2d, new IntPoint(x, yCenter));
        }
        for (int y = yCenter + step; y <= height - step; y += step) {
            drawOYAxePoint(g2d, new IntPoint(xCenter, y));
        }
        for (int y = yCenter - step; y >= step; y -= step) {
            drawOYAxePoint(g2d, new IntPoint(xCenter, y));
        }
    }

    private void drawOXAxePoint(Graphics2D graphics2D, IntPoint point) {
        final int x = point.getX();
        final int y = point.getY();
        final Double2DPoint realPoint = ContextUtils.screenToRealBspline(
                new IntPoint(x, y),
                context
        );
        graphics2D.drawLine(x, y - SPREAD, x, y + SPREAD);
        final String label = String.valueOf(rounder.round(realPoint.getX()));
        graphics2D.drawString(label, x - label.length() * 3, y - 10); // Значения подобраны эмпирически
    }

    private void drawOYAxePoint(Graphics2D graphics2D, IntPoint point) {
        final int x = point.getX();
        final int y = point.getY();
        final Double2DPoint realPoint = ContextUtils.screenToRealBspline(
                new IntPoint(x, y),
                context
        );
        graphics2D.drawLine(x - SPREAD, y, x + SPREAD, y);
        final String label = String.valueOf(rounder.round(realPoint.getY()));
        graphics2D.drawString(label, x + 10, y + 5); // Значения подобраны эмпирически
    }
}
