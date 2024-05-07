package ru.nsu.kondrenko.gui.controller.wireframe;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.wireframe.WireframeUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class WireframeResetDistanceController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setCameraMatrix(WireframeUtils.createDefaultCameraMatrix());
        context.notifyWireframeListeners();
    }
}
