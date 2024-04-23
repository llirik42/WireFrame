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
    private final List<BSplineEditorPoint> points = new ArrayList<>();
    private final List<BSplineEditorContextListener> listeners = new ArrayList<>();

    public BSplineEditorContext() {
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

    public void addPoint(BSplineEditorPoint point) {
        points.add(point);
        notifyListenersAboutPointsChange();
    }

    public void removePoint(BSplineEditorPoint point) {
        points.remove(point);
        notifyListenersAboutPointsChange();
    }

    public void setPolylinesNumber(int polylinesNumber) {
        this.polylinesNumber = polylinesNumber;
        notifyListenersAboutPolylinesNumberChange();
    }

    public void setWidth(int width) {
        this.width = width;
        notifyListenersAboutDimensionsChange();
    }

    public void setHeight(int height) {
        this.height = height;
        notifyListenersAboutDimensionsChange();
    }

    public void setMinX(double minX) {
        this.minX = minX;
        notifyListenersAboutRangesChange();
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
        notifyListenersAboutRangesChange();
    }

    public void setMinY(double minY) {
        this.minY = minY;
        notifyListenersAboutRangesChange();
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
        notifyListenersAboutRangesChange();
    }

    public double getXRange() {
        return maxX - minX;
    }

    public double getYRange() {
        return maxY - minY;
    }

    private void notifyListenersAboutPointsChange() {
        for (final var listener : listeners) {
            listener.onPointsChange(this);
        }
    }

    private void notifyListenersAboutPolylinesNumberChange() {
        for (final var listener : listeners) {
            listener.onPolylinesNumberChange(this);
        }
    }

    private void notifyListenersAboutDimensionsChange() {
        for (final var listener : listeners) {
            listener.onDimensionsChange(this);
        }
    }

    private void notifyListenersAboutRangesChange() {
        for (final var listener : listeners) {
            listener.onRangesChange(this);
        }
    }
}
