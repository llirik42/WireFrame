package ru.nsu.kondrenko.model.context;

import lombok.Data;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.BSplineUtils;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.Double2DPoint;
import ru.nsu.kondrenko.model.WireframeUtils;

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

    private transient final List<BSplineContextListener> bSplineContextListeners = new ArrayList<>();

    public Context() {
        minX = Constants.START_MIN_X;
        maxX = Constants.START_MAX_X;
        minY = minX / 1280.0 * 590.0;
        maxY = maxX / 1280.0 * 590.0;
        width = 1280;
        height = 590;
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
    }

    public double getXRange() {
        return maxX - minX;
    }

    public double getYRange() {
        return maxY - minY;
    }

    public void addBSplineListener(BSplineContextListener listener) {
        bSplineContextListeners.add(listener);
    }

    public double getHeightWidthRatio() {
        return 1.0 * height / width;
    }

    public void addPoint(Double2DPoint point) {
        points.add(point);
        updateBSplinePoints();
    }

    public void insertPoint(Double2DPoint point, int index) {
        points.add(index, point);
        updateBSplinePoints();
    }

    public int removePoint(Double2DPoint point) {
        final int index = points.indexOf(point);
        points.remove(point);
        updateBSplinePoints();
        return index;
    }

    public void notifyBSplineListeners() {
        for (final var it : bSplineContextListeners) {
            it.onBSplineContextChange(this);
        }
    }

    private void updateBSplinePoints() {
        bSplinePoints = BSplineUtils.calculateBSplinePoints(
                getPoints(),
                getPolylinesNumber()
        );
    }
}
