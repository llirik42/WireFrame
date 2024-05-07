package ru.nsu.kondrenko.gui.view.wireframe;

import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.WireframeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WireframeWindow extends JFrame implements WireframeListener {
    private final WireFrameViewer viewer;

    public WireframeWindow(Context context,
                           ActionListener openListener,
                           ActionListener saveListener,
                           ActionListener exitListener,
                           ActionListener resetAngleListener,
                           ActionListener resetDistanceListener,
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
                aboutListener
        ).getMenuBar());

        add(viewer);
        pack();
    }

    @Override
    public void onWireframeChange(Context context) {
        viewer.onWireframeChange(context);
    }
}
