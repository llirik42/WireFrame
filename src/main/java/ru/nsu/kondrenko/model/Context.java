package ru.nsu.kondrenko.model;

import lombok.Data;
import org.ejml.simple.SimpleMatrix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class Context implements Serializable {
    private int width;
    private int height;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private int polylinesNumber;
    private int generatricesNumber;
    private int circleSegmentsNumber;

    private int bSplineSensitivity;
    private int wireframeSensitivity;

    private SimpleMatrix rotationMatrix;
    private SimpleMatrix cameraMatrix;

    private List<Double2DPoint> points = new LinkedList<>();
    private List<Double2DPoint> bSplinePoints = new LinkedList<>();

    private transient final List<ContextListener> listeners = new ArrayList<>();

    public Context() {
        minX = Constants.START_MIN_X;
        maxX = Constants.START_MAX_X;
        minY = minX / 1280.0 * 590.0;
        maxY = maxX / 1280.0 * 590.0;
        polylinesNumber = Constants.START_POLYLINES_NUMBER;
        generatricesNumber = Constants.START_GENERATRICES_NUMBER;
        circleSegmentsNumber = Constants.START_CIRCLE_SEGMENTS_NUMBER;
        bSplineSensitivity = Constants.START_BSPLINE_SENSITIVITY;
        wireframeSensitivity = Constants.START_WIREFRAME_SENSITIVITY;
        rotationMatrix = WireframeUtils.createDefaultRotationMatrix();
        cameraMatrix = WireframeUtils.createDefaultCameraMatrix();
    }

    public void updateValues(Context other) {
        setWidth(other.getWidth());
        setHeight(other.getHeight());
        setMinX(other.getMinX());
        setMaxX(other.getMaxX());
        setMinY(other.getMinY());
        setMaxY(other.getMaxY());
        setPolylinesNumber(other.getPolylinesNumber());
        setGeneratricesNumber(other.getGeneratricesNumber());
        setCircleSegmentsNumber(other.getCircleSegmentsNumber());
        setBSplineSensitivity(other.getBSplineSensitivity());
        setWireframeSensitivity(other.getWireframeSensitivity());
        setRotationMatrix(other.getRotationMatrix());
        setCameraMatrix(other.getCameraMatrix());
        setPoints(other.getPoints());
        setBSplinePoints(other.getBSplinePoints());
        notifyListeners();
    }

    public double getXRange() {
        return maxX - minX;
    }

    public double getYRange() {
        return maxY - minY;
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

    public void setRotationMatrix(SimpleMatrix rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
        notifyListeners();
    }

    public void setCameraMatrix(SimpleMatrix cameraMatrix) {
        this.cameraMatrix = cameraMatrix;
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

    public void notifyListeners() {
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
