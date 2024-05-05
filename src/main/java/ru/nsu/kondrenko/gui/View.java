package ru.nsu.kondrenko.gui;

import java.io.File;

public interface View {
    File showSaveDialog();

    File showOpenDialog();

    void showError(String errorMessage);

    void destroy();

    void show();
}
