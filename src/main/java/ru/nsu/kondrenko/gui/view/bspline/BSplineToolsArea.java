package ru.nsu.kondrenko.gui.view.bspline;

import ru.nsu.kondrenko.gui.view.common.ToolsArea;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class BSplineToolsArea extends ToolsArea {
    public BSplineToolsArea(ActionListener bSplineNormalizationListener,
                            ActionListener bSplineClearListener,
                            KeyListener keyListener) {
        final JButton normalizeButton = createToolButton(
                "/bspline_normalization.png",
                bSplineNormalizationListener,
                "normalize"
        );
        normalizeButton.addKeyListener(keyListener);

        final JButton clearButton = createToolButton(
                "/trash.png",
                bSplineClearListener,
                "clear"
        );
        clearButton.addKeyListener(keyListener);

        add(normalizeButton);
        add(clearButton);
    }
}
