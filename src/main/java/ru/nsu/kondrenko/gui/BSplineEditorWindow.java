package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineMouseController;
import ru.nsu.kondrenko.controller.BSplineMovingController;
import ru.nsu.kondrenko.model.Context;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BSplineEditorWindow extends JFrame {
    public BSplineEditorWindow(Context context,
                               ActionListener openListener,
                               ActionListener saveListener,
                               ActionListener exitListener,
                               ActionListener bSplineNormalizationListener,
                               ActionListener helpListener,
                               ActionListener aboutListener) {
        final BSplineMouseController controller = new BSplineMouseController(context);
        add(new BSplineEditor(context, controller), BorderLayout.CENTER);
        add(new Form(context), BorderLayout.SOUTH);
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
                helpListener,
                aboutListener
        );

        setJMenuBar(menuArea.getMenuBar());

        pack();
    }
}
