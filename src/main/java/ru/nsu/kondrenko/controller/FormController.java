package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.gui.IntegerSpinner;
import ru.nsu.kondrenko.model.Context;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@RequiredArgsConstructor
public class FormController implements ChangeListener {
    private final IntegerSpinner polylinesSpinner;
    private final IntegerSpinner generatricesSpinner;
    private final IntegerSpinner circleSegmentsSpinner;
    private final Context context;

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        final IntegerSpinner source = (IntegerSpinner) changeEvent.getSource();

        if (source == polylinesSpinner) {
            context.setPolylinesNumber(source.getIntValue());
            return;
        }

        if (source == generatricesSpinner) {
            context.setGeneratricesNumber(source.getIntValue());
        }

        if (source == circleSegmentsSpinner) {
            context.setCircleSegmentsNumber(source.getIntValue());
        }
    }
}