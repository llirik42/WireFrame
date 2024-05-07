package ru.nsu.kondrenko.gui.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.ContextUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class BSplineClearController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.clearPivotPoints();
        ContextUtils.normalizeContext(context);
        context.notifyWireframeListeners();
    }
}
