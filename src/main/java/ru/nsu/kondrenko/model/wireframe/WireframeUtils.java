package ru.nsu.kondrenko.model.wireframe;

import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.CommonUtils;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.Double4DPoint;
import ru.nsu.kondrenko.model.dto.IntPoint;

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

    private static final double[][] XYZ_CAMERA_MATRIX_VALUES = {
            {1, 0, 0, 0},
            {0, Constants.XYZ_CAMERA_MATRIX_VALUE, 0, 0},
            {0, 0, Constants.XYZ_CAMERA_MATRIX_VALUE, 0},
            {1, 0, 0, Constants.CAMERA_DISTANCE}
    };

    private static final SimpleMatrix XYZ_CAMERA_MATRIX = new SimpleMatrix(XYZ_CAMERA_MATRIX_VALUES);

    private static final SimpleMatrix CENTER_MATRIX = new SimpleMatrix(new double[]{0, 0, 0, 1});

    private static final SimpleMatrix X_MATRIX = new SimpleMatrix(new double[]{1, 0, 0, 1});

    private static final SimpleMatrix Y_MATRIX = new SimpleMatrix(new double[]{0, 1, 0, 1});

    private static final SimpleMatrix Z_MATRIX = new SimpleMatrix(new double[]{0, 0, 1, 1});

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

    public static List<IntPoint> calculateAxesPoints(SimpleMatrix rotationMatrix,
                                                     int wireframeWidth,
                                                     int wireframeHeight) {
        final SimpleMatrix centerPointMatrix = XYZ_CAMERA_MATRIX.mult(rotationMatrix.mult(CENTER_MATRIX));
        final SimpleMatrix xMatrix = XYZ_CAMERA_MATRIX.mult(rotationMatrix.mult(X_MATRIX));
        final SimpleMatrix yMatrix = XYZ_CAMERA_MATRIX.mult(rotationMatrix.mult(Y_MATRIX));
        final SimpleMatrix zMatrix = XYZ_CAMERA_MATRIX.mult(rotationMatrix.mult(Z_MATRIX));

        final int xPos = wireframeWidth - Constants.AXES_OFFSET;
        final int yPos = wireframeHeight - Constants.AXES_OFFSET;

        final IntPoint centerPointOnScreen = calculatePointOfAxis(centerPointMatrix, xPos, yPos);
        final IntPoint xPointOnScreen = calculatePointOfAxis(xMatrix, xPos, yPos);
        final IntPoint yPointOnScreen = calculatePointOfAxis(yMatrix, xPos, yPos);
        final IntPoint zPointOnScreen = calculatePointOfAxis(zMatrix, xPos, yPos);

        return List.of(
                centerPointOnScreen,
                xPointOnScreen,
                yPointOnScreen,
                zPointOnScreen
        );
    }

    public static List<List<Double4DPoint>> normalizePoints(List<Double4DPoint> generatricesPoints,
                                                            List<Double4DPoint> circlesPoints) {
        final double[] minMaxValues = WireframeUtils.findMinMax(generatricesPoints, circlesPoints);
        final double minX = minMaxValues[0];
        final double maxX = minMaxValues[1];
        final double minY = minMaxValues[2];
        final double maxY = minMaxValues[3];
        final double minZ = minMaxValues[4];
        final double maxZ = minMaxValues[5];

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
        for (final var p : generatricesPoints) {
            normalizedPoints.add(p.minus(centerPoint).divide(maxRange));
        }

        final List<Double4DPoint> normalizedCircles = new ArrayList<>();
        for (final var c : circlesPoints) {
            normalizedCircles.add(c.minus(centerPoint).divide(maxRange));
        }

        res.add(normalizedPoints);
        res.add(normalizedCircles);

        return res;
    }

    public static WireframeScreenPoint calculatePointOnScreen(Double4DPoint point,
                                                              SimpleMatrix cameraMatrix,
                                                              SimpleMatrix rotationMatrix,
                                                              int screenWidth,
                                                              int screenHeight,
                                                              double minX,
                                                              double maxX,
                                                              double minY,
                                                              double maxY) {
        final double[] pointValues = {point.getX(), point.getY(), point.getZ(), point.getT()};

        final SimpleMatrix afterRotationMatrix = rotationMatrix.mult(new SimpleMatrix(pointValues));
        final SimpleMatrix pMatrix = cameraMatrix.mult(afterRotationMatrix);
        final Double4DPoint p = new Double4DPoint(
                pMatrix.get(0),
                pMatrix.get(1),
                pMatrix.get(2),
                pMatrix.get(3)
        );

        final double distance = p.getT();

        final Double2DPoint res = new Double2DPoint(
                p.getY() / distance,
                p.getZ() / distance
        );

        /*
        Значения подобраны эмпирически, исходя из соображений, что
        функция y(x) = ax + b должна как-то переводить отрезок [-1; 1] в отрезок [0; 1]
         */

        final double a = 0.35;
        final double b = 0.75;

        return new WireframeScreenPoint(
                CommonUtils.realToScreen(
                        res,
                        screenWidth,
                        screenHeight,
                        minX,
                        maxX,
                        minY,
                        maxY
                ),
                (float) (Math.pow(afterRotationMatrix.get(0, 0) * a + b, 2))
        );
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

    private static IntPoint calculatePointOfAxis(SimpleMatrix axisMatrix, int xPos, int yPos) {
        final double tmp = axisMatrix.get(3);
        return new IntPoint(
                (int) (axisMatrix.get(1) / tmp) + xPos,
                (int) (-axisMatrix.get(2) / tmp) + yPos
        );
    }
}
