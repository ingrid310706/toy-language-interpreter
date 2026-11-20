package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record WhileStmt(IExp cond, IStmt body) implements IStmt {                // S13
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = cond.eval(state.getSymTable(), state.getHeap());
        if (!(value instanceof BoolValue(boolean b)))
            throw new StatementException("while condition must be boolean");
        if (b) {
            state.getExeStack().push(this);     // the loop comes back after the body
            state.getExeStack().push(body);
        }
        return state;
    }

    @Override
    public String toString() {
        return "(while (" + cond + ") " + body + ")";
    }
}