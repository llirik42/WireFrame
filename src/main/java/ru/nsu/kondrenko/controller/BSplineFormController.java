package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.model.BSplineEditorContext;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BSplineFormController implements ChangeListener {
    private final BSplineEditorContext context;

    public BSplineFormController(BSplineEditorContext context) {
        this.context = context;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        final JSpinner spinner = (JSpinner) changeEvent.getSource();
        context.setPolylinesNumber(Integer.parseInt(spinner.getValue().toString()));
    }
}
