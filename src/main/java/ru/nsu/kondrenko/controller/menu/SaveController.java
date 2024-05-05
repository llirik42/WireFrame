package ru.nsu.kondrenko.controller.menu;

import lombok.Setter;
import ru.nsu.kondrenko.gui.View;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveController implements ActionListener {
    private final Context context;

    @Setter
    private View view;

    public SaveController(Context context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final File selectedFile = view.showSaveDialog();

        if (selectedFile == null) {
            return;
        }

        final String filePathWithoutExtension = selectedFile.toString();
        final String filePath = String.format("%s.%s", filePathWithoutExtension, Constants.SCENE_EXTENSION);

        try (final FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             final ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            outputStream.writeObject(context);
        } catch (Exception exception) {
            view.showError("Cannot save scene");
        }
    }
}
