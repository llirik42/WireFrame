package ru.nsu.kondrenko.gui.view.wireframe;

import ru.nsu.kondrenko.gui.view.common.Window;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.WireframeListener;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public class WireframeWindow extends Window implements WireframeListener {
    private final WireFrameViewer viewer;

    public WireframeWindow(Context context,
                           ActionListener openListener,
                           ActionListener saveListener,
                           ActionListener exitListener,
                           ActionListener resetAngleListener,
                           ActionListener resetDistanceListener,
                           ActionListener aboutListener,
                           MouseListener mouseListener,
                           MouseMotionListener mouseMotionListener,
                           MouseWheelListener mouseWheelListener) {
        super();
        viewer = new WireFrameViewer(
                context,
                mouseListener,
                mouseMotionListener,
                mouseWheelListener
        );

        add(new WireframeToolsArea(resetAngleListener, resetDistanceListener), BorderLayout.NORTH);

        add(viewer);

        setJMenuBar(new WireframeMenuArea(
                openListener,
                saveListener,
                exitListener,
                resetAngleListener,
                resetDistanceListener,
                aboutListener
        ).getMenuBar());

        pack();
    }

    @Override
    public void onWireframeChange(Context context) {
        viewer.onWireframeChange(context);
    }
}
