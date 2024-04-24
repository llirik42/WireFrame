package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineMouseController;
import ru.nsu.kondrenko.model.BSplineEditorContext;

import javax.swing.*;
import java.awt.*;

public class BSplineEditorWindow extends JFrame {
    public BSplineEditorWindow() {
        final BSplineEditorContext context = new BSplineEditorContext();
        final BSplineMouseController controller = new BSplineMouseController(context);
        add(new BSplineEditor(context, controller), BorderLayout.CENTER);
        add(new BSplineForm(), BorderLayout.SOUTH);
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
