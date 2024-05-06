package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nsu.kondrenko.gui.View;
import ru.nsu.kondrenko.model.ContextIOException;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.ContextIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@RequiredArgsConstructor
public class OpenController implements ActionListener {
    private final Context context;
    private final ContextIO contextIO;

    @Setter
    private View view;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final File selectedFile = view.showOpenDialog();

        if (selectedFile == null) {
            return;
        }

        try {
            context.updateValues(contextIO.read(selectedFile.toString()));
        } catch (ContextIOException exception) {
            view.showError("Cannot open scene");
        }
    }
}
