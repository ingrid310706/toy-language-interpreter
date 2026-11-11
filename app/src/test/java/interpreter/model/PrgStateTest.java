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

import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.value.IntValue;

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

    //added 3 tests after modifying prg state

    @Test
    void logStringHasTheFourSectionsInOrder() {
        String log = PrgState.of(dummyProgram()).toLogString();

        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
                c = log.indexOf("Out:"), d = log.indexOf("FileTable:");

        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    void logStringPutsEachElementOnItsOwnLine() throws MyException {
        PrgState state = PrgState.of(dummyProgram());
        state.getSymTable().put("x", new IntValue(5));
        state.getOut().add(new IntValue(10));
        state.getFileTable().put(new StringValue("test.in"),
                new BufferedReader(new java.io.StringReader("")));

        assertEquals(
                "ExeStack:\ndummy\n"
                        + "SymTable:\nx --> 5\n"
                        + "Out:\n10\n"
                        + "FileTable:\ntest.in\n",
                state.toLogString());
    }

    @Test
    void logStringListsTheTopOfTheStackFirst() throws MyException {
        PrgState state = PrgState.of(new PrintStmt(new ValueExp(new IntValue(1))));
        state.getExeStack().push(new PrintStmt(new ValueExp(new IntValue(2))));

        String log = state.toLogString();

        assertTrue(log.indexOf("print(2)") < log.indexOf("print(1)"));
    }
}