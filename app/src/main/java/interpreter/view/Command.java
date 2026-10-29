
package interpreter.view;

import interpreter.exceptions.MyException;

public interface Command {

    String getKey();

    String getDescription();

    void execute() throws MyException;
}
