package ru.nsu.kondrenko.controller.menu;

import lombok.RequiredArgsConstructor;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class ResetAngleController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        context.setRotationMatrix(SimpleMatrix.identity(4));
    }
}
