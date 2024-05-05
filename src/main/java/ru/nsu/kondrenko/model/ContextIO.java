package ru.nsu.kondrenko.model;

public interface ContextIO {
    Context read(String path) throws ContextIOException;

    void write(Context context, String path) throws ContextIOException;
}
