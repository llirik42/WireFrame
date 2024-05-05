package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class BSplineNormalizationController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final double k = 1.0 * context.getHeight() / context.getWidth();
        context.setMinX(Constants.START_MIN_X);
        context.setMaxX(Constants.START_MAX_X);
        context.setMinY(Constants.START_MIN_X * k);
        context.setMaxY(Constants.START_MAX_X * k);
    }
}
