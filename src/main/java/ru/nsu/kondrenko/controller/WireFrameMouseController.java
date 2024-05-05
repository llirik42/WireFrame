package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.WireframeUtils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

@RequiredArgsConstructor
public class WireFrameMouseController extends MouseController {
    private final Context context;

    private int prevX;
    private int prevY;
    private boolean hasPrevPoint = false;

    @Override
    public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
        hasPrevPoint = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        hasPrevPoint = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        final double precision = e.getPreciseWheelRotation();
        final int sensitivity = context.getWireframeSensitivity();

        final SimpleMatrix cameraMatrix = context.getCameraMatrix();
        final double v1 = cameraMatrix.get(1, 1);
        final double v2 = cameraMatrix.get(2, 2);
        cameraMatrix.set(1, 1, v1 * (1 - sensitivity * precision / 200));
        cameraMatrix.set(2, 2, v2 * (1 - sensitivity * precision / 200));
        context.notifyListeners();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!hasPrevPoint || !isMouseOnEditor()) {
            return;
        }

        final int deltaX = e.getX() - prevX;
        final int deltaY = e.getY() - prevY;

        context.setRotationMatrix(WireframeUtils.createRotationMatrix(deltaX, deltaY).mult(context.getRotationMatrix()));
        prevX = e.getX();
        prevY = e.getY();
    }
}
