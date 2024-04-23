package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineFormController;

import javax.swing.*;
import java.awt.*;

public class BSplineForm extends JPanel {
    public BSplineForm() {
        final String[] variants = new String[50];
        for (int i = 0; i < 50; i++) {
            variants[i] = Integer.toString(i + 1);
        }

        SpinnerListModel monthModel = new SpinnerListModel(variants);
        JSpinner spinner = new JSpinner(monthModel);
        spinner.setPreferredSize(new Dimension(100, 30));
        spinner.addChangeListener(new BSplineFormController());
        // add(new JLabel("N"));
        // add(spinner);
        setPreferredSize(new Dimension(-1, 100));
    }
}
