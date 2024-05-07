package ru.nsu.kondrenko.gui.view.common;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Getter
public abstract class MenuArea extends JPanel {
    private static final Color MENU_BACKGROUND_COLOR = new Color(100, 100, 100);
    private static final Color BUTTONS_COLOR = new Color(200, 200, 200);
    private static final Font FONT = new Font("G0", Font.PLAIN, 14);

    private final JMenuBar menuBar;

    protected MenuArea() {
        setBackground(MENU_BACKGROUND_COLOR);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        menuBar = new JMenuBar();
        menuBar.setBackground(MENU_BACKGROUND_COLOR);
    }

    protected static JMenu createMenu(String label) {
        JMenu result = new JMenu(label);
        result.setBackground(MENU_BACKGROUND_COLOR);
        result.setForeground(BUTTONS_COLOR);
        result.setFont(FONT);
        return result;
    }

    protected static JMenuItem createItem(String label, ActionListener actionListener) {
        final JMenuItem result = new JMenuItem(label);
        initButton(result, actionListener);
        return result;
    }

    private static void initButton(AbstractButton button, ActionListener actionListener) {
        button.setBorderPainted(false);
        button.setFont(FONT);
        button.setForeground(MENU_BACKGROUND_COLOR);
        button.addActionListener(actionListener);
    }
}
