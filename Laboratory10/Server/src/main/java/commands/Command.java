package commands;

public interface Command {
    void execute();
    boolean isStatusCode();
    String getErrorMsg();
    String getOutput();
}
