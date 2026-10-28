
package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {

    private PrgState createState() {
        IStmt dummy = state -> state;

        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                dummy
        );
    }

    @Test
    void printsIntegerValue() throws MyException {
        PrgState state = createState();

        IStmt stmt = new PrintStmt(
                new ValueExp(new IntValue(5))
        );

        stmt.execute(state);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void printsBooleanValue() throws MyException {
        PrgState state = createState();

        IStmt stmt = new PrintStmt(
                new ValueExp(new BoolValue(true))
        );

        stmt.execute(state);

        assertEquals(new BoolValue(true), state.getOut().get(0));
    }

    @Test
    void printsVariableValue() throws MyException {
        PrgState state = createState();
        state.getSymTable().put("x", new IntValue(10));

        IStmt stmt = new PrintStmt(new VarExp("x"));

        stmt.execute(state);

        assertEquals(new IntValue(10), state.getOut().get(0));
    }

    @Test
    void undefinedVariableThrows() {
        PrgState state = createState();

        IStmt stmt = new PrintStmt(new VarExp("x"));

        ExpressionException ex = assertThrows(
                ExpressionException.class,
                () -> stmt.execute(state)
        );

        assertEquals("variable x is not defined", ex.getMessage());
        assertEquals(0, state.getOut().size());
    }
}
