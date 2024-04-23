package ru.nsu.kondrenko.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class DoublePoint {
    private double x;
    private double y;

    public double distance(DoublePoint p) {
        return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    }

    public void minus(DoublePoint p) {
        x -= p.x;
        y -= p.y;
    }

    public void scale(double scale) {
        x *= scale;
        y *= scale;
    }
}
