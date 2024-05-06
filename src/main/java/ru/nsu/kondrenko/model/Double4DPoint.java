package ru.nsu.kondrenko.model;

public record Double4DPoint(double x, double y, double z, double t) {
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
