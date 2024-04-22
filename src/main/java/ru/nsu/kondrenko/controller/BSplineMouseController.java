package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.model.BSplineEditorContext;
import ru.nsu.kondrenko.model.DoublePoint;
import ru.nsu.kondrenko.model.IntPoint;
import ru.nsu.kondrenko.model.Utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BSplineMouseController extends MouseAdapter {
    private final BSplineEditorContext context;

    public BSplineMouseController(BSplineEditorContext context) {
        this.context = context;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        final IntPoint clickPoint = new IntPoint(e.getX(), e.getY());
        final DoublePoint realPoint = Utils.mouseToRealScale(clickPoint, context);
        context.addPoint(realPoint);
    }
}
