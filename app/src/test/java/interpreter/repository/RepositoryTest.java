
package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private PrgState createState() {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );
    }

    @Test
    void getCrtPrgReturnsInitialProgram() {
        PrgState state = createState();

        IRepository repo = new Repository(state);

        assertSame(state, repo.getCrtPrg());
    }

    @Test
    void getCrtPrgReturnsSameStateAfterChanges() throws MyException {
        PrgState state = createState();

        IRepository repo = new Repository(state);

        state.getSymTable().put("x", new IntValue(5));
        state.getOut().add(new IntValue(10));

        PrgState retrieved = repo.getCrtPrg();

        assertSame(state, retrieved);
        assertEquals(new IntValue(5),
                retrieved.getSymTable().lookup("x"));
        assertEquals(new IntValue(10),
                retrieved.getOut().get(0));
    }
}
