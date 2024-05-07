package ru.nsu.kondrenko.gui.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.gui.controller.common.MouseController;
import ru.nsu.kondrenko.model.CommonUtils;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.ContextUtils;
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
        for (final var p : context.getPivotPoints()) {
            final IntPoint mousePoint = ContextUtils.realToScreenBSpline(
                    p,
                    context
            );

            if (mousePoint.distance(clickPoint) < Constants.PIVOT_POINT_RADIUS) {
                foundPoint = p;
            }
        }

        if (foundPoint == null) {
            final Double2DPoint realClickPoint = CommonUtils.screenToReal(
                    clickPoint,
                    context.getBSplineWidth(),
                    context.getBSplineHeight(),
                    context.getBSplineMinX(),
                    context.getBSplineMaxX(),
                    context.getBSplineMinY(),
                    context.getBSplineMaxY()
            );
            context.addPivotPoint(realClickPoint);
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
        context.setBSplineMinY(context.getBSplineMinY() * k);
        context.setBSplineMaxY(context.getBSplineMaxY() * k);
        context.setBSplineMinX(context.getBSplineMinX() * k);
        context.setBSplineMaxX(context.getBSplineMaxX() * k);
        context.notifyBSplineListeners();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (prevPoint != null) {
            context.removePivotPoint(prevPoint);
            context.notifyBSplineListeners();
            context.notifyWireframeListeners();
            prevPoint = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isMouseOnEditor() || prevPoint == null) {
            return;
        }

        final IntPoint mousePoint = new IntPoint(e.getX(), e.getY());
        final Double2DPoint currentPoint = ContextUtils.screenToRealBspline(
                mousePoint,
                context
        );
        final int index = context.removePivotPoint(prevPoint);
        context.insertPivotPoint(currentPoint, index);
        context.notifyBSplineListeners();
        context.notifyWireframeListeners();
        prevPoint = currentPoint;
    }
}
