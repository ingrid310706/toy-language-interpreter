package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CloseRFileStmtTest {

    @TempDir
    Path dir;

    private PrgState state;
    private StringValue fileName;
    private ValueExp file;

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        fileName = new StringValue(f.toString());
        file = new ValueExp(fileName);
        state = PrgState.of(new NopStmt());
    }

    @Test
    void closingRemovesTheFileFromTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().isDefined(fileName));

        assertSame(state, new CloseRFileStmt(file).execute(state));

        assertFalse(state.getFileTable().isDefined(fileName));
    }

    @Test
    void theReaderIsReallyClosed() throws MyException {
        new OpenRFileStmt(file).execute(state);
        BufferedReader reader = state.getFileTable().lookup(fileName);

        new CloseRFileStmt(file).execute(state);

        assertThrows(IOException.class, reader::read);
    }

    @Test
    void aClosedFileCanBeOpenedAgain() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);

        new OpenRFileStmt(file).execute(state);

        assertTrue(state.getFileTable().isDefined(fileName));
        new CloseRFileStmt(file).execute(state);
    }

    @Test
    void closingAFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(file).execute(state));
    }

    @Test
    void nonStringExpressionThrows() {
        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(new ValueExp(new IntValue(3)))
                        .execute(state));
    }

    @Test
    void toStringIsInfixForm() {
        assertEquals("closeRFile(\"a.in\")",
                new CloseRFileStmt(new ValueExp(new StringValue("a.in"))).toString());
    }
}