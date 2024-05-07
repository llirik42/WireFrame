package ru.nsu.kondrenko.gui.view.wireframe;

import ru.nsu.kondrenko.gui.view.common.ToolsArea;

import java.awt.event.ActionListener;

public class WireframeToolsArea extends ToolsArea {
    public WireframeToolsArea(ActionListener resetAngleListener, ActionListener resetDistanceListener) {
        super();

        add(createToolButton(
                "/angle.png",
                resetAngleListener,
                "reset angle"
        ));

        add(createToolButton(
                "/distance.png",
                resetDistanceListener,
                "reset distance"
        ));
    }
}
