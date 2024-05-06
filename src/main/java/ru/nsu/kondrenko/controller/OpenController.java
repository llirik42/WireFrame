package ru.nsu.kondrenko.controller;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nsu.kondrenko.gui.View;
import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.ContextIO;
import ru.nsu.kondrenko.model.ContextIOException;

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
            view.showError("Cannot open scene: " + exception.getLocalizedMessage());
        }
    }
}
