package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineEditorController;
import ru.nsu.kondrenko.model.BSplineEditorContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class BSplineEditor extends JPanel {
    private final BSplineEditorContext context;

    public BSplineEditor(BSplineEditorContext context) {
        this.context = context;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(100, 100));
        addMouseListener(new BSplineEditorController(this, context));
    }
}
