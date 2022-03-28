package commands;

import catalog.Catalog;
import customexceptions.customcommandexceptions.CommandException;
import com.fasterxml.jackson.databind.ObjectMapper;
import customexceptions.customcommandexceptions.SaveCommandException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/** Class for saving a Catalog object as a JSON. */
public class SaveCommand<T extends List<Object>, V extends String> implements Command<T, V> {
    private int statusCode = 1;
    private Catalog catalog;
    private File toSaveFile;
    private String outputString;

    public SaveCommand(T args) {
        if (validateArgs(args)) {
            this.catalog = (Catalog) args.get(0);
            this.toSaveFile = new File((String) args.get(1));
        }
    }

    private boolean validateArgs(T args) {
        if (args.size() != 2 && !(args.get(0) instanceof Catalog) && !(args.get(1) instanceof String)) {
            outputString = "Wrong format for the command. Args are: catalog, filePath.";
            statusCode = 0;
            return false;
        }
        return true;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setArgs(T args) {
        if (validateArgs(args)) {
            this.catalog = (Catalog) args.get(0);
            this.toSaveFile = new File((String) args.get(1));
        }
    }

    @Override
    public void printArgs() {
        System.out.println("Catalog name: " + catalog.getName() + "\nCatalog content:");
        catalog.getItemList().stream()
                .forEach(System.out::println);
        System.out.println("File to save to path: " + toSaveFile.getPath());
    }

    @Override
    public void execute() throws CommandException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(toSaveFile, catalog);
        } catch (IOException err) {
            outputString = "Error while mapping to object to the given path.";
            statusCode = 0;
            err.printStackTrace();
            throw new SaveCommandException(err);
        }
    }

    @Override
    public V getCommandOutput() {
        if (statusCode == 0)
            return (V) outputString;
        else
            return (V) "Command successfully executed.";
    }
}
