package ru.nsu.kondrenko.controller.menu;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.nsu.kondrenko.gui.View;
import ru.nsu.kondrenko.model.Context;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

@RequiredArgsConstructor
public class OpenController implements ActionListener {
    private final Context context;

    @Setter
    private View view;

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        final File selectedFile = view.showOpenDialog();

        if (selectedFile == null) {
            return;
        }

        try (final FileInputStream fileInputStream = new FileInputStream(selectedFile);
             final ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            final Context contextFromFile = (Context) inputStream.readObject();

            System.out.println(contextFromFile);
        } catch (Exception exception) {
            view.showError("Cannot open scene");
        }
    }
}
