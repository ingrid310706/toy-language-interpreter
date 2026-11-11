package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class OpenRFileStmtTest {

    @TempDir
    Path tempDir;

    @Test
    void opensFileAndAddsItToFileTable()
            throws MyException, IOException {

        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "10");

        PrgState state = PrgState.of(new NopStmt());

        StringValue fileName = new StringValue(file.toString());

        IStmt stmt = new OpenRFileStmt(
                new ValueExp(fileName)
        );

        assertSame(state, stmt.execute(state));

        assertTrue(
                state.getFileTable().isDefined(fileName)
        );

        state.getFileTable().lookup(fileName).close();
    }

    @Test
    void openingSameFileTwiceThrows()
            throws MyException, IOException {

        Path file = tempDir.resolve("test.txt");
        Files.writeString(file, "10");

        PrgState state = PrgState.of(new NopStmt());

        StringValue fileName = new StringValue(file.toString());

        IStmt stmt = new OpenRFileStmt(
                new ValueExp(fileName)
        );

        stmt.execute(state);

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );

        state.getFileTable().lookup(fileName).close();
    }

    @Test
    void nonStringExpressionThrows() {

        PrgState state = PrgState.of(new NopStmt());

        IStmt stmt = new OpenRFileStmt(
                new ValueExp(new IntValue(10))
        );

        assertThrows(
                StatementException.class,
                () -> stmt.execute(state)
        );
    }
}