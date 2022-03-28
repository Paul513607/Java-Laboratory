package customexceptions.customcommandexceptions;

public class LoadCommandException extends CommandException {
    public LoadCommandException(Exception exception) {
        super(exception);
    }
}
