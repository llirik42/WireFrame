package ru.nsu.kondrenko.model;

public interface BSplineEditorContextListener {
    void onPointsChange(BSplineEditorContext context);

    void onRangesChange(BSplineEditorContext context);

    void onDimensionsChange(BSplineEditorContext context);

    void onPolylinesNumberChange(BSplineEditorContext context);
}
