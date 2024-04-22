package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.model.BSplineEditorContext;

import javax.swing.*;
import java.awt.*;

public class BSplineEditorWindow extends JFrame {
    public BSplineEditorWindow() {
        add(new BSplineEditor(new BSplineEditorContext()), BorderLayout.CENTER);
        add(new BSplineForm(), BorderLayout.SOUTH);
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }
}
