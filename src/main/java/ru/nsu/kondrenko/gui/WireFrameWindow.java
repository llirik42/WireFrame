package ru.nsu.kondrenko.gui;

import javax.swing.*;
import java.awt.*;

public class WireFrameWindow extends JFrame {
    public WireFrameWindow() {
        setPreferredSize(new Dimension(1280, 720));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }
}
