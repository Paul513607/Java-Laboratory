package commands;

import catalog.Catalog;
import customexceptions.customcommandexceptions.CommandException;
import customexceptions.customcommandexceptions.ListCommandException;

/** Class for listing the catalog data. */
public class ListCommand<T extends Catalog, V extends String> implements Command<T, V> {
    private int statusCode = 1;
    private T catalog;

    public ListCommand(T catalog) {
        this.catalog = catalog;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setArgs(T catalog) {
        this.catalog = catalog;
    }

    @Override
    public void printArgs() {
        System.out.println("Catalog name: " + catalog.getName() + "\nCatalog content:");
        catalog.getItemList().stream()
                .forEach(System.out::println);
    }

    @Override
    public void execute() throws CommandException {
        if (statusCode == 0)
            throw new ListCommandException(new Exception("Can't print the catalog."));
        System.out.println("Catalog name: " + catalog.getName() + "\nCatalog content:");
        catalog.getItemList().stream()
                .forEach(System.out::println);
    }

    @Override
    public V getCommandOutput() {
        if (statusCode == 1) {
            return (V) "Command successfully executed.";
        }
        return (V) "An error has occurred while listing the catalog content.";
    }
}
