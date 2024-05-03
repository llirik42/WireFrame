package ru.nsu.kondrenko.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class Context {
    private int width;
    private int height;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private int polylinesNumber;
    private int generatricesNumber;
    private int circleSegmentsNumber;

    private final List<Double2DPoint> points = new LinkedList<>();
    private List<Double2DPoint> bSplinePoints = new LinkedList<>();

    private final List<ContextListener> listeners = new ArrayList<>();

    public Context() {
        polylinesNumber = 1;
        generatricesNumber = 2;
        circleSegmentsNumber = 1;
        minX = -10;
        maxX = 10;
        minY = -10.0 / 1280 * 620;
        maxY = 10.0 / 1280 * 620;
    }

    public void addListener(ContextListener listener) {
        listeners.add(listener);
    }

    public void removeListener(ContextListener listener) {
        listeners.remove(listener);
    }

    public void addPoint(Double2DPoint point) {
        points.add(point);
        updateBSplinePoints();
        notifyListeners();
    }

    public void insertPoint(Double2DPoint point, int index) {
        points.add(index, point);
        updateBSplinePoints();
        notifyListeners();
    }

    public int removePoint(Double2DPoint point) {
        final int index = points.indexOf(point);
        points.remove(point);
        updateBSplinePoints();
        notifyListeners();
        return index;
    }

    public void setPolylinesNumber(int polylinesNumber) {
        this.polylinesNumber = polylinesNumber;
        updateBSplinePoints();
        notifyListeners();
    }

    public void setGeneratricesNumber(int generatricesCount) {
        this.generatricesNumber = generatricesCount;
        notifyListeners();
    }

    public void setCircleSegmentsNumber(int circleSegmentsNumber) {
        this.circleSegmentsNumber = circleSegmentsNumber;
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

    private void updateBSplinePoints() {
        bSplinePoints = BSplineUtils.calculateBSplinePoints(
                getPoints(),
                getPolylinesNumber()
        );
    }
}
