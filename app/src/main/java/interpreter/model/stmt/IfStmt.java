package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.exceptions.StatementException;

public record IfStmt(IExp cond  ,IStmt thenS , IStmt elseS ) implements IStmt {
    @Override
    public PrgState execute(PrgState state)
        throws MyException {
        IValue value = cond.eval(state.getSymTable());
        if(!(value instanceof BoolValue)){
            throw new StatementException("if condition must be boolean");
        }
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.val()) {
            state.getExeStack().push(thenS);
        } else {
            state.getExeStack().push(elseS);
        }

        return state;

    }
}
