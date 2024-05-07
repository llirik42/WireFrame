package ru.nsu.kondrenko.gui.controller.bspline;

import lombok.RequiredArgsConstructor;
import ru.nsu.kondrenko.model.context.Context;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@RequiredArgsConstructor
public class BSplineResizeController extends ComponentAdapter {
    private final Context context;

    @Override
    public void componentResized(ComponentEvent e) {
        final int oldWidth = context.getBSplineWidth();
        final int oldHeight = context.getBSplineHeight();

        final int newWidth = e.getComponent().getWidth();
        final int newHeight = e.getComponent().getHeight();

        context.setBSplineWidth(newWidth);
        context.setBSplineHeight(newHeight);

        if (newWidth != oldWidth) {
            final double k = 1.0 * oldWidth / newWidth;
            context.setBSplineMinY(context.getBSplineMinY() * k);
            context.setBSplineMaxY(context.getBSplineMaxY() * k);
        }

        if (newHeight != oldHeight) {
            final double k = 1.0 * newHeight / newWidth;
            context.setBSplineMinY(context.getBSplineMinX() * k);
            context.setBSplineMaxY(context.getBSplineMaxX() * k);
        }
    }
}
