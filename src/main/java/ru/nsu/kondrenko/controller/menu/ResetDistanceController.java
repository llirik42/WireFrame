package ru.nsu.kondrenko.controller.menu;

import lombok.RequiredArgsConstructor;
import org.ejml.simple.SimpleMatrix;
import ru.nsu.kondrenko.model.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class ResetDistanceController implements ActionListener {
    private final Context context;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final double[][] cameraMatrixValues = {
                {1, 0, 0, 0},
                {0, 2000, 0, 0},
                {0, 0, 2000, 0},
                {1, 0, 0, 10}
        };
        context.setCameraMatrix(new SimpleMatrix(cameraMatrixValues));
    }
}
