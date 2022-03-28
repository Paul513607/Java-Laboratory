package customexceptions.customcommandexceptions;

public class ListCommandException extends CommandException {
    public ListCommandException(Exception exception) {
        super(exception);
    }
}
