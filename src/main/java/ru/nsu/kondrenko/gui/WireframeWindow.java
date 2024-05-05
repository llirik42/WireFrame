package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.ContextListener;

import javax.swing.*;
import java.awt.*;

public class WireframeWindow extends JFrame implements ContextListener {
    private final WireFrameViewer viewer;

    public WireframeWindow(Context context) {
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.viewer = new WireFrameViewer(context);
        add(viewer);
        pack();
    }

    @Override
    public void onContextChange(Context context) {
        viewer.repaint();
    }
}
