package ru.nsu.kondrenko.model.context;

import lombok.Data;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.bspline.BSplineUtils;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.Double4DPoint;
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

    private List<Double2DPoint> pivotPoints = new LinkedList<>();
    private List<Double2DPoint> bSplinePoints = new ArrayList<>();
    private List<Double4DPoint> generatricesPoints = new ArrayList<>();
    private List<Double4DPoint> circlesPoints = new ArrayList<>();

    private transient final List<BSplineListener> bSplineContextListeners = new ArrayList<>();
    private transient final List<WireframeListener> wireframeListeners = new ArrayList<>();
    private transient final List<FormDataListener> formDataListeners = new ArrayList<>();

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

    public void setPolylinesNumber(int polylinesNumber) {
        this.polylinesNumber = polylinesNumber;
        updateBSplinePoints();
    }

    public void setGeneratricesNumber(int generatricesNumber) {
        this.generatricesNumber = generatricesNumber;
        updateGeneratricesPoints();
        updateCirclesPoints();
    }

    public void setCircleSegmentsNumber(int circleSegmentsNumber) {
        this.circleSegmentsNumber = circleSegmentsNumber;
        updateCirclesPoints();
    }

    public void updateValues(Context other) {
        bSplineWidth = other.bSplineWidth;
        bSplineHeight = other.bSplineHeight;
        bSplineMinX = other.bSplineMinX;
        bSplineMaxX = other.bSplineMaxX;
        bSplineMinY = other.bSplineMinY;
        bSplineMaxY = other.bSplineMaxY;

        wireframeWidth = other.wireframeWidth;
        wireframeHeight = other.wireframeHeight;
        wireframeMinX = other.wireframeMinX;
        wireframeMaxX = other.wireframeMaxX;
        wireframeMinY = other.wireframeMinY;
        wireframeMaxY = other.wireframeMaxY;

        polylinesNumber = other.polylinesNumber;
        generatricesNumber = other.generatricesNumber;
        circleSegmentsNumber = other.circleSegmentsNumber;

        bSplineSensitivity = other.bSplineSensitivity;
        wireframeSensitivity = other.wireframeSensitivity;

        rotationMatrix = other.rotationMatrix;
        cameraMatrix = other.cameraMatrix;

        pivotPoints = other.pivotPoints;
        generatricesPoints = other.generatricesPoints;
        circlesPoints = other.circlesPoints;

        notifyBSplineListeners();
        notifyWireframeListeners();
        notifyFormDataListeners();
    }

    public double getBSplineXRange() {
        return bSplineMaxX - bSplineMinX;
    }

    public void addBSplineListener(BSplineListener listener) {
        bSplineContextListeners.add(listener);
    }

    public void addWireframeListener(WireframeListener listener) {
        wireframeListeners.add(listener);
    }

    public void addFormDataListener(FormDataListener listener) {
        formDataListeners.add(listener);
    }

    public double getHeightWidthRatio() {
        return 1.0 * bSplineHeight / bSplineWidth;
    }

    public void addPivotPoint(Double2DPoint point) {
        pivotPoints.add(point);
        updateBSplinePoints();
    }

    public void insertPivotPoint(Double2DPoint point, int index) {
        pivotPoints.add(index, point);
        updateBSplinePoints();
    }

    public int removePivotPoint(Double2DPoint point) {
        final int index = pivotPoints.indexOf(point);
        pivotPoints.remove(point);
        updateBSplinePoints();
        return index;
    }

    public void clearPivotPoints() {
        pivotPoints.clear();
        bSplinePoints.clear();
        updateBSplinePoints();
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

    public void notifyFormDataListeners() {
        for (final var it : formDataListeners) {
            it.onFormDataChange(this);
        }
    }

    private void updateBSplinePoints() {
        bSplinePoints = BSplineUtils.calculateBSplinePoints(
                getPivotPoints(),
                getPolylinesNumber()
        );
        updateGeneratricesPoints();
        updateCirclesPoints();
    }

    private void updateGeneratricesPoints() {
        generatricesPoints = WireframeUtils.calculateGeneratricesPoints(
                generatricesNumber,
                bSplinePoints
        );
    }

    private void updateCirclesPoints() {
        if (bSplinePoints.isEmpty()) {
            return;
        }

        circlesPoints = WireframeUtils.calculateCirclesPoints(
                polylinesNumber,
                generatricesNumber,
                circleSegmentsNumber,
                bSplinePoints
        );
    }
}
