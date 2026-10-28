
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {

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
    void declaringIntegerInitializesToZero() throws MyException {
        PrgState state = createState();

        IStmt stmt = new VarDeclStmt("x", new IntType());
        stmt.execute(state);

        assertEquals(new IntValue(0),
                state.getSymTable().lookup("x"));
    }

    @Test
    void declaringBooleanInitializesToFalse() throws MyException {
        PrgState state = createState();

        IStmt stmt = new VarDeclStmt("a", new BoolType());
        stmt.execute(state);

        assertEquals(new BoolValue(false),
                state.getSymTable().lookup("a"));
    }

    @Test
    void declaringSameVariableTwiceThrows() throws MyException {
        PrgState state = createState();

        IStmt stmt = new VarDeclStmt("x", new IntType());
        stmt.execute(state);

        StatementException ex = assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );

        assertEquals("variable x is already declared",
                ex.getMessage());

        assertEquals(new IntValue(0),
                state.getSymTable().lookup("x"));
    }

    @Test
    void toStringDisplaysDeclaration() {
        IStmt intStmt = new VarDeclStmt("x", new IntType());
        IStmt boolStmt = new VarDeclStmt("a", new BoolType());

        assertEquals("int x", intStmt.toString());
        assertEquals("bool a", boolStmt.toString());
    }
}
