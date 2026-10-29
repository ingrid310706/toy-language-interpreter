package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;

public class RunExampleCommand implements Command {
    private final String key;
    private final String description;
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        this.key = key;
        this.description = description;
        this.controller = controller;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute() throws MyException {
        controller.allStep();
    }
}
