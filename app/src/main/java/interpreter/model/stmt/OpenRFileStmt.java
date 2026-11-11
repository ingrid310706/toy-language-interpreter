package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(IExp expression) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IValue value = expression.eval(state.getSymTable());

        if (!(value instanceof StringValue stringValue)) {
            throw new StatementException(
                    "The expression must evaluate to a string"
            );
        }

        if (state.getFileTable().isDefined(stringValue)) {
            throw new StatementException(
                    "File is already open"
            );
        }

        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new FileReader(stringValue.val())
                    );

            state.getFileTable().put(stringValue, bufferedReader);

        } catch (IOException e) {
            throw new StatementException(
                    "Could not open file: " + stringValue.val()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + expression + ")";
    }
}