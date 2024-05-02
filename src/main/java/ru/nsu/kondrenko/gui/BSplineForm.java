package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineFormController;
import ru.nsu.kondrenko.model.BSplineEditorContext;

import javax.swing.*;
import java.awt.*;

public class BSplineForm extends JPanel {
    public BSplineForm(BSplineEditorContext context) {
        final String[] variants = new String[20];
        for (int i = 0; i < 20; i++) {
            variants[i] = Integer.toString(i + 1);
        }

        SpinnerListModel model = new SpinnerListModel(variants);
        model.setValue(Integer.toString(context.getPolylinesNumber()));
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 30));
        spinner.addChangeListener(new BSplineFormController(context));
        add(new JLabel("N"));
        add(spinner);
        setPreferredSize(new Dimension(-1, 100));
    }
}
