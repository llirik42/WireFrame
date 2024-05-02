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

    public static List<DoublePoint> calculateBSplinePoints(List<DoublePoint> points, int polylinesNumber) {
        final List<DoublePoint> result = new ArrayList<>();

        final double step = 1.0 / polylinesNumber;

        for (int i = 0; i < points.size() - 3; i++) {
            final DoublePoint p1 = points.get(i);
            final DoublePoint p2 = points.get(i + 1);
            final DoublePoint p3 = points.get(i + 2);
            final DoublePoint p4 = points.get(i + 3);

            final double[][] pointsValues = {
                    {p1.getX(), p1.getY()},
                    {p2.getX(), p2.getY()},
                    {p3.getX(), p3.getY()},
                    {p4.getX(), p4.getY()},
            };
            final SimpleMatrix pointsMatrix = MATRIX.mult(new SimpleMatrix(pointsValues));
            final SimpleMatrix prevMatrixPoint = T_MATRIX.mult(pointsMatrix);
            final DoublePoint firstPoint = new DoublePoint(
                    prevMatrixPoint.get(0),
                    prevMatrixPoint.get(1)
            );
            result.add(firstPoint);

            for (int j = 1; j <= polylinesNumber; j++) {
                final double t = j * step;
                final double[][] currentTValues = {{
                        t * t * t,
                        t * t,
                        t,
                        1
                }};
                final SimpleMatrix currentTMatrix = new SimpleMatrix(currentTValues);
                final SimpleMatrix currentMatrixPoint = currentTMatrix.mult(pointsMatrix);
                final DoublePoint currentPoint = new DoublePoint(
                        currentMatrixPoint.get(0),
                        currentMatrixPoint.get(1)
                );
                result.add(currentPoint);
            }
        }

        return result;
    }
}
