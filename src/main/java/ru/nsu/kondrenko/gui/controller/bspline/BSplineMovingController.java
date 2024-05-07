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

        final double minX = context.getBSplineMinX();
        final double maxX = context.getBSplineMaxX();
        final double minY = context.getBSplineMinY();
        final double maxY = context.getBSplineMaxY();

        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            context.setBSplineMinX(minX + delta);
            context.setBSplineMaxX(maxX + delta);
            context.notifyBSplineListeners();
        }

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            context.setBSplineMinX(minX - delta);
            context.setBSplineMaxX(maxX - delta);
            context.notifyBSplineListeners();
        }

        final double ratio = context.getHeightWidthRatio();

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            context.setBSplineMinY(minY + delta / ratio);
            context.setBSplineMaxY(maxY + delta / ratio);
            context.notifyBSplineListeners();
        }

        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            context.setBSplineMinY(minY - delta / ratio);
            context.setBSplineMaxY(maxY - delta / ratio);
            context.notifyBSplineListeners();
        }
    }
}
