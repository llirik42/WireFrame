package ru.nsu.kondrenko.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BSplineEditorContext {
    private int width;
    private int height;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private final List<DoublePoint> points = new ArrayList<>();

    public BSplineEditorContext() {
        minX = -10;
        maxX = 10;
        minY = -10;
        maxY = 10;
    }

    public void addPoint(DoublePoint point) {
        points.add(point);
    }

    public double getXRange() {
        return maxX - minX;
    }

    public double getYRange() {
        return maxY - minY;
    }
}
