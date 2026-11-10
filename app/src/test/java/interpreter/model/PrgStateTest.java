package interpreter.model;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.adt.IStack;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;

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
                new MyDictionary<>(),
                program
        );

        assertSame(program, state.getOriginalProgram());
        assertSame(program, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void gettersReturnProvidedStructures() {
        IStack<IStmt> stack = new MyStack<>();
        IDictionary<String, IValue> symTable = new MyDictionary<>();
        IList<IValue> out = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable =
                new MyDictionary<>();

        PrgState state = new PrgState(
                stack,
                symTable,
                out,
                fileTable,
                dummyProgram()
        );

        assertSame(stack, state.getExeStack());
        assertSame(symTable, state.getSymTable());
        assertSame(out, state.getOut());
        assertSame(fileTable, state.getFileTable());
    }

    @Test
    void toStringDisplaysAllStructures() {
        PrgState state = new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                new MyDictionary<>(),
                dummyProgram()
        );

        String text = state.toString();

        assertTrue(text.contains("ExeStack:"));
        assertTrue(text.contains("SymTable:"));
        assertTrue(text.contains("Out:"));
        assertTrue(text.contains("FileTable:"));
    }

    @Test
    void ofCreatesProgramState() throws MyException {
        IStmt program = dummyProgram();

        PrgState state = PrgState.of(program);

        assertSame(program, state.getOriginalProgram());
        assertSame(program, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }
}