package ru.nsu.kondrenko.gui.view.bspline;

import ru.nsu.kondrenko.gui.view.common.Window;
import ru.nsu.kondrenko.model.context.BSplineListener;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.FormDataListener;

import java.awt.*;
import java.awt.event.*;

public class BSplineWindow extends Window implements BSplineListener, FormDataListener {
    private final BSplineEditor editor;
    private final BSplineForm form;

    public BSplineWindow(Context context,
                         ComponentListener bSplineEditorListener,
                         ActionListener openListener,
                         ActionListener saveListener,
                         ActionListener exitListener,
                         ActionListener bSplineNormalizationListener,
                         ActionListener bSplineClearListener,
                         ActionListener aboutListener,
                         KeyListener keyListener,
                         MouseListener mouseListener,
                         MouseMotionListener mouseMotionListener,
                         MouseWheelListener mouseWheelListener) {
        super();
        editor = new BSplineEditor(
                context,
                mouseListener,
                mouseMotionListener,
                mouseWheelListener,
                bSplineEditorListener);
        form = new BSplineForm(context);
        final BSplineToolsArea toolsArea = new BSplineToolsArea(
                bSplineNormalizationListener,
                bSplineClearListener,
                keyListener
        );

        add(editor, BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);
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

    @Override
    public void onFormDataChange(Context context) {
        form.onFormDataChange(context);
    }
}
