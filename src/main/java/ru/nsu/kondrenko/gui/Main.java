package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.AboutController;
import ru.nsu.kondrenko.controller.ExitController;
import ru.nsu.kondrenko.controller.OpenController;
import ru.nsu.kondrenko.controller.SaveController;
import ru.nsu.kondrenko.controller.bspline.BSplineNormalizationController;
import ru.nsu.kondrenko.controller.wireframe.WireframeResetAngleController;
import ru.nsu.kondrenko.controller.wireframe.WireframeResetDistanceController;
import ru.nsu.kondrenko.model.ContextIOException;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.ContextIO;
import ru.nsu.kondrenko.model.context.FileContextIO;

import java.net.URL;

public class Main {
    private static final String START_SCENE_PATH = "start_scene.wfctx";

    public static void main(String[] args) {
        final ContextIO contextIO = new FileContextIO();
        final Context context = new Context();

        final OpenController openController = new OpenController(context, contextIO);
        final SaveController saveController = new SaveController(context, contextIO);
        final ExitController exitController = new ExitController();
        final WireframeResetAngleController resetAngleController = new WireframeResetAngleController(context);
        final WireframeResetDistanceController resetDistanceController = new WireframeResetDistanceController(context);
        final BSplineNormalizationController bSplineNormalizationController = new BSplineNormalizationController(context);
        final AboutController aboutController = new AboutController();

        final SwingView view = new SwingView(
                context,
                exitController,
                openController,
                saveController,
                exitController,
                resetAngleController,
                resetDistanceController,
                bSplineNormalizationController,
                aboutController
        );

        try {
            final URL url = Main.class.getClassLoader().getResource(START_SCENE_PATH);

            if (url == null) {
                view.showWarning(String.format("Start scene '%s' not found", START_SCENE_PATH));
            } else {
                context.updateValues(contextIO.read(url.getPath()));
            }

        } catch (ContextIOException exception) {
            view.showWarning("Error during reading start scene");
        }

        context.addListener(view);
        openController.setView(view);
        saveController.setView(view);
        exitController.setView(view);
        aboutController.setView(view);

        view.show();
    }
}
