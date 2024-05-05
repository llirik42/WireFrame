package ru.nsu.kondrenko.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseController extends MouseAdapter {
    private boolean isMouseOnEditor = false;

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseOnEditor = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseOnEditor = false;
    }

    protected boolean isMouseOnEditor() {
        return isMouseOnEditor;
    }
}
