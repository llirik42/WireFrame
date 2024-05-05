package ru.nsu.kondrenko.model;

public final class Utils {
    private Utils() {}

    public static Double2DPoint screenToReal(IntPoint mousePoint, Context context) {
        final double xCenter = mousePoint.getX() - context.getWidth() / 2.0;
        final double yCenter = mousePoint.getY() - context.getHeight() / 2.0;
        final double xScale = xCenter / context.getScale();
        final double yScale = yCenter / context.getScale();
        final double xDiff = xScale + context.getZeroPoint().x();
        final double yDiff = yScale + context.getZeroPoint().y();
        return new Double2DPoint(xDiff, yDiff);
    }

    public static IntPoint realToScreen(Double2DPoint realPoint, Context context) {
        final Double2DPoint tmp = new Double2DPoint(
                (realPoint.x() - context.getZeroPoint().x()) * context.getScale(),
                (realPoint.y() - context.getZeroPoint().y()) * context.getScale()
        );

        return new IntPoint(
                (int) (Math.round(tmp.x() + context.getWidth() / 2.0)),
                (int) (Math.round(tmp.y() + context.getHeight() / 2.0))
        );
    }
}
