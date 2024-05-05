package ru.nsu.kondrenko.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileContextIO implements ContextIO {
    public Context read(String path) throws ContextIOException {
        System.out.println(path);
        try (final FileInputStream fileInputStream = new FileInputStream(path);
             final ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            return (Context) inputStream.readObject();
        } catch (Exception exception) {
            throw new ContextIOException("Cannot read context", exception);
        }
    }

    public void write(Context context, String path) throws ContextIOException {
        try (final FileOutputStream fileOutputStream = new FileOutputStream(path);
             final ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream)) {
            outputStream.writeObject(context);
        } catch (Exception exception) {
            throw new ContextIOException("Cannot write context", exception);
        }
    }
}
