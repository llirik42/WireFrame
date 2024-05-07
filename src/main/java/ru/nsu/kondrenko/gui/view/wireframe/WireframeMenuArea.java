package ru.nsu.kondrenko.gui.view.wireframe;

import lombok.Getter;
import ru.nsu.kondrenko.gui.view.common.DefaultMenuArea;

import javax.swing.*;
import java.awt.event.ActionListener;

@Getter
public class WireframeMenuArea extends DefaultMenuArea {
    public WireframeMenuArea(ActionListener openListener,
                             ActionListener saveListener,
                             ActionListener exitListener,
                             ActionListener resetAngleListener,
                             ActionListener resetDistanceListener,
                             ActionListener aboutListener) {
        super(openListener, saveListener, exitListener, aboutListener);
        final JMenuBar menuBar = getMenuBar();

        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu(resetAngleListener, resetDistanceListener));
        menuBar.add(createInfoMenu());
    }

    private static JMenu createViewMenu(ActionListener resetAngleListener,
                                        ActionListener resetDistanceListener) {
        final JMenu result = createMenu("View");
        result.add(createItem("Reset angle", resetAngleListener));
        result.add(createItem("Reset distance", resetDistanceListener));
        return result;
    }
}
