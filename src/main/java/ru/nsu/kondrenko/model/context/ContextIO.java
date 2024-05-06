package ru.nsu.kondrenko.model.context;

import ru.nsu.kondrenko.model.ContextIOException;

public interface ContextIO {
    Context read(String path) throws ContextIOException;

    void write(Context context, String path) throws ContextIOException;
}
