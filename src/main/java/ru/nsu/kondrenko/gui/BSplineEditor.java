package ru.nsu.kondrenko.gui;

import org.decimal4j.util.DoubleRounder;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.controller.BSplineMouseController;
import ru.nsu.kondrenko.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BSplineEditor extends JPanel implements BSplineEditorContextListener {
    private static final int CURVE_POINT_RADIUS = 10;
    private static final int CURVE_POINT_DIAMETER = 2 * CURVE_POINT_RADIUS;
    private final BSplineEditorContext context;
    private final DoubleRounder rounder;

    public BSplineEditor(BSplineEditorContext context, BSplineMouseController controller) {
        this.context = context;
        this.rounder = new DoubleRounder(1);
        context.addListener(this);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 100));
        addMouseListener(controller);
        addMouseMotionListener(controller);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                context.setWidth(e.getComponent().getWidth());
                context.setHeight(e.getComponent().getHeight());
            }
        });

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCurvePoints(g);
        drawAxes(g);
        drawBSpline(g);
    }

    private void drawCurvePoints(Graphics g) {
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = Utils.realToMouseScale(p, context);
            g.drawOval(
                    mousePoint.getX() - CURVE_POINT_RADIUS,
                    mousePoint.getY() - CURVE_POINT_RADIUS,
                    CURVE_POINT_DIAMETER,
                    CURVE_POINT_DIAMETER
            );
        }
    }

    private void drawAxes(Graphics g) {
        final IntPoint centerPoint = Utils.realToMouseScale(new DoublePoint(0, 0), context);
        final int yOfXAxes = centerPoint.getY();
        final int xOfYAxes = centerPoint.getX();
        g.drawLine(0, yOfXAxes, context.getWidth(), yOfXAxes);
        g.drawLine(xOfYAxes, 0, xOfYAxes, context.getHeight());

        final int width = getWidth();
        final int height = getHeight();
        final Graphics2D g2d = (Graphics2D)g;
        final int yCenter = centerPoint.getY();
        final int xCenter = centerPoint.getX();
        final int axesPointCount = 20;
        final int step = width / axesPointCount;
        final int spread = 5;
        for (int x = xCenter + step; x < width; x += step) {
            drawOXAxePoint(g2d, new IntPoint(x, yCenter), spread);
        }
        for (int x = xCenter - step; x > 0; x -= step) {
            drawOXAxePoint(g2d, new IntPoint(x, yCenter), spread);
        }
        for (int y = yCenter + step; y < height; y += step) {
            drawOYAxePoint(g2d, new IntPoint(xCenter, y), spread);
        }
        for (int y = yCenter - step; y > 0; y -= step) {
            drawOYAxePoint(g2d, new IntPoint(xCenter, y), spread);
        }
    }

    private void drawBSpline(Graphics g) {
        final double[][] splineCoefficientsValues = {
                {-1.0, 3.0, -3.0, 1.0},
                {3.0, -6.0, 3.0, 0.0},
                {-3.0, 0.0, 3.0, 0.0},
                {1.0, 4.0, 1.0, 0.0}
        };
        final SimpleMatrix mSpline = new SimpleMatrix(splineCoefficientsValues);
        mSpline.divide(6);

        final double[] tMatrixValues = {0.0, 0.0, 0.0, 1.0};
        final SimpleMatrix tMatrix = new SimpleMatrix(tMatrixValues);
        tMatrix.reshape(1, 4);
        final double step = 1.0 / context.getPolylinesNumber();

        for (int i = 0; i < context.getPoints().size() - 3; i++) {
            final DoublePoint p1 = context.getPoints().get(i);
            final DoublePoint p2 = context.getPoints().get(i + 1);
            final DoublePoint p3 = context.getPoints().get(i + 2);
            final DoublePoint p4 = context.getPoints().get(i + 3);

            final double[][] pointsValues = {
                    {p1.getX(), p1.getY()},
                    {p2.getX(), p2.getY()},
                    {p3.getX(), p3.getY()},
                    {p4.getX(), p4.getY()},
            };
            final SimpleMatrix pointsMatrix = mSpline.mult(new SimpleMatrix(pointsValues));
            System.out.println(pointsMatrix);
            pointsMatrix.reshape(4, 2);
            final SimpleMatrix prevMatrixPoint = tMatrix.mult(pointsMatrix);
            DoublePoint prevPoint = new DoublePoint(prevMatrixPoint.get(0), prevMatrixPoint.get(1));

            for (int j = 1; j <= context.getPolylinesNumber(); j++) {
                final double t = j * step;
                final double[] currentTValues = {
                        t * t * t,
                        t * t,
                        t,
                        1
                };
                final SimpleMatrix currentTMatrix = new SimpleMatrix(currentTValues);
                currentTMatrix.reshape(1, 4);
                final SimpleMatrix currentMatrixPoint = currentTMatrix.mult(pointsMatrix);
                final DoublePoint currentPoint = new DoublePoint(currentMatrixPoint.get(0), currentMatrixPoint.get(1));

                final IntPoint prevMousePoint = Utils.realToMouseScale(prevPoint, context);
                final IntPoint currentMousePoint = Utils.realToMouseScale(currentPoint, context);
                prevPoint = currentPoint;

                g.drawLine(
                        prevMousePoint.getX(),
                        prevMousePoint.getY(),
                        currentMousePoint.getX(),
                        currentMousePoint.getY()
                );
            }
        }
    }

    @Override
    public void onContextChange(BSplineEditorContext context) {
        repaint();
    }

    private void drawOXAxePoint(Graphics2D graphics2D, IntPoint point, int spread) {
        final int x = point.getX();
        final int y = point.getY();
        final DoublePoint realPoint = Utils.mouseToRealScale(new IntPoint(x, y), context);
        graphics2D.drawLine(x, y - spread, x, y + spread);
        final String label = String.valueOf(rounder.round(realPoint.getX()));
        graphics2D.drawString(label, x - label.length() * 3, y - 10);
    }

    private void drawOYAxePoint(Graphics2D graphics2D, IntPoint point, int spread) {
        final int x = point.getX();
        final int y = point.getY();
        final DoublePoint realPoint = Utils.mouseToRealScale(new IntPoint(x, y), context);
        graphics2D.drawLine(x - spread, y, x + spread, y);
        final String label = String.valueOf(rounder.round(realPoint.getY()));
        graphics2D.drawString(label, x + 10, y + 5);
    }
}
