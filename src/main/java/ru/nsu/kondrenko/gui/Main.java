package ru.nsu.kondrenko.gui;

import ru.nsu.kondrenko.model.Context;

public class Main {
    public static void main(String[] args) {
        // TODO: сделать масштабирование, перемещение и нормализацию в редакторе б-сплайнов
        // TODO: сделать редактор б-сплайнов resizable
        final Context context = new Context();
        new BSplineEditorWindow(context);
        context.addListener(new WireFrameWindow(context));
    }
}
