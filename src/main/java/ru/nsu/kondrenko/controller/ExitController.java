package ru.nsu.kondrenko.controller;

import lombok.Setter;
import ru.nsu.kondrenko.gui.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Setter
public class ExitController extends WindowAdapter implements ActionListener {
    private View view;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        doReaction();
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        doReaction();
    }

    private void doReaction() {
        view.destroy();
    }
}
