package ru.nsu.kondrenko.gui.controller.common;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nsu.kondrenko.gui.view.View;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.io.ContextIO;
import ru.nsu.kondrenko.model.context.io.ContextIOException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@RequiredArgsConstructor
public class SaveController implements ActionListener {
    private final Context context;
    private final ContextIO contextIO;

    @Setter
    private View view;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final File selectedFile = view.showSaveDialog();

        if (selectedFile == null) {
            return;
        }

        final String filePathWithoutExtension = selectedFile.toString();
        final String filePath = String.format("%s.%s", filePathWithoutExtension, Constants.SCENE_EXTENSION);

        try {
            contextIO.write(context, filePath);
        } catch (ContextIOException exception) {
            view.showError("Cannot save scene");
        }
    }
}
