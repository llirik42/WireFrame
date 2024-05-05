package ru.nsu.kondrenko.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.net.URL;

public class BSplineToolsArea extends JPanel {
    public BSplineToolsArea(ActionListener bSplineNormalizationListener, KeyListener keyListener) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        final Color backgroundColor = new Color(200, 200, 200);

        final JButton normalizeButton = new JButton(loadIcon("/bspline_normalization.png"));
        initButton(
                normalizeButton,
                "normalize",
                bSplineNormalizationListener,
                32,
                backgroundColor
        );

        normalizeButton.addKeyListener(keyListener);

        add(normalizeButton);
    }

    private static ImageIcon loadIcon(String path) {
        final URL url = WireframeToolsArea.class.getResource(path);

        if (url == null) {
            throw new RuntimeException("Icon not found: " + path);
        }

        return new ImageIcon(url);
    }

    private static void initButton(AbstractButton button,
                                   String tip,
                                   ActionListener actionListener,
                                   int toolSize,
                                   Color backgroundColor) {
        button.setFocusPainted(false);
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(toolSize, toolSize));
        button.setMinimumSize(new Dimension(toolSize, toolSize));
        button.setBackground(backgroundColor);
        button.setBorderPainted(false);
    }
}
