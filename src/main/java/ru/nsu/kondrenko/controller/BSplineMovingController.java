package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.Double2DPoint;
import ru.nsu.kondrenko.model.IntPoint;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
public class BSplineMovingController extends KeyAdapter {
    private final Context context;

    @Override
    public void keyPressed(KeyEvent e) {
        final int keyCode = e.getKeyCode();
        final double sensitivity = context.getBSplineSensitivity();
        final int delta = (int)(sensitivity);
        final Double2DPoint oldZeroPoint = context.getZeroPoint();

        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
            context.setZeroPoint(new Double2DPoint(oldZeroPoint.x() + delta, oldZeroPoint.y()));
            context.notifyListeners();
            return;
        }

        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
            context.setZeroPoint(new Double2DPoint(oldZeroPoint.x() - delta, oldZeroPoint.y()));
            context.notifyListeners();
            return;
        }

        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            context.setZeroPoint(new Double2DPoint(oldZeroPoint.x(), oldZeroPoint.y() + delta));
            context.notifyListeners();
            return;
        }

        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
            context.setZeroPoint(new Double2DPoint(oldZeroPoint.x(), oldZeroPoint.y() - delta));
            context.notifyListeners();
        }
    }
}
