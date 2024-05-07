package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.gui.controller.bspline.BSplineNormalizationController;
import ru.nsu.kondrenko.gui.controller.bspline.BSplineResizeController;
import ru.nsu.kondrenko.gui.controller.common.AboutController;
import ru.nsu.kondrenko.gui.controller.common.ExitController;
import ru.nsu.kondrenko.gui.controller.common.OpenController;
import ru.nsu.kondrenko.gui.controller.common.SaveController;
import ru.nsu.kondrenko.gui.controller.wireframe.WireframeResetAngleController;
import ru.nsu.kondrenko.gui.controller.wireframe.WireframeResetDistanceController;
import ru.nsu.kondrenko.gui.view.SwingView;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.io.ContextIO;
import ru.nsu.kondrenko.model.context.io.ContextIOException;
import ru.nsu.kondrenko.model.context.io.FileContextIO;

import java.net.URL;

public class Main {
    private static final String START_SCENE_PATH = "start_scene.wfctx";

    public static void main(String[] args) {
        final ContextIO contextIO = new FileContextIO();
        final Context context = new Context();

        final BSplineResizeController bSplineResizeController = new BSplineResizeController(context);
        final OpenController openController = new OpenController(context, contextIO);
        final SaveController saveController = new SaveController(context, contextIO);
        final ExitController exitController = new ExitController();
        final WireframeResetAngleController resetAngleController = new WireframeResetAngleController(context);
        final WireframeResetDistanceController resetDistanceController = new WireframeResetDistanceController(context);
        final BSplineNormalizationController bSplineNormalizationController = new BSplineNormalizationController(context);
        final AboutController aboutController = new AboutController();

        final SwingView view = new SwingView(
                context,
                bSplineResizeController,
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

        context.addBSplineListener(view);
        context.addWireframeListener(view);
        openController.setView(view);
        saveController.setView(view);
        exitController.setView(view);
        aboutController.setView(view);

        view.show();
    }
}
