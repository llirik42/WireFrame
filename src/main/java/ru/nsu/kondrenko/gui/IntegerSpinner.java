package ru.nsu.kondrenko.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class IntegerSpinner extends JSpinner {
    public IntegerSpinner(int minValue, int maxValue, int startValue) {
        final int variantsCount = maxValue - minValue + 1;
        final String[] variants = new String[variantsCount];
        for (int i = 0; i < variantsCount; i++) {
            variants[i] = Integer.toString(minValue + i);
        }

        final SpinnerListModel model = new SpinnerListModel(variants);
        model.setValue(Integer.toString(startValue));

        setModel(model);

        Set<AWTKeyStroke> keys = getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set<AWTKeyStroke> newKeys = new HashSet<>(keys);
        newKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeys);
    }

    public int getIntValue() {
        return Integer.parseInt(getValue().toString());
    }
}
