package customexceptions.customcommandexceptions;

/** A generic exception for a Command error. */
public class CommandException extends Exception {
    public CommandException(Exception exception) {
        super("Can't run the command.", exception);
    }
}
