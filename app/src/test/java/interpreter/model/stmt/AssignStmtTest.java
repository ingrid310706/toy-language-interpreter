
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {

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
    void assignIntegerWorks() throws MyException {
        PrgState state = createState();
        state.getSymTable().put("x", new IntValue(0));

        IStmt stmt = new AssignStmt("x",
                new ArithExp(ArithOp.ADD,
                        new ValueExp(new IntValue(2)),
                        new ValueExp(new IntValue(3))));

        stmt.execute(state);

        assertEquals(new IntValue(5),
                state.getSymTable().lookup("x"));
    }

    @Test
    void assignBooleanWorks() throws MyException {
        PrgState state = createState();
        state.getSymTable().put("a", new BoolValue(false));

        IStmt stmt = new AssignStmt("a",
                new ValueExp(new BoolValue(true)));

        stmt.execute(state);

        assertEquals(new BoolValue(true),
                state.getSymTable().lookup("a"));
    }

    @Test
    void undeclaredVariableThrows() {
        PrgState state = createState();

        IStmt stmt = new AssignStmt("x",
                new ValueExp(new IntValue(5)));

        StatementException ex = assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );

        assertEquals("variable x was not declared",
                ex.getMessage());
    }

    @Test
    void typeMismatchThrows() throws MyException {
        PrgState state = createState();
        state.getSymTable().put("x", new IntValue(0));

        IStmt stmt = new AssignStmt("x",
                new ValueExp(new BoolValue(true)));

        StatementException ex = assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );

        assertEquals(
                "declared type of variable x and type of the assigned expression do not match",
                ex.getMessage()
        );

        assertEquals(new IntValue(0),
                state.getSymTable().lookup("x"));
    }
}
