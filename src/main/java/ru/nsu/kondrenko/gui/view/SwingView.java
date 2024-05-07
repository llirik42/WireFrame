package ru.nsu.kondrenko.gui.view;

import ru.nsu.kondrenko.gui.view.bspline.BSplineWindow;
import ru.nsu.kondrenko.gui.view.wireframe.WireframeWindow;
import ru.nsu.kondrenko.model.Constants;
import ru.nsu.kondrenko.model.context.BSplineListener;
import ru.nsu.kondrenko.model.context.Context;
import ru.nsu.kondrenko.model.context.FormDataListener;
import ru.nsu.kondrenko.model.context.WireframeListener;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.File;

public class SwingView implements View, BSplineListener, WireframeListener, FormDataListener {
    private final BSplineWindow bSplineWindow;
    private final WireframeWindow wireframeWindow;
    private final JFileChooser savingChooser;
    private final JFileChooser openChooser;
    private final JTextPane aboutTextPane;

    public SwingView(Context context,
                     ComponentListener bSplineEditorListener,
                     WindowListener windowListener,
                     ActionListener openListener,
                     ActionListener saveListener,
                     ActionListener exitListener,
                     ActionListener resetAngleListener,
                     ActionListener resetDistanceListener,
                     ActionListener bSplineNormalizationListener,
                     ActionListener bSplineClearListener,
                     ActionListener aboutListener,
                     KeyListener keyListener,
                     MouseListener bSplineMouseListener,
                     MouseMotionListener bSplineMouseMotionListener,
                     MouseWheelListener bSplineMouseWheelListener,
                     MouseListener wireframeMouseListener,
                     MouseMotionListener wireframeMouseMotionListener,
                     MouseWheelListener wireframeMouseWheelListener) {
        aboutTextPane = createAboutTextArea();
        bSplineWindow = new BSplineWindow(
                context,
                bSplineEditorListener,
                openListener,
                saveListener,
                exitListener,
                bSplineNormalizationListener,
                bSplineClearListener,
                aboutListener,
                keyListener,
                bSplineMouseListener,
                bSplineMouseMotionListener,
                bSplineMouseWheelListener
        );
        wireframeWindow = new WireframeWindow(
                context,
                openListener,
                saveListener,
                exitListener,
                resetAngleListener,
                resetDistanceListener,
                aboutListener,
                wireframeMouseListener,
                wireframeMouseMotionListener,
                wireframeMouseWheelListener
        );

        bSplineWindow.addWindowListener(windowListener);
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
        bSplineWindow.setVisible(false);
        wireframeWindow.setVisible(false);
        bSplineWindow.dispose();
        wireframeWindow.dispose();
    }

    @Override
    public void showAbout() {
        JOptionPane.showMessageDialog(
                null,
                aboutTextPane,
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void show() {
        bSplineWindow.setVisible(true);
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

    private static JTextPane createAboutTextArea() {
        final JTextPane result = createHelpAboutTextPane();
        result.setText(getAboutText());
        return result;
    }

    private static JTextPane createHelpAboutTextPane() {
        final JTextPane result = new JTextPane();
        result.setEditable(false);
        result.setBackground(null);
        result.setContentType("text/html");
        return result;
    }

    private static String getAboutText() {
        return """
                <html>
                     <p><b><i>Wireframe</i></b> represents a program for visualising simple rotation shapes</p>
                     <b>Created by</b>: Kondrenko Kirill, student of group 21203 in NSU in May 2024 as task of the course "Engineering and computer graphics"</b>
                </html>
                """;
    }

    @Override
    public void onBSplineContextChange(Context context) {
        bSplineWindow.onBSplineContextChange(context);
    }

    @Override
    public void onWireframeChange(Context context) {
        wireframeWindow.onWireframeChange(context);
    }

    @Override
    public void onFormDataChange(Context context) {
        bSplineWindow.onFormDataChange(context);
    }
}
