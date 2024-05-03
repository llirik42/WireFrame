package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineMouseController;
import ru.nsu.kondrenko.model.Context;

import javax.swing.*;
import java.awt.*;

public class BSplineEditorWindow extends JFrame {
    public BSplineEditorWindow(Context context) {
        final BSplineMouseController controller = new BSplineMouseController(context);
        add(new BSplineEditor(context, controller), BorderLayout.CENTER);
        add(new Form(context), BorderLayout.SOUTH);
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
