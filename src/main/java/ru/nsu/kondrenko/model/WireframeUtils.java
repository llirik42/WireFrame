package ru.nsu.kondrenko.model;

import org.ejml.simple.SimpleMatrix;

public final class WireframeUtils {
    private WireframeUtils() {
    }

    public static SimpleMatrix createRotationMatrix(int mouseDeltaX, int mouseDeltaY) {
        SimpleMatrix rotationMatrix = SimpleMatrix.identity(4);

        if (mouseDeltaX != 0) {
            final double xAngle = Math.PI / 100 * mouseDeltaX;
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
            final double yAngle = Math.PI / 100 * mouseDeltaY;
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
}
