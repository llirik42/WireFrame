package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.gui.controller.bspline.BSplineMouseController;
import ru.nsu.kondrenko.gui.controller.bspline.BSplineMovingController;
import ru.nsu.kondrenko.gui.controller.bspline.BSplineNormalizationController;
import ru.nsu.kondrenko.gui.controller.bspline.BSplineResizeController;
import ru.nsu.kondrenko.gui.controller.common.AboutController;
import ru.nsu.kondrenko.gui.controller.common.ExitController;
import ru.nsu.kondrenko.gui.controller.common.OpenController;
import ru.nsu.kondrenko.gui.controller.common.SaveController;
import ru.nsu.kondrenko.gui.controller.wireframe.WireFrameMouseController;
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
        final WireframeResetAngleController resettingAngleController = new WireframeResetAngleController(context);
        final WireframeResetDistanceController resettingDistanceController = new WireframeResetDistanceController(context);
        final BSplineNormalizationController normalizationController = new BSplineNormalizationController(context);
        final AboutController aboutController = new AboutController();
        final BSplineMovingController bSplineMovingController = new BSplineMovingController(context);
        final BSplineMouseController bSplineMouseController = new BSplineMouseController(context);
        final WireFrameMouseController wireFrameMouseController = new WireFrameMouseController(context);

        final SwingView view = new SwingView(
                context,
                bSplineResizeController,
                exitController,
                openController,
                saveController,
                exitController,
                resettingAngleController,
                resettingDistanceController,
                normalizationController,
                aboutController,
                bSplineMovingController,
                bSplineMouseController,
                bSplineMouseController,
                bSplineMouseController,
                wireFrameMouseController,
                wireFrameMouseController,
                wireFrameMouseController
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
        context.addFormDataListener(view);

        openController.setView(view);
        saveController.setView(view);
        exitController.setView(view);
        aboutController.setView(view);

        view.show();
    }
}
