package ru.nsu.kondrenko.model;

public final class Utils {
    private Utils() {}

    public static DoublePoint mouseToRealScale(IntPoint mousePoint, Context context) {
        final int width = context.getWidth();
        final int height = context.getHeight();
        final double minX = context.getMinX();
        final double minY = context.getMinY();
        final double xRelative = 1.0 * mousePoint.getX() / width;
        final double yRelative = 1.0 * mousePoint.getY() / height;
        final double x = minX + xRelative * context.getXRange();
        final double y = minY + context.getYRange() * (1 - yRelative);
        return new DoublePoint(x, y);
    }

    public static IntPoint realToMouseScale(DoublePoint realPoint, Context context) {
        final double minX = context.getMinX();
        final double minY = context.getMinY();
        final double xRelative = (realPoint.getX() - minX) / context.getXRange();
        final double yRelative = (realPoint.getY() - minY) / context.getYRange();
        final int x = (int) Math.round(xRelative * context.getWidth());
        final int y = (int) Math.round((1 - yRelative) * context.getHeight());
        return new IntPoint(x, y);
    }
}
