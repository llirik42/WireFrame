package ru.nsu.kondrenko.model.context;

import lombok.Data;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.bspline.BSplineUtils;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.wireframe.WireframeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static ru.nsu.kondrenko.model.Constants.*;

@Data
public class Context implements Serializable {
    private int bSplineWidth;
    private int bSplineHeight;
    private double bSplineMinX;
    private double bSplineMaxX;
    private double bSplineMinY;
    private double bSplineMaxY;

    private int wireframeWidth;
    private int wireframeHeight;
    private double wireframeMinX;
    private double wireframeMaxX;
    private double wireframeMinY;
    private double wireframeMaxY;

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
    private transient final List<WireframeListener> wireframeListeners = new ArrayList<>();

    public Context() {
        final double bSplineRatio = 1.0 * START_BSPLINE_EDITOR_WIDTH / START_BSPLINE_EDITOR_HEIGHT;
        bSplineMinX = Constants.BSPLINE_START_MIN_X;
        bSplineMaxX = Constants.BSPLINE_START_MAX_X;
        bSplineWidth = START_BSPLINE_EDITOR_WIDTH;
        bSplineHeight = START_BSPLINE_EDITOR_HEIGHT;
        bSplineMinY = bSplineMinX / bSplineRatio;
        bSplineMaxY = bSplineMaxX / bSplineRatio;

        wireframeMinX = WIREFRAME_MIN_X;
        wireframeMaxX = WIREFRAME_MAX_X;

        polylinesNumber = Constants.START_POLYLINES_NUMBER;
        generatricesNumber = Constants.START_GENERATRICES_NUMBER;
        circleSegmentsNumber = Constants.START_CIRCLE_SEGMENTS_NUMBER;
        bSplineSensitivity = Constants.START_BSPLINE_SENSITIVITY;
        wireframeSensitivity = Constants.START_WIREFRAME_SENSITIVITY;
        rotationMatrix = WireframeUtils.createDefaultRotationMatrix();
        cameraMatrix = WireframeUtils.createDefaultCameraMatrix();
    }

    public void updateValues(Context other) {
        bSplineWidth = other.getBSplineWidth();
        bSplineHeight = other.getBSplineHeight();
        bSplineMinX = other.getBSplineMinX();
        bSplineMaxX = other.getBSplineMaxX();
        bSplineMinY = other.getBSplineMinY();
        bSplineMaxY = other.getBSplineMaxY();
        polylinesNumber = other.getPolylinesNumber();
        generatricesNumber = other.getGeneratricesNumber();
        circleSegmentsNumber = other.getCircleSegmentsNumber();
        bSplineSensitivity = other.getBSplineSensitivity();
        wireframeSensitivity = other.getWireframeSensitivity();
        rotationMatrix = other.getRotationMatrix();
        cameraMatrix = other.getCameraMatrix();
        points = other.getPoints();
        bSplinePoints = other.getBSplinePoints();
    }

    public double getXRange() {
        return bSplineMaxX - bSplineMinX;
    }

    public double getYRange() {
        return bSplineMaxY - bSplineMinY;
    }

    public void addBSplineListener(BSplineContextListener listener) {
        bSplineContextListeners.add(listener);
    }

    public void addWireframeListener(WireframeListener listener) {
        wireframeListeners.add(listener);
    }

    public double getHeightWidthRatio() {
        return 1.0 * bSplineHeight / bSplineWidth;
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

    public void notifyWireframeListeners() {
        for (final var it : wireframeListeners) {
            it.onWireframeChange(this);
        }
    }

    public void updateBSplinePoints() {
        bSplinePoints = BSplineUtils.calculateBSplinePoints(
                getPoints(),
                getPolylinesNumber()
        );
    }
}
