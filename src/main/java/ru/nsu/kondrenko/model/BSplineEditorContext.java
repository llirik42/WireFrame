package ru.nsu.kondrenko.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BSplineEditorContext {
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public BSplineEditorContext() {
        minX = -10;
        maxX = 10;
        minY = -1;
        maxY = 20;
    }
}
