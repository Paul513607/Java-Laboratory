package commands;

import catalog.Catalog;
import customexceptions.customcommandexceptions.CommandException;
import com.fasterxml.jackson.databind.ObjectMapper;
import customexceptions.customcommandexceptions.LoadCommandException;

import java.io.File;

/** Class for loading the JSON data from a file into a new Catalog object. */
public class LoadCommand<T extends String, V extends Catalog> implements Command<T, V> {
    private int statusCode = 1;
    private File fileToRead;
    private Catalog outputCatalog;

    public LoadCommand(T path) {
        this.fileToRead = new File(path);
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setArgs(T args) {
        this.fileToRead = new File(args);
    }

    @Override
    public void printArgs() {
        System.out.println("File to read path: " + fileToRead.getPath());
    }

    @Override
    public void execute() throws CommandException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            outputCatalog = objectMapper.readValue(fileToRead, Catalog.class);
        } catch (Exception e) {
            statusCode = 0;
            e.printStackTrace();
            throw new LoadCommandException(e);
        }
    }

    @Override
    public V getCommandOutput() {
        if (statusCode == 0)
            return (V) new Catalog();
        return (V) outputCatalog;
    }
}
