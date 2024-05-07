package ru.nsu.kondrenko.gui.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.gui.controller.common.MouseController;
import ru.nsu.kondrenko.model.CommonUtils;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.dto.Double2DPoint;
import ru.nsu.kondrenko.model.dto.IntPoint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

@RequiredArgsConstructor
public class BSplineMouseController extends MouseController {
    private static final double SENSITIVITY_DIVIDER = 200;

    private final Context context;
    private Double2DPoint prevPoint;

    @Override
    public void mousePressed(MouseEvent e) {
        final IntPoint clickPoint = new IntPoint(e.getX(), e.getY());

        Double2DPoint foundPoint = null;
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = CommonUtils.realToScreen(p, context);

            if (mousePoint.distance(clickPoint) < 10) {
                foundPoint = p;
            }
        }

        if (foundPoint == null) {
            final Double2DPoint realClickPoint = CommonUtils.screenToReal(clickPoint, context);
            context.addPoint(realClickPoint);
            context.notifyBSplineListeners();
            context.notifyWireframeListeners();
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
        context.notifyBSplineListeners();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (prevPoint != null) {
            context.removePoint(prevPoint);
            context.notifyBSplineListeners();
            prevPoint = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isMouseOnEditor() || prevPoint == null) {
            return;
        }

        final IntPoint mousePoint = new IntPoint(e.getX(), e.getY());
        final Double2DPoint currentPoint = CommonUtils.screenToReal(mousePoint, context);
        final int index = context.removePoint(prevPoint);
        context.insertPoint(currentPoint, index);
        context.notifyBSplineListeners();
        prevPoint = currentPoint;
    }
}
