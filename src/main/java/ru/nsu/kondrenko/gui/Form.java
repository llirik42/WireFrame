package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.FormController;
import ru.nsu.kondrenko.model.Context;

import javax.swing.*;
import java.awt.*;

public class Form extends JPanel {
    public Form(Context context) {
        final IntegerSpinner polylinesSpinner = new IntegerSpinner(1, 50);
        final IntegerSpinner generatricesSpinner = new IntegerSpinner(2, 50);
        final IntegerSpinner circleSegmentsSpinner = new IntegerSpinner(1, 50);
        final IntegerSpinner wireframeSensitivitySpinner = new IntegerSpinner(1, 100);

        final FormController formController = new FormController(
                polylinesSpinner,
                generatricesSpinner,
                circleSegmentsSpinner,
                wireframeSensitivitySpinner,
                context
        );

        polylinesSpinner.addChangeListener(formController);
        generatricesSpinner.addChangeListener(formController);
        circleSegmentsSpinner.addChangeListener(formController);
        wireframeSensitivitySpinner.addChangeListener(formController);

        polylinesSpinner.setPreferredSize(new Dimension(50, 20));
        generatricesSpinner.setPreferredSize(new Dimension(50, 20));
        circleSegmentsSpinner.setPreferredSize(new Dimension(50, 20));
        wireframeSensitivitySpinner.setPreferredSize(new Dimension(50, 20));

        add(new JLabel("N"));
        add(polylinesSpinner);
        add(new JLabel("M"));
        add(generatricesSpinner);
        add(new JLabel("M1"));
        add(circleSegmentsSpinner);
        add(new JLabel("W-Sensitivity"));
        add(wireframeSensitivitySpinner);
        setPreferredSize(new Dimension(-1, 100));
    }
}
