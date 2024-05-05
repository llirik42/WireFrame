package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.menu.*;
import ru.nsu.kondrenko.model.Context;

public class Main {
    public static void main(String[] args) {
        final Context context = new Context();

        final OpenController openController = new OpenController(context);
        final SaveController saveController = new SaveController(context);
        final ExitController exitController = new ExitController();
        final ResetAngleController resetAngleController = new ResetAngleController(context);
        final ResetDistanceController resetDistanceController = new ResetDistanceController(context);

        final SwingView view = new SwingView(
                context,
                exitController,
                openController,
                saveController,
                exitController,
                resetAngleController,
                resetDistanceController,
                null,
                null
        );

        context.addListener(view);

        openController.setView(view);
        saveController.setView(view);
        exitController.setView(view);

        view.show();
    }
}
