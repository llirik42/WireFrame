package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineFormController;
import ru.nsu.kondrenko.model.Context;

import javax.swing.*;
import java.awt.*;

public class BSplineForm extends JPanel {
    public BSplineForm(Context context) {
        final String[] nVariants = new String[50];
        for (int i = 0; i < nVariants.length; i++) {
            nVariants[i] = Integer.toString(i + 1);
        }

        final String[] mVariants = new String[50];
        for (int i = 0; i < mVariants.length; i++) {
            mVariants[i] = Integer.toString(i + 2);
        }

        SpinnerListModel model = new SpinnerListModel(nVariants);



        model.setValue(Integer.toString(context.getPolylinesNumber()));
        JSpinner spinner = new JSpinner(model);
        spinner.setPreferredSize(new Dimension(100, 30));
        spinner.addChangeListener(new BSplineFormController(context));

        add(new JLabel("N"));
        add(spinner);





        setPreferredSize(new Dimension(-1, 100));
    }
}
