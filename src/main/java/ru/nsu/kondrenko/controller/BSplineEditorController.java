package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.gui.BSplineEditor;
import ru.nsu.kondrenko.model.BSplineEditorContext;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BSplineEditorController extends MouseAdapter {
    private final BSplineEditor bSplineEditor;
    private final BSplineEditorContext context;

    public BSplineEditorController(BSplineEditor bSplineEditor, BSplineEditorContext context) {
        this.bSplineEditor = bSplineEditor;
        this.context = context;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        final int width = bSplineEditor.getWidth();
        final int height = bSplineEditor.getHeight();

        final double xRange = context.getMaxX() - context.getMinX();
        final double yRange = context.getMaxY() - context.getMinY();

        final double xRelative = 1.0 * e.getX() / width;
        final double yRelative = 1.0 * e.getY() / bSplineEditor.getHeight();

        final double x = xRelative * xRange + context.getMinX();
        final double y = yRange - yRelative * yRange + context.getMinY();

        System.out.println(x + " " + y);
    }
}
