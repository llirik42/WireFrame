package ru.nsu.kondrenko.model.context;

import ru.nsu.kondrenko.model.ContextIOException;

import java.io.*;

public class FileContextIO implements ContextIO {
    public Context read(String path) throws ContextIOException {
        try (final FileInputStream fileInputStream = new FileInputStream(path);
             final ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            return (Context) inputStream.readObject();
        } catch (ClassNotFoundException | InvalidClassException | StreamCorruptedException |
                 OptionalDataException exception) {
            throw new ContextIOException("Invalid format of file", exception);
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
