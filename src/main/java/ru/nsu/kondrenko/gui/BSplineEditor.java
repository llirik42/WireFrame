package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.controller.BSplineEditorController;
import ru.nsu.kondrenko.model.BSplineEditorContext;
import ru.nsu.kondrenko.model.IntPoint;
import ru.nsu.kondrenko.model.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BSplineEditor extends JPanel {
    private static final int CURVE_POINT_RADIUS = 10;
    private static final int CURVE_POINT_DIAMETER = 2 * CURVE_POINT_RADIUS;
    private final BSplineEditorContext context;

    public BSplineEditor(BSplineEditorContext context) {
        this.context = context;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(100, 100));
        addMouseListener(new BSplineEditorController(this, context));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                context.setWidth(e.getComponent().getWidth());
                context.setHeight(e.getComponent().getHeight());
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (final var p : context.getPoints()) {
            final IntPoint mousePoint = Utils.realToMouseScale(p, context);
            g.drawOval(
                    mousePoint.getX() - CURVE_POINT_RADIUS,
                    mousePoint.getY() - CURVE_POINT_RADIUS,
                    CURVE_POINT_DIAMETER,
                    CURVE_POINT_DIAMETER
            );
        }
    }
}
