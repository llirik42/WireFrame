package ru.nsu.kondrenko.gui;

import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WireFrameViewer extends JPanel {
    private final Context context;

    public WireFrameViewer(Context context) {
        this.context = context;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (context.getBSplinePoints().isEmpty()) {
            return;
        }

        final int generatricesNumber = context.getGeneratricesNumber();
        final double rotationDelta = (2 * Math.PI) / (generatricesNumber);
        final int bSplinePointsNumber = context.getBSplinePoints().size();

        final java.util.List<Double4DPoint> points = new ArrayList<>();
        final java.util.List<Double4DPoint> circles = new ArrayList<>();

        // 3D-каркас
        for (int i = 0; i < generatricesNumber; i++) {
            final double angle = rotationDelta * i;
            final double sin = Math.sin(angle);
            final double cos = Math.cos(angle);

            for (final var it : context.getBSplinePoints()) {
                double x = it.y() * cos;
                double y = it.y() * sin;
                points.add(new Double4DPoint(x, y, it.x(), 1));
            }
        }

        // Дуги
        final int allSegmentsNumber = generatricesNumber * context.getCircleSegmentsNumber();
        final double segmentsDelta = (2 * Math.PI) / allSegmentsNumber;
        for (final var p : context.getBSplinePoints()) {
            for (int j = 0; j < allSegmentsNumber; j++) {
                final double angle = segmentsDelta * j;
                final double sin = Math.sin(angle);
                final double cos = Math.cos(angle);
                final double x = p.y() * cos;
                final double y = p.y() * sin;
                circles.add(new Double4DPoint(x, y, p.x(), 1));
            }
        }

        for (final var c : circles) {
            System.out.println(c);
        }
        System.out.println();

        final java.util.List<java.util.List<Double4DPoint>> normalized = normalize(points, circles);
        final java.util.List<Double4DPoint> normalizedPoints = normalized.get(0);
        final List<Double4DPoint> normalizedCircles = normalized.get(1);

        for (int i = 0; i < context.getGeneratricesNumber(); i++) {
            IntPoint prevPointOnScreen = calculatePointOnScreen(normalizedPoints.get(i * bSplinePointsNumber), context);

            for (int j = 1; j < bSplinePointsNumber; j++) {
                IntPoint currentPointsOnScreen = calculatePointOnScreen(
                        normalizedPoints.get(i * bSplinePointsNumber + j),
                        context
                );
                g.drawLine(
                        prevPointOnScreen.getX(),
                        prevPointOnScreen.getY(),
                        currentPointsOnScreen.getX(),
                        currentPointsOnScreen.getY()
                );
                prevPointOnScreen = currentPointsOnScreen;
            }
        }

        for (int i = 0; i < bSplinePointsNumber; i++) {
            IntPoint startPoint = calculatePointOnScreen(normalizedCircles.get(allSegmentsNumber * i), context);
            IntPoint prevPointOnScreen = startPoint;

            for (int j = 1; j < allSegmentsNumber; j++) {
                IntPoint currentPointsOnScreen = calculatePointOnScreen(normalizedCircles.get(allSegmentsNumber * i + j), context);
                g.drawLine(
                        prevPointOnScreen.getX(),
                        prevPointOnScreen.getY(),
                        currentPointsOnScreen.getX(),
                        currentPointsOnScreen.getY()
                );
                prevPointOnScreen = currentPointsOnScreen;
            }

            g.drawLine(
                    prevPointOnScreen.getX(),
                    prevPointOnScreen.getY(),
                    startPoint.getX(),
                    startPoint.getY()
            );
        }
    }

    private List<List<Double4DPoint>> normalize(List<Double4DPoint> points, List<Double4DPoint> circles) {
        final List<Double4DPoint> allPoints = Stream.concat(points.stream(), circles.stream()).toList();

        double minX = points.get(0).x();
        double maxX = minX;
        double minY = points.get(0).y();
        double maxY = minY;
        double minZ = points.get(0).z();
        double maxZ = minZ;

        for (final var p : allPoints) {
            minX = Double.min(minX, p.x());
            maxX = Double.max(maxX, p.x());
            minY = Double.min(minY, p.y());
            maxY = Double.max(maxY, p.y());
            minZ = Double.min(minZ, p.z());
            maxZ = Double.max(maxZ, p.z());
        }

        final double xRange = maxX - minX;
        final double yRange = maxY - minY;
        final double zRange = maxZ - minZ;
        final double maxRange = Double.max(xRange, Double.max(yRange, zRange));
        final double xCenter = (maxX + minX) / 2.0;
        final double yCenter = (maxY + minY) / 2.0;
        final double zCenter = (maxZ + minZ) / 2.0;

        final Double4DPoint centerPoint = new Double4DPoint(xCenter, yCenter, zCenter, 0);

        final List<List<Double4DPoint>> res = new ArrayList<>();

        final List<Double4DPoint> normalizedPoints = new ArrayList<>();
        for (final var p : points) {
            normalizedPoints.add(p.minus(centerPoint).divide(maxRange));
        }

        final List<Double4DPoint> normalizedCircles = new ArrayList<>();
        for (final var c : circles) {
            normalizedCircles.add(c.minus(centerPoint).divide(maxRange));
        }

        res.add(normalizedPoints);
        res.add(normalizedCircles);

        return res;
    }

    private IntPoint calculatePointOnScreen(Double4DPoint point, Context context){
        final double phi = Math.PI / 6;
        double sin = Math.sin(phi);
        double cos = Math.cos(phi);

        final double[][] rotationMatrixValues = {
                {1, 0, 0, 0},
                {0, cos, -sin, 0},
                {0, sin, cos, 0},
                {0, 0, 0, 1}
        };

        final double[] pointValues = {point.x(), point.y(), point.z(), point.t()};

        final SimpleMatrix rotationMatrix = new SimpleMatrix(rotationMatrixValues);
        final SimpleMatrix afterRotationMatrix = rotationMatrix.mult(new SimpleMatrix(pointValues));

        final double[][] cameraMatrixData = {
                {1, 0, 0, 0},
                {0, 2000, 0, 0},
                {0, 0, 2000, 0},
                {1, 0, 0, 10}
        };
        final SimpleMatrix cameraMatrix = new SimpleMatrix(cameraMatrixData);

        final SimpleMatrix pMatrix = cameraMatrix.mult(afterRotationMatrix);
        final Double4DPoint p = new Double4DPoint(
                pMatrix.get(0),
                pMatrix.get(1),
                pMatrix.get(2),
                pMatrix.get(3)
        );

        final double distance = p.t();
        final Double2DPoint res = new Double2DPoint(
                p.y() / distance,
                p.z() / distance
        );

        return Utils.realToMouseScale(res, context);
    }
}
