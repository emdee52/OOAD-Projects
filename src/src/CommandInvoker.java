import java.util.ArrayList;

public class CommandInvoker {
    private ArrayList<ActionsCommand> commandList = new ArrayList<>();

    public void addCommand(ActionsCommand command) {
        commandList.add(command);
    }

    public void placeCommand() {
        for (ActionsCommand command : commandList) {
            command.execute();
        }
        commandList.clear();
    }
}