package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeapExamplesTest {

    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        new Controller(new Repository(state, dir.resolve("log.txt").toString()), false)
                .allStep();
        return state;
    }

    private static IValue i(int n) {
        return new IntValue(n);
    }

    @Test
    void ex6PrintsTheTwoReferences() throws MyException {
        PrgState state = run(Examples.EX6);

        assertEquals(List.of(new RefValue(1, new IntType()),
                        new RefValue(2, new RefType(new IntType()))),
                state.getOut().toList());
    }

    @Test
    void ex7Prints20And25() throws MyException {
        assertEquals(List.of(i(20), i(25)), run(Examples.EX7).getOut().toList());
    }

    @Test
    void ex8Prints20And35() throws MyException {
        assertEquals(List.of(i(20), i(35)), run(Examples.EX8).getOut().toList());
    }

    @Test
    void ex9Prints20BecauseTheSafeCollectorKeepsAddressOne() throws MyException {
        PrgState state = run(Examples.EX9);

        assertEquals(List.of(i(20)), state.getOut().toList());
        assertEquals(java.util.Set.of(1, 2, 3), state.getHeap().getContent().keySet());
    }

    @Test
    void ex10CountsDownFromFourToZero() throws MyException {
        assertEquals(List.of(i(4), i(3), i(2), i(1), i(0)),
                run(Examples.EX10).getOut().toList());
    }
}