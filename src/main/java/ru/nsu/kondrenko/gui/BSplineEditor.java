package ru.nsu.kondrenko.gui;

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

    public BSplineEditor(BSplineEditorContext context) {
        final BSplineMouseController controller = new BSplineMouseController(context);

        this.context = context;
        context.addListener(this);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 100));
        addMouseListener(controller);
        addMouseMotionListener(controller);

        addKeyListener(controller);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                context.setWidth(e.getComponent().getWidth());
                context.setHeight(e.getComponent().getHeight());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCurvePoints(g);
        //drawBSpline(g);
    }

    private void drawCurvePoints(Graphics g) {
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = p.getMousePoint();
            g.drawOval(
                    mousePoint.getX() - CURVE_POINT_RADIUS,
                    mousePoint.getY() - CURVE_POINT_RADIUS,
                    CURVE_POINT_DIAMETER,
                    CURVE_POINT_DIAMETER
            );
        }
    }

    private void drawBSpline(Graphics g) {
        final int pointsCount = context.getPoints().size();
        final double step = 1.0 / context.getPolylinesNumber();

        final double[][] matrixValues = {
                {-1.0, 3.0, -3.0, 1.0},
                {3.0, -6.0, 3.0, 0.0},
                {-3.0, 0.0, 3.0, 0.0},
                {1.0, 4.0, 1.0, 0.0}
        };
        final SimpleMatrix matrix = new SimpleMatrix(matrixValues).divide(6);

        final double[] tValues = {0.0, 0.0, 0.0, 1.0};
        final SimpleMatrix tMatrix = new SimpleMatrix(tValues);
        tMatrix.reshape(1, 4);

        final var points = context.getPoints();
        for (int i = 0; i < pointsCount - 3; i++) {
            final double[][] pointsValues = {
                    {points.get(i).getRealPoint().getX(), points.get(i).getRealPoint().getY()},
                    {points.get(i + 1).getRealPoint().getX(), points.get(i + 1).getRealPoint().getY()},
                    {points.get(i + 2).getRealPoint().getX(), points.get(i + 2).getRealPoint().getY()},
                    {points.get(i + 3).getRealPoint().getX(), points.get(i + 3).getRealPoint().getY()},
            };
            SimpleMatrix pointsMatrix = new SimpleMatrix(pointsValues);
            pointsMatrix.reshape(2, 4);
            pointsMatrix = pointsMatrix.mult(matrix);
            pointsMatrix.reshape(4, 2);
            final SimpleMatrix prevMatrixPoint = tMatrix.mult(pointsMatrix);

            DoublePoint prevPoint = new DoublePoint(prevMatrixPoint.get(0), prevMatrixPoint.get(1));
            prevPoint.minus(new DoublePoint(360, 490)); //переходим в координаты центра
            prevPoint.scale(1); // учитываем приближение

            IntPoint prevMousePoint = Utils.realToMouseScale(prevPoint, context);

            for (int j = 1; j <= context.getPolylinesNumber(); j++){
                final double t = (j * step);
                final double[] currentTValues = {
                        t * t * t,
                        t * t,
                        t,
                        1
                };
                SimpleMatrix currentMatrix = new SimpleMatrix(currentTValues);
                SimpleMatrix currentMatrixPoint = currentMatrix.transpose().mult(pointsMatrix);
                DoublePoint currentPoint = new DoublePoint(currentMatrixPoint.get(0), currentMatrixPoint.get(1));
                currentPoint.minus(new DoublePoint(0, 0)); //переходим в координаты центра
                currentPoint.scale(1); // учитываем приближение
                final IntPoint currentMousePoint = Utils.realToMouseScale(currentPoint, context);

                g.drawLine(
                        prevMousePoint.getX(),
                        prevMousePoint.getY(),
                        currentMousePoint.getX(),
                        currentMousePoint.getY()
                );

                prevMousePoint = currentMousePoint;
            }
        }




//        for (int i = 0; i < pointsCount - 1; i++) {
//            final IntPoint p1 = context.getPoints().get(i).getMousePoint();
//            final IntPoint p2 = context.getPoints().get(i + 1).getMousePoint();
//            g.drawLine(
//                    p1.getX(),
//                    p1.getY(),
//                    p2.getX(),
//                    p2.getY()
//            );
//        }
    }

    @Override
    public void onPointsChange(BSplineEditorContext context) {
        repaint();
    }

    @Override
    public void onRangesChange(BSplineEditorContext context) {

    }

    @Override
    public void onDimensionsChange(BSplineEditorContext context) {

    }

    @Override
    public void onPolylinesNumberChange(BSplineEditorContext context) {

    }
}
