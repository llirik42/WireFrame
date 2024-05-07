package ru.nsu.kondrenko.gui.view.bspline;

import ru.nsu.kondrenko.gui.controller.bspline.BSplineFormController;
import ru.nsu.kondrenko.gui.view.common.IntegerSpinner;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.FormDataListener;

import javax.swing.*;
import java.awt.*;

public class BSplineForm extends JPanel implements FormDataListener {
    private static final Dimension SPINNER_DIMENSION = new Dimension(50, 20);

    private final IntegerSpinner polylinesSpinner;
    private final IntegerSpinner generatricesSpinner;
    private final IntegerSpinner circleSegmentsSpinner;
    private final IntegerSpinner bSplineSensitivitySpinner;
    private final IntegerSpinner wireframeSensitivitySpinner;

    public BSplineForm(Context context) {
        polylinesSpinner = new IntegerSpinner(
                Constants.MIN_POLYLINES_NUMBER,
                Constants.MAX_POLYLINES_NUMBER,
                context.getPolylinesNumber()
        );
        generatricesSpinner = new IntegerSpinner(
                Constants.MIN_GENERATRICES_NUMBER,
                Constants.MAX_GENERATRICES_NUMBER,
                context.getGeneratricesNumber()
        );
        circleSegmentsSpinner = new IntegerSpinner(
                Constants.MIN_CIRCLE_SEGMENTS_NUMBER,
                Constants.MAX_CIRCLE_SEGMENTS_NUMBER,
                context.getCircleSegmentsNumber()
        );
        bSplineSensitivitySpinner = new IntegerSpinner(
                Constants.MIN_BSPLINE_SENSITIVITY,
                Constants.MAX_BSPLINE_SENSITIVITY,
                context.getBSplineSensitivity()
        );
        wireframeSensitivitySpinner = new IntegerSpinner(
                Constants.MIN_WIREFRAME_SENSITIVITY,
                Constants.MAX_WIREFRAME_SENSITIVITY,
                context.getWireframeSensitivity()
        );

        final BSplineFormController formController = new BSplineFormController(
                polylinesSpinner,
                generatricesSpinner,
                circleSegmentsSpinner,
                bSplineSensitivitySpinner,
                wireframeSensitivitySpinner,
                context
        );

        polylinesSpinner.addChangeListener(formController);
        generatricesSpinner.addChangeListener(formController);
        circleSegmentsSpinner.addChangeListener(formController);
        bSplineSensitivitySpinner.addChangeListener(formController);
        wireframeSensitivitySpinner.addChangeListener(formController);

        polylinesSpinner.setPreferredSize(SPINNER_DIMENSION);
        generatricesSpinner.setPreferredSize(SPINNER_DIMENSION);
        circleSegmentsSpinner.setPreferredSize(SPINNER_DIMENSION);
        bSplineSensitivitySpinner.setPreferredSize(SPINNER_DIMENSION);
        wireframeSensitivitySpinner.setPreferredSize(SPINNER_DIMENSION);

        // Empty JPanel's are fictive to create distance between spinners

        add(new JLabel("N"));
        add(polylinesSpinner);

        add(new JPanel());

        add(new JLabel("M"));
        add(generatricesSpinner);

        add(new JPanel());

        add(new JLabel("M1"));
        add(circleSegmentsSpinner);

        add(new JPanel());

        add(new JLabel("B-Sensitivity"));
        add(bSplineSensitivitySpinner);

        add(new JPanel());

        add(new JLabel("W-Sensitivity"));
        add(wireframeSensitivitySpinner);
    }

    @Override
    public void onFormDataChange(Context context) {
        polylinesSpinner.setIntValue(context.getPolylinesNumber());
        generatricesSpinner.setIntValue(context.getGeneratricesNumber());
        circleSegmentsSpinner.setIntValue(context.getCircleSegmentsNumber());
        bSplineSensitivitySpinner.setIntValue(context.getBSplineSensitivity());
        wireframeSensitivitySpinner.setIntValue(context.getWireframeSensitivity());
    }
}
