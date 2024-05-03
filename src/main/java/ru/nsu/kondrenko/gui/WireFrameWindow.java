package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.ContextListener;
import ru.nsu.kondrenko.model.Double4DPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WireFrameWindow extends JFrame implements ContextListener {
    public WireFrameWindow() {
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }

    @Override
    public void onContextChange(Context context) {
        if (context.getBSplinePoints().isEmpty()) {
            return;
        }

        final int generatricesNumber = context.getGeneratricesNumber();
        final double rotationDelta = (2 * Math.PI) / (generatricesNumber);

        final List<Double4DPoint> points = new ArrayList<>();
        final List<Double4DPoint> circles = new ArrayList<>();

        // 3D-каркас
        for (int i = 0; i < generatricesNumber; i++) {
            final double angle = rotationDelta * i;
            final double sin = Math.sin(angle);
            final double cos = Math.cos(angle);

            for (final var it : context.getBSplinePoints()) {
                double x = it.y() * cos;
                double y = it.y() * sin;
                points.add(new Double4DPoint(x, y, it.x(), 1));
            }
        }

        // Дуги
        final int allSegmentsNumber = generatricesNumber * context.getCircleSegmentsNumber();
        final double segmentsDelta = (2 * Math.PI) / allSegmentsNumber;
        for (final var p : context.getBSplinePoints()) {
            for (int j = 0; j < allSegmentsNumber; j++) {
                final double angle = segmentsDelta * j;
                final double sin = Math.sin(angle);
                final double cos = Math.cos(angle);
                final double x = p.y() * cos;
                final double y = p.y() * sin;
                circles.add(new Double4DPoint(x, y, p.x(), 1));
            }
        }

        final List<List<Double4DPoint>> normalized = normalize(points, circles);
        final List<Double4DPoint> normalizedPoints = normalized.get(0);
        final List<Double4DPoint> normalizedCircles = normalized.get(1);
    }

    public List<List<Double4DPoint>> normalize(List<Double4DPoint> points, List<Double4DPoint> circles) {
        final List<Double4DPoint> allPoints = Stream.concat(points.stream(), circles.stream()).toList();

        double minX = points.get(0).x();
        double maxX = minX;
        double minY = points.get(0).y();
        double maxY = minY;
        double minZ = points.get(0).z();
        double maxZ = minZ;

        for (final var p : allPoints) {
            minX = Double.min(minX, p.x());
            maxX = Double.max(maxX, p.x());
            minY = Double.min(minY, p.y());
            maxY = Double.max(maxY, p.y());
            minZ = Double.min(minZ, p.z());
            maxZ = Double.max(maxZ, p.z());
        }

        final List<List<Double4DPoint>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        res.add(new ArrayList<>());

        return res;
    }
}
