package ru.nsu.kondrenko.gui.view.bspline;

import ru.nsu.kondrenko.gui.controller.bspline.BSplineMouseController;
import ru.nsu.kondrenko.gui.controller.bspline.BSplineMovingController;
import ru.nsu.kondrenko.model.context.BSplineContextListener;
import ru.nsu.kondrenko.model.context.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

public class BSplineWindow extends JFrame implements BSplineContextListener {
    private final BSplineEditor editor;

    public BSplineWindow(Context context,
                         ComponentListener bSplineEditorListener,
                         ActionListener openListener,
                         ActionListener saveListener,
                         ActionListener exitListener,
                         ActionListener bSplineNormalizationListener,
                         ActionListener aboutListener) {
        final BSplineMouseController controller = new BSplineMouseController(context);
        editor = new BSplineEditor(context, controller, bSplineEditorListener);
        add(editor, BorderLayout.CENTER);
        add(new BSplineForm(context), BorderLayout.SOUTH);
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setMinimumSize(new Dimension(640, 480));

        final BSplineMovingController movingController = new BSplineMovingController(context);

        final BSplineToolsArea toolsArea = new BSplineToolsArea(bSplineNormalizationListener, movingController);

        add(toolsArea, BorderLayout.NORTH);

        final BSplineMenuArea menuArea = new BSplineMenuArea(
                openListener,
                saveListener,
                exitListener,
                bSplineNormalizationListener,
                aboutListener
        );

        setJMenuBar(menuArea.getMenuBar());

        pack();
    }

    @Override
    public void onBSplineContextChange(Context context) {
        editor.onBSplineContextChange(context);
    }
}
