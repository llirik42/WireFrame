package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.model.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BSplineMouseController extends MouseAdapter {
    private final Context context;
    private boolean isMouseOnEditor = false;
    private DoublePoint prevPoint;

    public BSplineMouseController(Context context) {
        this.context = context;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final IntPoint clickPoint = new IntPoint(e.getX(), e.getY());

        DoublePoint foundPoint = null;
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = Utils.realToMouseScale(p, context);

            if (mousePoint.distance(clickPoint) < 10) {
                foundPoint = p;
            }
        }

        if (foundPoint == null) {
            final DoublePoint realClickPoint = Utils.mouseToRealScale(clickPoint, context);
            context.addPoint(realClickPoint);
            prevPoint = null;
        } else {
            prevPoint = foundPoint;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (prevPoint != null) {
            context.removePoint(prevPoint);
            prevPoint = null;
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
        if (!isMouseOnEditor || prevPoint == null) {
            return;
        }

        final IntPoint mousePoint = new IntPoint(e.getX(), e.getY());
        final DoublePoint currentPoint = Utils.mouseToRealScale(mousePoint, context);
        final int index = context.removePoint(prevPoint);
        context.insertPoint(currentPoint, index);
        prevPoint = currentPoint;
    }
}
