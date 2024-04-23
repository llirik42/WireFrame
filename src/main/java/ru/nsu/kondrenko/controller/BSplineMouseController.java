package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.model.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BSplineMouseController extends MouseAdapter {
    private final BSplineEditorContext context;
    private boolean isMouseOnEditor = false;
    private BSplineEditorPoint sourcePoint;

    public BSplineMouseController(BSplineEditorContext context) {
        this.context = context;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final IntPoint mousePoint = new IntPoint(e.getX(), e.getY());

        BSplineEditorPoint foundPoint = null;
        for (final var p : context.getPoints()) {
            if (p.getMousePoint().distance(mousePoint) < 10) {
                foundPoint = p;
            }
        }

        if (foundPoint == null) {
            final DoublePoint realPoint = Utils.mouseToRealScale(mousePoint, context);
            context.addPoint(new BSplineEditorPoint(realPoint, mousePoint));
            sourcePoint = null;
        } else {
            sourcePoint = foundPoint;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (sourcePoint != null) {
            context.removePoint(sourcePoint);
            sourcePoint = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseOnEditor = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseOnEditor = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        final IntPoint mousePoint = new IntPoint(e.getX(), e.getY());

        if (!isMouseOnEditor || sourcePoint == null) {
            return;
        }

        final DoublePoint realPoint = Utils.mouseToRealScale(mousePoint, context);
        final BSplineEditorPoint currentPoint = new BSplineEditorPoint(realPoint, mousePoint);
        context.addPoint(currentPoint);
        context.removePoint(sourcePoint);
        sourcePoint = currentPoint;
    }
}
