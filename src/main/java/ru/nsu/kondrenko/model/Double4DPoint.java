package ru.nsu.kondrenko.model;

public record Double4DPoint(double x, double y, double z, double t) {
    public static int comparatorX(Double4DPoint p1, Double4DPoint p2) {
        return Double.compare(p1.x, p2.x);
    }

    public static int comparatorY(Double4DPoint p1, Double4DPoint p2) {
        return Double.compare(p1.y, p2.y);
    }

    public static int comparatorZ(Double4DPoint p1, Double4DPoint p2) {
        return Double.compare(p1.z, p2.z);
    }

    public Double4DPoint minus(Double4DPoint p) {
        return new Double4DPoint(
                x - p.x,
                y - p.y,
                z - p.z,
                t - p.t
        );
    }

    public Double4DPoint divide(double value) {
        return new Double4DPoint(
                x / value,
                y / value,
                z / value,
                t
        );
    }
}
