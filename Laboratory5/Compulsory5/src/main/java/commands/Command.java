package commands;

import customexceptions.customcommandexceptions.CommandException;

/** Interface from which other commands define how they execute.
    The "T" generic defines the arguments type, and the "V" generic defines the command output type. */
public interface Command<T, V> {
    int STATUS_CODE = 1;
    default int getStatusCode() {
        return STATUS_CODE;
    }
    void setArgs(T args);
    void printArgs();
    void execute() throws CommandException;
    V getCommandOutput();
}
