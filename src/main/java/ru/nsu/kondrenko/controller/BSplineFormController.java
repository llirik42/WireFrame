package ru.nsu.kondrenko.controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BSplineFormController implements ChangeListener {
    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        final JSpinner spinner = (JSpinner) changeEvent.getSource();
        System.out.println(spinner.getValue());
    }
}
