package ru.nsu.kondrenko.controller.wireframe;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.WireframeUtils;
import ru.nsu.kondrenko.model.context.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class WireframeResetAngleController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setRotationMatrix(WireframeUtils.createDefaultRotationMatrix());
    }
}
