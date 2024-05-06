package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.ContextListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WireframeWindow extends JFrame implements ContextListener {
    private final WireFrameViewer viewer;

    public WireframeWindow(Context context,
                           ActionListener openListener,
                           ActionListener saveListener,
                           ActionListener exitListener,
                           ActionListener resetAngleListener,
                           ActionListener resetDistanceListener,
                           ActionListener helpListener,
                           ActionListener aboutListener) {
        setMinimumSize(new Dimension(640, 480));
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.viewer = new WireFrameViewer(context);

        add(new WireframeToolsArea(resetAngleListener, resetDistanceListener), BorderLayout.NORTH);

        setJMenuBar(new WireframeMenuArea(
                openListener,
                saveListener,
                exitListener,
                resetAngleListener,
                resetDistanceListener,
                helpListener,
                aboutListener
        ).getMenuBar());

        add(viewer);
        pack();
    }

    @Override
    public void onContextChange(Context context) {
        viewer.repaint();
    }
}
