package ru.nsu.kondrenko.gui.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.context.Context;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public class BSplineMovingController extends KeyAdapter {
    private final Context context;

    @Override
    public void keyPressed(KeyEvent e) {
        final int keyCode = e.getKeyCode();
        final double sensitivity = context.getBSplineSensitivity();
        final double delta = 0.05 * sensitivity;

        final double minX = context.getMinX();
        final double maxX = context.getMaxX();
        final double minY = context.getMinY();
        final double maxY = context.getMaxY();

        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            context.setMinX(minX + delta);
            context.setMaxX(maxX + delta);
            context.notifyBSplineListeners();
        }

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            context.setMinX(minX - delta);
            context.setMaxX(maxX - delta);
            context.notifyBSplineListeners();
        }

        final double ratio = context.getHeightWidthRatio();

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            context.setMinY(minY + delta / ratio);
            context.setMaxY(maxY + delta / ratio);
            context.notifyBSplineListeners();
        }

        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            context.setMinY(minY - delta / ratio);
            context.setMaxY(maxY - delta / ratio);
            context.notifyBSplineListeners();
        }
    }
}
