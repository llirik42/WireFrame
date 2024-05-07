package ru.nsu.kondrenko.gui.view.common;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.event.ActionListener;

@RequiredArgsConstructor
public class DefaultMenuArea extends MenuArea {
    private final ActionListener openListener;
    private final ActionListener saveListener;
    private final ActionListener exitListener;
    private final ActionListener aboutListener;

    protected JMenu createFileMenu() {
        final JMenu result = createMenu("File");
        result.add(createItem("Open", openListener));
        result.add(createItem("Save", saveListener));
        result.add(createItem("Exit", exitListener));
        return result;
    }

    protected JMenu createInfoMenu() {
        final JMenu result = createMenu("About");
        result.add(createItem("About", aboutListener));
        return result;
    }
}
