package interpreter.view;

public class ExitCommand implements  Command {
    @Override
    public String getKey() {
        return "0";
    }

    @Override
    public String getDescription() {
        return "Exit";
    }

    @Override
    public void execute() {
        System.exit(0);
    }

}
