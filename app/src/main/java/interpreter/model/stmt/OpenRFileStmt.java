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

public record OpenRFileStmt(IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IValue value = exp.eval(state.getSymTable());

        if (!(value instanceof StringValue stringValue)) {
            throw new StatementException(
                    "openRFile: " + exp + " is not a string"
            );
        }

        if (state.getFileTable().isDefined(stringValue)) {
            throw new StatementException(
                    "openRFile: " + stringValue + " is already open"
            );
        }

        try {
            // no try-with-resources: the file must stay open until closeRFile
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new FileReader(stringValue.val())
                    );

            state.getFileTable().put(stringValue, bufferedReader);

        } catch (IOException e) {
            throw new StatementException(
                    "openRFile: cannot open " + stringValue
                            + ": " + e.getMessage()
            );
        }

        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")";
    }
}