
package interpreter.model;

import interpreter.exceptions.MyException;
import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {

    private static IStmt dummyProgram() {
        return new IStmt() {
            @Override
            public PrgState execute(PrgState state) throws MyException {
                return state;
            }

            @Override
            public String toString() {
                return "dummy";
            }
        };
    }

    @Test
    void constructorPushesOriginalProgram() throws MyException {
        IStmt program = dummyProgram();

        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );

        assertSame(program, state.getOriginalProgram());
        assertSame(program, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void symbolTableAndOutputAreInitiallyEmpty() {
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                dummyProgram()
        );

        assertEquals("{}", state.getSymTable().toString());
        assertEquals(0, state.getOut().size());
    }

    @Test
    void toStringDisplaysAllThreeStructures() {
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                dummyProgram()
        );

        String text = state.toString();

        assertTrue(text.contains("ExeStack:"));
        assertTrue(text.contains("SymTable:"));
        assertTrue(text.contains("Out:"));
    }

    @Test
    void gettersReturnProvidedStructures() {
        IStack<IStmt> stack = new MyStack<>();
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IList<IValue> out = new MyList<>();

        PrgState state = new PrgState(
                stack, symTable, out, dummyProgram()
        );

        assertSame(stack, state.getExeStack());
        assertSame(symTable, state.getSymTable());
        assertSame(out, state.getOut());
    }
}
