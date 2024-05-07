package ru.nsu.kondrenko.gui.controller.common;

import lombok.Setter;
import ru.nsu.kondrenko.gui.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Setter
public class AboutController implements ActionListener {
    private View view;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        view.showAbout();
    }
}
