package customexceptions.customcommandexceptions;

public class ViewCommandException extends CommandException {
    public ViewCommandException(Exception exception) {
        super(exception);
    }
}
