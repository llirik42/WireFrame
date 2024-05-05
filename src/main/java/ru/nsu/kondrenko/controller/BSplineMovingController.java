package ru.nsu.kondrenko.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BSplineMovingController extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e);
    }
}
