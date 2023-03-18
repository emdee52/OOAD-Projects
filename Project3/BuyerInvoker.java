/*
 * 
 * Part of the implementation of the command pattern for invoker
 * 
 */

import java.util.ArrayList;

public class BuyerInvoker {
    private ArrayList<BuyerCommand> commandList = new ArrayList<BuyerCommand>();

    public void takeCommand(BuyerCommand command) {
        commandList.add(command);
    }

    public void placeCommand() {
        for (BuyerCommand command : commandList) {
            command.execute();
        }
        commandList.clear();
    }
}
