package customexceptions.customcommandexceptions;

public class SaveCommandException extends CommandException {
    public SaveCommandException(Exception exception) {
        super(exception);
    }
}
