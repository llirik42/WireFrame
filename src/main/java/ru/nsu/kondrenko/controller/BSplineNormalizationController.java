package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.Double2DPoint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class BSplineNormalizationController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (context.getPoints().isEmpty()) {
            context.setZeroPoint(new Double2DPoint(-10, 10));
            context.setScale(1);
        } else {
            double minX = context.getPoints().get(0).x();
            double maxX = context.getPoints().get(0).x();
            double minY = context.getPoints().get(0).y();
            double maxY = context.getPoints().get(0).y();

            for (final var it : context.getPoints()) {
                minX = Double.min(it.x(), minX);
                maxX = Double.max(it.x(), maxX);
                minY = Double.min(it.y(), minY);
                maxY = Double.max(it.y(), maxY);
            }

            final double scale = Double.max(maxX - minX, maxY - minY) / 100;
            context.setZeroPoint(new Double2DPoint(minX + scale * 10, maxY - scale * 10));
//            context.setScale(Double.max());
        }

        context.notifyListeners();
    }
}
