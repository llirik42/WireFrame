package ru.nsu.kondrenko.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class BSplineNormalizationController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (context.getPoints().isEmpty()) {
            final double k = 1.0 * context.getHeight() / context.getWidth();
            context.setMinX(Constants.START_MIN_X);
            context.setMaxX(Constants.START_MAX_X);
            context.setMinY(Constants.START_MIN_X * k);
            context.setMaxY(Constants.START_MAX_X * k);
            context.notifyListeners();
        } else {
            double minX = context.getPoints().get(0).getX();
            double maxX = context.getPoints().get(0).getX();
            double minY = context.getPoints().get(0).getY();
            double maxY = context.getPoints().get(0).getY();

            for (final var it : context.getPoints()) {
                minX = Double.min(it.getX(), minX);
                maxX = Double.max(it.getX(), maxX);
                minY = Double.min(it.getY(), minY);
                maxY = Double.max(it.getY(), maxY);
            }

            context.setMinX(minX - 1);
            context.setMaxX(maxX + 1);
            context.setMinY(minY - 1);
            context.setMaxY(maxY + 1);
            context.notifyListeners();
        }

        context.notifyListeners();
    }
}
