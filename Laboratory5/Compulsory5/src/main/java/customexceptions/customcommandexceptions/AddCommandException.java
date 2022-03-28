package customexceptions.customcommandexceptions;

public class AddCommandException extends CommandException {
    public AddCommandException(Exception exception) {
        super(exception);
    }
}
