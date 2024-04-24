package ru.nsu.kondrenko.controller;

import ru.nsu.kondrenko.model.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BSplineMouseController extends MouseAdapter {
    private final BSplineEditorContext context;
    private boolean isMouseOnEditor = false;
    private DoublePoint prevPoint;

    public BSplineMouseController(BSplineEditorContext context) {
        this.context = context;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            final IntPoint clickPoint = new IntPoint(e.getX(), e.getY());

            DoublePoint foundPoint = null;
            for (final var p : context.getPoints()) {
                final IntPoint mousePoint = Utils.realToMouseScale(p, context);

                if (mousePoint.distance(clickPoint) < 10) {
                    foundPoint = p;
                }
            }

            if (foundPoint == null) {
                final DoublePoint realClickPoint = Utils.mouseToRealScale(clickPoint, context);
                context.addPoint(realClickPoint);
                prevPoint = null;
            } else {
                prevPoint = foundPoint;
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            System.out.println("RIGHT");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (prevPoint != null) {
            context.removePoint(prevPoint);
            prevPoint = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseOnEditor = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseOnEditor = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isMouseOnEditor || prevPoint == null) {
            return;
        }

        final IntPoint mousePoint = new IntPoint(e.getX(), e.getY());
        final DoublePoint currentPoint = Utils.mouseToRealScale(mousePoint, context);
        context.addPoint(currentPoint);
        context.removePoint(prevPoint);
        prevPoint = currentPoint;
    }
}
