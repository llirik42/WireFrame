package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Context;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public class BSplineMovingController extends KeyAdapter {
    private final Context context;

    @Override
    public void keyPressed(KeyEvent e) {
        final int keyCode = e.getKeyCode();
        final double sensitivity = context.getBSplineSensitivity();
        final double k = 1.0 * context.getHeight() / context.getWidth();
        final double delta = 0.05 * sensitivity;

        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            context.setMinX(context.getMinX() + delta);
            context.setMaxX(context.getMaxX() + delta);
            context.notifyListeners();
            return;
        }

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            context.setMinX(context.getMinX() - delta);
            context.setMaxX(context.getMaxX() - delta);
            context.notifyListeners();
            return;
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            context.setMinY(context.getMinY() + delta / k);
            context.setMaxY(context.getMaxY() + delta / k);
            context.notifyListeners();
            return;
        }

        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            context.setMinY(context.getMinY() - delta / k);
            context.setMaxY(context.getMaxY() - delta / k);
            context.notifyListeners();
        }
    }
}
