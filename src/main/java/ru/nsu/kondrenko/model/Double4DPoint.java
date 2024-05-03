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
}
