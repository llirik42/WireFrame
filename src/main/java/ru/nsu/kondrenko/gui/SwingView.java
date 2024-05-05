package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.Context;
import ru.nsu.kondrenko.model.ContextListener;
import ru.nsu.kondrenko.model.IntPoint;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.File;

public class SwingView implements View, ContextListener {
    private final BSplineEditorWindow bSplineEditorWindow;
    private final WireframeWindow wireframeWindow;
    private final JFileChooser savingChooser;
    private final JFileChooser openChooser;
    private final Context context;

    public SwingView(Context context,
                     WindowListener windowListener,
                     ActionListener openListener,
                     ActionListener saveListener,
                     ActionListener exitListener,
                     ActionListener resetAngleListener,
                     ActionListener resetDistanceListener,
                     ActionListener bSplineNormalizationListener,
                     ActionListener helpListener,
                     ActionListener aboutListener) {
        this.context = context;
        bSplineEditorWindow = new BSplineEditorWindow(
                context,
                openListener,
                saveListener,
                exitListener,
                bSplineNormalizationListener,
                helpListener,
                aboutListener
        );
        wireframeWindow = new WireframeWindow(
                context,
                openListener,
                saveListener,
                exitListener,
                resetAngleListener,
                resetDistanceListener,
                helpListener,
                aboutListener
        );

        bSplineEditorWindow.addWindowListener(windowListener);
        wireframeWindow.addWindowListener(windowListener);

        final FileFilter fileFilter = new FileNameExtensionFilter(
                Constants.SCENE_EXTENSION,
                Constants.SCENE_EXTENSION
        );

        openChooser = new JFileChooser();
        openChooser.setFileFilter(fileFilter);

        savingChooser = new JFileChooser();
    }

    @Override
    public void destroy() {
        bSplineEditorWindow.setVisible(false);
        wireframeWindow.setVisible(false);
        bSplineEditorWindow.dispose();
        wireframeWindow.dispose();
    }

    @Override
    public void show() {
        bSplineEditorWindow.setVisible(true);
        wireframeWindow.setVisible(true);
    }

    @Override
    public File showSaveDialog() {
        final int code = savingChooser.showSaveDialog(null);

        if (code == JFileChooser.CANCEL_OPTION) {
            return null;
        }

        if (code == JFileChooser.ERROR_OPTION) {
            showError("Saving failed");
            return null;
        }

        return savingChooser.getSelectedFile();
    }

    @Override
    public File showOpenDialog() {
        final int code = openChooser.showOpenDialog(null);

        if (code == JFileChooser.CANCEL_OPTION) {
            return null;
        }

        if (code == JFileChooser.ERROR_OPTION) {
            showError("Opening failed");
            return null;
        }

        return openChooser.getSelectedFile();
    }

    @Override
    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(
                null,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    @Override
    public void showWarning(String warningMessage) {
        JOptionPane.showMessageDialog(
                null,
                warningMessage,
                "Warning",
                JOptionPane.WARNING_MESSAGE
        );
    }

    @Override
    public void onContextChange(Context context) {
        wireframeWindow.onContextChange(context);
    }
}
