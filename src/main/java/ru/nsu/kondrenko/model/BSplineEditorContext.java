package ru.nsu.kondrenko.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BSplineEditorContext {
    private int width;
    private int height;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;
    private int polylinesNumber;
    private final List<DoublePoint> points = new ArrayList<>();
    private final List<BSplineEditorContextListener> listeners = new ArrayList<>();

    public BSplineEditorContext() {
        polylinesNumber = 1;
        minX = -10;
        maxX = 10;
        minY = -10;
        maxY = 10;
    }

    public void addListener(BSplineEditorContextListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BSplineEditorContextListener listener) {
        listeners.remove(listener);
    }

    public void addPoint(DoublePoint point) {
        points.add(point);
        notifyListeners();
    }

    public void removePoint(DoublePoint point) {
        points.remove(point);
        notifyListeners();
    }

    public void setPolylinesNumber(int polylinesNumber) {
        this.polylinesNumber = polylinesNumber;
        notifyListeners();
    }

    public void setWidth(int width) {
        this.width = width;
        notifyListeners();
    }

    public void setHeight(int height) {
        this.height = height;
        notifyListeners();
    }

    public void setMinX(double minX) {
        this.minX = minX;
        notifyListeners();
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
        notifyListeners();
    }

    public void setMinY(double minY) {
        this.minY = minY;
        notifyListeners();
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
        notifyListeners();
    }

    public double getXRange() {
        return maxX - minX;
    }

    public double getYRange() {
        return maxY - minY;
    }

    private void notifyListeners() {
        for (final var it : listeners) {
            it.onContextChange(this);
        }
    }
}
