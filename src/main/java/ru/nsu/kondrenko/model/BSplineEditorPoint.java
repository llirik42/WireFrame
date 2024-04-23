package ru.nsu.kondrenko.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BSplineEditorPoint {
    private DoublePoint realPoint;

    private IntPoint mousePoint;
}
