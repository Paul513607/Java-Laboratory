package commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpCommand implements Command {
    private List<String> args = new ArrayList<>();

    private String output;

    private String errorMsg;
    private boolean statusCode;

    public HelpCommand(List<String> args) {
        this.args = args;
    }

    @Override
    public String getOutput() {
        return output;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public boolean isStatusCode() {
        return statusCode;
    }

    @Override
    public void execute() {
        statusCode = true;
        output = "|The following commands can be entered without being logged in:|" +
                "'help' --- shows which commands you can run on the server|" +
                "'register <name>' --- add a new user to the server's database|" +
                "'login <name>' --- login as an existing user with the name <name>|" +
                "The following commands can be entered only while logged in:|" +
                "'stop' --- logs the user out and stops the entries to the server|" +
                "'friend <name1> <name2> ... <nameK>' --- adds the users <name1..k> to the current user's friends list|" +
                "'send <message>' --- sends a message to all friends|" +
                "'read' --- reads the messages for the current user|";
    }
}
