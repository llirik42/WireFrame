package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.gui.BSplineEditor;
import ru.nsu.kondrenko.model.BSplineEditorContext;
import ru.nsu.kondrenko.model.IntPoint;
import ru.nsu.kondrenko.model.Utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BSplineEditorController extends MouseAdapter {
    private final BSplineEditor bSplineEditor;
    private final BSplineEditorContext context;

    public BSplineEditorController(BSplineEditor bSplineEditor, BSplineEditorContext context) {
        this.bSplineEditor = bSplineEditor;
        this.context = context;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        context.addPoint(Utils.mouseToRealScale(new IntPoint(e.getX(), e.getY()), context));
        bSplineEditor.repaint();
    }
}
