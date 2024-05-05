package ru.nsu.kondrenko.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class WireframeToolsArea extends JPanel {
    public WireframeToolsArea(ActionListener resetAngleListener, ActionListener resetDistanceListener) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        final Color backgroundColor = new Color(200, 200, 200);

        final JButton resetAngleButton = new JButton(loadIcon("/angle.png"));
        initButton(
                resetAngleButton,
                "reset angle",
                resetAngleListener,
                32,
                backgroundColor
        );

        final JButton resetDistanceButton = new JButton(loadIcon("/distance.png"));
        initButton(
                resetDistanceButton,
                "reset distance",
                resetDistanceListener,
                32,
                backgroundColor
        );

        add(resetAngleButton);
        add(resetDistanceButton);
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
