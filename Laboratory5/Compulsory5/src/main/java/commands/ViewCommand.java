package commands;

import customexceptions.customcommandexceptions.CommandException;
import items.Item;
import customexceptions.customcommandexceptions.ViewCommandException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/** Class for viewing the contents of an Item object in the default OS app. */
public class ViewCommand<T extends Item, V extends String> implements Command<T, V>{
    private int statusCode = 1;
    private T item;

    public ViewCommand(T item) {
        this.item = item;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setArgs(T item) {
        this.item = item;
    }

    @Override
    public void printArgs() {
        System.out.println(item);
    }

    @Override
    public void execute() throws CommandException {
        Desktop desktop = Desktop.getDesktop();
        try {
            try {
                desktop.browse(new URI(item.getLocation()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            statusCode = 0;
            e.printStackTrace();
            throw new ViewCommandException(e);
        }
    }

    @Override
    public V getCommandOutput() {
        if (statusCode == 1) {
            return (V) "Command successfully executed.";
        }
        return (V) "An error has occurred while listing the catalog content.";
    }
}
