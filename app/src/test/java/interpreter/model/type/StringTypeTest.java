
package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {

    @Test
    void stringVariableGetsEmptyDefaultValue() throws MyException {
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new NopStmt()
        );

        new VarDeclStmt("name", new StringType()).execute(state);

        assertEquals(
                new StringValue(""),
                state.getSymTable().lookup("name")
        );
    }
}
