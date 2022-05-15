package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** Abstract class that contains methods to parse a command. */
public abstract class CommandParser {
    public static List<String> parseCommand(String commandLine) {
        if (commandLine == null) {
            return List.of("");
        }

        List<String> args = new ArrayList<>();
        commandLine = commandLine.trim();
        Collections.addAll(args, commandLine.split(" "));
        return args;
    }
}
