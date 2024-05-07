package ru.nsu.kondrenko.gui.view;

import ru.nsu.kondrenko.model.dto.IntPoint;

import java.awt.*;

public class SwingUtils {
    public static void drawLine(Graphics g, IntPoint p1, IntPoint p2) {
        g.drawLine(
                p1.getX(),
                p1.getY(),
                p2.getX(),
                p2.getY()
        );
    }
}
