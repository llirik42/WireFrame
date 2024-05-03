package ru.nsu.kondrenko.model;

import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.List;

public final class BSplineUtils {
    private static final double[][] MATRIX_VALUES = {
            {-1.0, 3.0, -3.0, 1.0},
            {3.0, -6.0, 3.0, 0.0},
            {-3.0, 0.0, 3.0, 0.0},
            {1.0, 4.0, 1.0, 0.0}
    };

    private static final double[] T_MATRIX_VALUES = {0.0, 0.0, 0.0, 1.0};

    private static final SimpleMatrix MATRIX = new SimpleMatrix(MATRIX_VALUES).divide(6);

    private static final SimpleMatrix T_MATRIX = new SimpleMatrix(T_MATRIX_VALUES).transpose();

    private BSplineUtils() {
    }

    public static List<Double2DPoint> calculateBSplinePoints(List<Double2DPoint> points, int polylinesNumber) {
        final List<Double2DPoint> result = new ArrayList<>();

        final double step = 1.0 / polylinesNumber;

        final int pointsCount = points.size();
        for (int i = 0; i < pointsCount - 3; i++) {
            final Double2DPoint p1 = points.get(i);
            final Double2DPoint p2 = points.get(i + 1);
            final Double2DPoint p3 = points.get(i + 2);
            final Double2DPoint p4 = points.get(i + 3);

            final double[][] pointsValues = {
                    {p1.x(), p1.y()},
                    {p2.x(), p2.y()},
                    {p3.x(), p3.y()},
                    {p4.x(), p4.y()},
            };
            final SimpleMatrix pointsMatrix = MATRIX.mult(new SimpleMatrix(pointsValues));
            final SimpleMatrix prevMatrixPoint = T_MATRIX.mult(pointsMatrix);
            final Double2DPoint firstPoint = new Double2DPoint(
                    prevMatrixPoint.get(0),
                    prevMatrixPoint.get(1)
            );
            result.add(firstPoint);

            final int delta = i == pointsCount - 4 ? 1 : 0;

            for (int j = 1; j < polylinesNumber + delta; j++) {
                final double t = j * step;
                final double[][] currentTValues = {{
                        t * t * t,
                        t * t,
                        t,
                        1
                }};
                final SimpleMatrix currentTMatrix = new SimpleMatrix(currentTValues);
                final SimpleMatrix currentMatrixPoint = currentTMatrix.mult(pointsMatrix);
                final Double2DPoint currentPoint = new Double2DPoint(
                        currentMatrixPoint.get(0),
                        currentMatrixPoint.get(1)
                );
                result.add(currentPoint);
            }
        }

        return result;
    }
}
