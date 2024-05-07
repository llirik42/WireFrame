package ru.nsu.kondrenko.gui.view.bspline;

import lombok.Getter;
import ru.nsu.kondrenko.gui.view.common.DefaultMenuArea;

import javax.swing.*;
import java.awt.event.ActionListener;

@Getter
public class BSplineMenuArea extends DefaultMenuArea {
    public BSplineMenuArea(ActionListener openListener,
                           ActionListener saveListener,
                           ActionListener exitListener,
                           ActionListener bSplineNormalizationListener,
                           ActionListener aboutListener) {
        super(openListener, saveListener, exitListener, aboutListener);
        final JMenuBar menuBar = getMenuBar();

        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu(bSplineNormalizationListener));
        menuBar.add(createInfoMenu());
    }

    private static JMenu createViewMenu(ActionListener bSplineNormalizationListener) {
        final JMenu result = createMenu("View");
        result.add(createItem("Normalize", bSplineNormalizationListener));
        return result;
    }
}
