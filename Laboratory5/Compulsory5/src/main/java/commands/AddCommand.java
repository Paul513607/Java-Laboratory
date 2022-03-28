package commands;

import catalog.Catalog;
import customexceptions.SameIdItemExistsException;
import customexceptions.customcommandexceptions.AddCommandException;
import customexceptions.customcommandexceptions.CommandException;
import items.Item;

import java.util.ArrayList;
import java.util.List;

/** Class for adding an item to the given catalog. */
public class AddCommand<T extends List<Object>, V extends String> implements Command<T, V> {
    private int statusCode = 1;
    private Catalog catalog;
    private List<Item> items = new ArrayList<>();
    private String outputString = "";

    public AddCommand(T args) {
        if (validateArgs(args)) {
            this.catalog = (Catalog) args.get(0);
            for (int index = 1; index < args.size(); ++index)
                items.add((Item) args.get(index));
        }
    }

    private boolean validateArgs(T args) {
        if (!(args.get(0) instanceof Catalog)) {
            outputString = "Wrong format for the command. Args are: catalog, item1, item2...";
            statusCode = 0;
            return false;
        }
        for (int index = 1; index < args.size(); ++index)
            if (!(args.get(index) instanceof Item)) {
                outputString = "Wrong format for the command. Args are: catalog, item1, item2...";
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
            for (int index = 1; index < args.size(); ++index)
                items.add((Item) args.get(index));
        }
    }

    @Override
    public void printArgs() {
        System.out.println("Catalog name: " + catalog.getName() + "\nCatalog content:");
        catalog.getItemList().stream()
                .forEach(System.out::println);
        System.out.println("\nItem list to add:\n");
        items.stream()
                .forEach(System.out::println);
    }

    @Override
    public void execute() throws CommandException {
        if (statusCode == 0)
            throw new AddCommandException(new Exception(outputString));
        for (Item item : items) {
            try {
                catalog.add(item);
            } catch (SameIdItemExistsException e) {
                statusCode = 0;
                e.printStackTrace();
            }
        }
    }

    @Override
    public V getCommandOutput() {
        if (statusCode == 1) {
            return (V) "Command successfully executed.";
        }
        return (V) ("An error has occurred while listing the catalog content. " + outputString);
    }
}
