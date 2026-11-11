package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RunExampleCommandTest {

    @TempDir
    Path dir;

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream captured;

    @BeforeEach
    void captureOutput() {
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    private Controller controllerFor(PrgState state) {
        return new Controller(
                new Repository(state, dir.resolve("log.txt").toString()), false);
    }

    @Test
    void runsTheExample() {
        PrgState state = PrgState.of(new VarDeclStmt("v", new IntType()));
        Command c = new RunExampleCommand("1", "example", controllerFor(state));

        c.execute();

        assertTrue(state.getExeStack().isEmpty());
        assertTrue(state.getSymTable().isDefined("v"));
    }

    @Test
    void anExampleRunsOnlyOnce() {
        PrgState state = PrgState.of(new VarDeclStmt("v", new IntType()));
        Command c = new RunExampleCommand("1", "example", controllerFor(state));

        c.execute();
        c.execute();

        assertTrue(captured.toString()
                .contains("This example has already been executed."));
    }

    @Test
    void anInterpreterErrorIsReportedNotThrown() {
        // printing an undeclared variable fails inside the controller
        PrgState state = PrgState.of(new PrintStmt(new VarExp("zzz")));
        Command c = new RunExampleCommand("1", "example", controllerFor(state));

        assertDoesNotThrow(c::execute);
        assertTrue(captured.toString().contains("Error: "));
    }
}