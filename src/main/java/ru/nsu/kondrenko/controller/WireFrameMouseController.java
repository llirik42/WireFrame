package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.WireframeUtils;

import java.awt.event.MouseEvent;

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
