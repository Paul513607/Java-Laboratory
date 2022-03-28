package customexceptions.customcommandexceptions;

public class ReportCommandException extends CommandException {
    public ReportCommandException(Exception exception) {
        super(exception);
    }
}
