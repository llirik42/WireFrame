package ru.nsu.kondrenko.gui.view.bspline;

import ru.nsu.kondrenko.gui.controller.bspline.BSplineFormController;
import ru.nsu.kondrenko.gui.view.common.IntegerSpinner;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.Context;

import javax.swing.*;
import java.awt.*;

public class BSplineForm extends JPanel {
    public BSplineForm(Context context) {
        final IntegerSpinner polylinesSpinner = new IntegerSpinner(
                Constants.MIN_POLYLINES_NUMBER,
                Constants.MAX_POLYLINES_NUMBER,
                context.getPolylinesNumber()
        );
        final IntegerSpinner generatricesSpinner = new IntegerSpinner(
                Constants.MIN_GENERATRICES_NUMBER,
                Constants.MAX_GENERATRICES_NUMBER,
                context.getGeneratricesNumber()
        );
        final IntegerSpinner circleSegmentsSpinner = new IntegerSpinner(
                Constants.MIN_CIRCLE_SEGMENTS_NUMBER,
                Constants.MAX_CIRCLE_SEGMENTS_NUMBER,
                context.getCircleSegmentsNumber()
        );
        final IntegerSpinner bSplineSensitivitySpinner = new IntegerSpinner(
                Constants.MIN_BSPLINE_SENSITIVITY,
                Constants.MAX_BSPLINE_SENSITIVITY,
                context.getBSplineSensitivity()
        );
        final IntegerSpinner wireframeSensitivitySpinner = new IntegerSpinner(
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

        polylinesSpinner.setPreferredSize(new Dimension(50, 20));
        generatricesSpinner.setPreferredSize(new Dimension(50, 20));
        circleSegmentsSpinner.setPreferredSize(new Dimension(50, 20));
        bSplineSensitivitySpinner.setPreferredSize(new Dimension(50, 20));
        wireframeSensitivitySpinner.setPreferredSize(new Dimension(50, 20));

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
}
