package ru.nsu.kondrenko.gui;

import javax.swing.*;

public class IntegerSpinner extends JSpinner {
    public IntegerSpinner(int minValue, int maxValue, int startValue) {
        super();

        final int variantsCount = maxValue - minValue + 1;
        final String[] variants = new String[variantsCount];
        for (int i = 0; i < variantsCount; i++) {
            variants[i] = Integer.toString(minValue + i);
        }

        final SpinnerListModel model = new SpinnerListModel(variants);
        model.setValue(Integer.toString(startValue));

        setModel(model);
    }

    public int getIntValue() {
        return Integer.parseInt(getValue().toString());
    }
}
