package ru.nsu.kondrenko.model.wireframe;

import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.Double4DPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class WireframeUtils {
    private static final double[][] CAMERA_MATRIX_VALUES = {
            {1, 0, 0, 0},
            {0, Constants.CAMERA_MATRIX_VALUE, 0, 0},
            {0, 0, Constants.CAMERA_MATRIX_VALUE, 0},
            {1, 0, 0, Constants.CAMERA_DISTANCE}
    };

    private WireframeUtils() {
    }

    public static double[] findMinMax(List<Double4DPoint> generatricesPoints,
                                      List<Double4DPoint> circlesPoints) {
        final List<Double4DPoint> allPoints = Stream.concat(
                generatricesPoints.stream(),
                circlesPoints.stream()
        ).toList();

        final Double4DPoint firstPoint = allPoints.get(0);
        double minX = firstPoint.getX();
        double maxX = minX;
        double minY = firstPoint.getY();
        double maxY = minY;
        double minZ = firstPoint.getZ();
        double maxZ = minZ;

        for (final var p : allPoints) {
            final double x = p.getX();
            final double y = p.getY();
            final double z = p.getZ();
            minX = Double.min(minX, x);
            maxX = Double.max(maxX, x);
            minY = Double.min(minY, y);
            maxY = Double.max(maxY, y);
            minZ = Double.min(minZ, z);
            maxZ = Double.max(maxZ, z);
        }

        return new double[]{minX, maxX, minY, maxY, minZ, maxZ};
    }

    public static List<Double4DPoint> calculateGeneratricesPoints(int generatricesNumber,
                                                                  List<Double2DPoint> bSplinePoints) {
        final List<Double4DPoint> result = new ArrayList<>();

        final double rotationDelta = (2 * Math.PI) / (generatricesNumber);
        for (int i = 0; i < generatricesNumber; i++) {
            final double angle = rotationDelta * i;
            final double sin = Math.sin(angle);
            final double cos = Math.cos(angle);

            for (final var it : bSplinePoints) {
                double x = it.getY() * cos;
                double y = it.getY() * sin;
                result.add(new Double4DPoint(x, y, it.getX(), 1));
            }
        }

        return result;
    }

    public static List<Double4DPoint> calculateCirclesPoints(int polylinesNumber,
                                                             int generatricesNumber,
                                                             int circleSegmentsNumber,
                                                             List<Double2DPoint> bSplinePoints) {
        final List<Double4DPoint> result = new ArrayList<>();

        final int bSplinePointsNumber = bSplinePoints.size();
        final int allSegmentsNumber = generatricesNumber * circleSegmentsNumber;
        final double segmentsDelta = (2 * Math.PI) / allSegmentsNumber;
        final int stop = polylinesNumber == 1 ? bSplinePointsNumber : bSplinePointsNumber + 1;
        for (int i = 0; i < stop; i += polylinesNumber) {
            final Double2DPoint points = bSplinePoints.get(i);
            final double pX = points.getX();
            final double pY = points.getY();

            for (int j = 0; j < allSegmentsNumber; j++) {
                final double angle = segmentsDelta * j;
                final double sin = Math.sin(angle);
                final double cos = Math.cos(angle);
                final double x = pY * cos;
                final double y = pY * sin;
                result.add(new Double4DPoint(x, y, pX, 1));
            }
        }

        return result;
    }

    public static SimpleMatrix createRotationMatrix(int mouseDeltaX, int mouseDeltaY) {
        SimpleMatrix rotationMatrix = SimpleMatrix.identity(4);

        if (mouseDeltaX != 0) {
            final double xAngle = 0.5 * Math.PI / 100 * mouseDeltaX;
            final double sin = Math.sin(xAngle);
            final double cos = Math.cos(xAngle);
            final double[][] matrixData = {
                    {cos, -sin, 0, 0},
                    {sin, cos, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            };
            rotationMatrix = new SimpleMatrix(matrixData).mult(rotationMatrix);
        }

        if (mouseDeltaY != 0) {
            final double yAngle = 0.5 * Math.PI / 100 * mouseDeltaY;
            final double sin = Math.sin(yAngle);
            final double cos = Math.cos(yAngle);
            final double[][] matrixData = {
                    {cos, 0, sin, 0},
                    {0, 1, 0, 0},
                    {-sin, 0, cos, 0},
                    {0, 0, 0, 1}
            };
            rotationMatrix = new SimpleMatrix(matrixData).mult(rotationMatrix);
        }

        return rotationMatrix;
    }

    public static SimpleMatrix createDefaultRotationMatrix() {
        return SimpleMatrix.identity(4);
    }

    public static SimpleMatrix createDefaultCameraMatrix() {
        return new SimpleMatrix(CAMERA_MATRIX_VALUES);
    }
}
