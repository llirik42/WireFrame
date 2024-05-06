package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.Double2DPoint;
import ru.nsu.kondrenko.model.IntPoint;
import ru.nsu.kondrenko.model.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class BSplineMouseController extends MouseController {
    private static final double SENSITIVITY_DIVIDER = 200;

    private final Context context;
    private Double2DPoint prevPoint;

    public BSplineMouseController(Context context) {
        this.context = context;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        final IntPoint clickPoint = new IntPoint(e.getX(), e.getY());

        Double2DPoint foundPoint = null;
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = Utils.realToScreen(p, context);

            if (mousePoint.distance(clickPoint) < 10) {
                foundPoint = p;
            }
        }

        if (foundPoint == null) {
            final Double2DPoint realClickPoint = Utils.screenToReal(clickPoint, context);
            context.addPoint(realClickPoint);
            prevPoint = null;
        } else {
            prevPoint = foundPoint;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        final double delta = e.getPreciseWheelRotation();
        final double sensitivity = context.getBSplineSensitivity();
        final double k = (1 + delta * sensitivity / SENSITIVITY_DIVIDER);
        context.setMinY(context.getMinY() * k);
        context.setMaxY(context.getMaxY() * k);
        context.setMinX(context.getMinX() * k);
        context.setMaxX(context.getMaxX() * k);
        context.notifyListeners();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (prevPoint != null) {
            context.removePoint(prevPoint);
            prevPoint = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isMouseOnEditor() || prevPoint == null) {
            return;
        }

        final IntPoint mousePoint = new IntPoint(e.getX(), e.getY());
        final Double2DPoint currentPoint = Utils.screenToReal(mousePoint, context);
        final int index = context.removePoint(prevPoint);
        context.insertPoint(currentPoint, index);
        prevPoint = currentPoint;
    }
}
