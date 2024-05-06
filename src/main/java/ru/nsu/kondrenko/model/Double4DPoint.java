package ru.nsu.kondrenko.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Double4DPoint {
    private double x;

    private double y;

    private double z;

    private double t;

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
