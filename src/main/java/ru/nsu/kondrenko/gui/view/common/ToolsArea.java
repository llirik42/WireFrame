package ru.nsu.kondrenko.gui.view.common;

import ru.nsu.kondrenko.gui.view.wireframe.WireframeToolsArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public abstract class ToolsArea extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(200, 200, 200);
    private static final int TOOL_SIZE = 32;
    private static final Dimension BUTTON_SIZE_DIMENSION = new Dimension(TOOL_SIZE, TOOL_SIZE);

    public ToolsArea() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    protected JButton createToolButton(String iconPath, ActionListener actionListener, String tip) {
        final JButton resetAngleButton = new JButton(loadIcon(iconPath));
        initButton(resetAngleButton, tip, actionListener);
        return resetAngleButton;
    }

    private static void initButton(AbstractButton button,
                                   String tip,
                                   ActionListener actionListener) {
        button.setFocusPainted(false);
        button.setToolTipText(tip);
        button.addActionListener(actionListener);
        button.setPreferredSize(BUTTON_SIZE_DIMENSION);
        button.setMinimumSize(BUTTON_SIZE_DIMENSION);
        button.setBackground(BACKGROUND_COLOR);
        button.setBorderPainted(false);
    }

    private static ImageIcon loadIcon(String path) {
        final URL url = WireframeToolsArea.class.getResource(path);

        if (url == null) {
            throw new RuntimeException("Icon not found: " + path);
        }

        return new ImageIcon(url);
    }
}
