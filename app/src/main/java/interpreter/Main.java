
package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;

public class Main {

    private static Controller createController(IStmt program) {

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );

        Repository repo = new Repository(state);

        return new Controller(repo, true);
    }

    public static void main(String[] args) {

        Controller controller1 = createController(Examples.example1());
        Controller controller2 = createController(Examples.example2());
        Controller controller3 = createController(Examples.example3());

        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand());

        menu.addCommand(new RunExampleCommand(
                "1", "Run Example 1", controller1
        ));

        menu.addCommand(new RunExampleCommand(
                "2", "Run Example 2", controller2
        ));

        menu.addCommand(new RunExampleCommand(
                "3", "Run Example 3", controller3
        ));

        menu.show();
    }
}
