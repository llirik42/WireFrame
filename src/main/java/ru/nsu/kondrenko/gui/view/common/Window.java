package ru.nsu.kondrenko.gui.view.common;

import javax.swing.*;
import java.awt.*;

public abstract class Window extends JFrame {
    public Window() {
        setPreferredSize(new Dimension(1280, 720));
        setMinimumSize(new Dimension(640, 480));
    }
}
