package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.model.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BSplineMouseController extends MouseAdapter {
    private final Context context;
    private boolean isMouseOnEditor = false;
    private Double2DPoint prevPoint;

    public BSplineMouseController(Context context) {
        this.context = context;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final IntPoint clickPoint = new IntPoint(e.getX(), e.getY());

        Double2DPoint foundPoint = null;
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = Utils.realToMouseScale(p, context);

            if (mousePoint.distance(clickPoint) < 10) {
                foundPoint = p;
            }
        }

        if (foundPoint == null) {
            final Double2DPoint realClickPoint = Utils.mouseToRealScale(clickPoint, context);
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
        final Double2DPoint currentPoint = Utils.mouseToRealScale(mousePoint, context);
        final int index = context.removePoint(prevPoint);
        context.insertPoint(currentPoint, index);
        prevPoint = currentPoint;
    }
}
