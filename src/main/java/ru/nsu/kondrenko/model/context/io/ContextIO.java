package ru.nsu.kondrenko.model.context.io;

import ru.nsu.kondrenko.model.context.Context;

public interface ContextIO {
    Context read(String path) throws ContextIOException;

    void write(Context context, String path) throws ContextIOException;
}
