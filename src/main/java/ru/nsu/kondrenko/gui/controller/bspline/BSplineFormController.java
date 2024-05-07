package ru.nsu.kondrenko.gui.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.gui.view.common.IntegerSpinner;
import ru.nsu.kondrenko.model.context.Context;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@RequiredArgsConstructor
public class BSplineFormController implements ChangeListener {
    private final IntegerSpinner polylinesSpinner;
    private final IntegerSpinner generatricesSpinner;
    private final IntegerSpinner circleSegmentsSpinner;
    private final IntegerSpinner bSplineSensitivitySpinner;
    private final IntegerSpinner wireframeSensitivitySpinner;
    private final Context context;

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        final IntegerSpinner source = (IntegerSpinner) changeEvent.getSource();

        if (source == polylinesSpinner) {
            context.setPolylinesNumber(source.getIntValue());
            context.updateBSplinePoints();
            context.notifyBSplineListeners();
            context.notifyWireframeListeners();
            return;
        }

        if (source == generatricesSpinner) {
            context.setGeneratricesNumber(source.getIntValue());
            context.notifyWireframeListeners();
            return;
        }

        if (source == circleSegmentsSpinner) {
            context.setCircleSegmentsNumber(source.getIntValue());
            context.notifyWireframeListeners();
            return;
        }

        if (source == bSplineSensitivitySpinner) {
            context.setBSplineSensitivity(source.getIntValue());
            return;
        }

        if (source == wireframeSensitivitySpinner) {
            context.setWireframeSensitivity(wireframeSensitivitySpinner.getIntValue());
        }
    }
}
