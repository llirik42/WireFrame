package ru.nsu.kondrenko.gui;

import org.decimal4j.util.DoubleRounder;
import ru.nsu.kondrenko.controller.BSplineMouseController;
import ru.nsu.kondrenko.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BSplineEditor extends JPanel implements ContextListener {
    private static final int CURVE_POINT_RADIUS = 10;
    private static final int CURVE_POINT_DIAMETER = 2 * CURVE_POINT_RADIUS;
    private final Context context;
    private final DoubleRounder rounder;

    public BSplineEditor(Context context, BSplineMouseController controller) {
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
        drawBSplinePointsConnection(g);
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
        for (int i = 0; i < context.getBSplinePoints().size() - 1; i++) {
            final IntPoint p1 = Utils.realToMouseScale(context.getBSplinePoints().get(i), context);
            final IntPoint p2 = Utils.realToMouseScale(context.getBSplinePoints().get(i + 1), context);

            g.drawLine(
                    p1.getX(),
                    p1.getY(),
                    p2.getX(),
                    p2.getY()
            );
        }
    }

    private void drawBSplinePointsConnection(Graphics g) {
        final Color oldColor = g.getColor();

        g.setColor(Color.GREEN);

        for (int i = 0; i < context.getPoints().size() - 1; i++) {
            final IntPoint p1 = Utils.realToMouseScale(context.getPoints().get(i), context);
            final IntPoint p2 = Utils.realToMouseScale(context.getPoints().get(i + 1), context);

            g.drawLine(
                    p1.getX(),
                    p1.getY(),
                    p2.getX(),
                    p2.getY()
            );
        }

        g.setColor(oldColor);
    }

    @Override
    public void onContextChange(Context context) {
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
