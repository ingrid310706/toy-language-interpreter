package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileStmtTest {

    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;
    private StringValue fileName;

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "15\n50\n");
        fileName = new StringValue(f.toString());
        file = new ValueExp(fileName);
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    private void closeIfOpen() throws Exception {
        if (state.getFileTable().isDefined(fileName))
            state.getFileTable().lookup(fileName).close();
    }

    @Test
    void readsTheNumbersInOrderThenZeroAtTheEnd() throws Exception {
        new OpenRFileStmt(file).execute(state);

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("x"));

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("x"));

        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));

        closeIfOpen();
    }

    @Test
    void emptyFileGivesZero() throws Exception {
        Path empty = dir.resolve("empty.in");
        Files.writeString(empty, "");
        StringValue emptyName = new StringValue(empty.toString());
        ValueExp emptyExp = new ValueExp(emptyName);

        new OpenRFileStmt(emptyExp).execute(state);
        new ReadFileStmt(emptyExp, "x").execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
        state.getFileTable().lookup(emptyName).close();
    }

    @Test
    void undeclaredVariableThrows() throws Exception {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "y").execute(state));
        closeIfOpen();
    }

    @Test
    void variableNotOfTypeIntThrows() throws Exception {
        new VarDeclStmt("b", new BoolType()).execute(state);
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "b").execute(state));
        closeIfOpen();
    }

    @Test
    void nonStringExpressionThrows() {
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(new ValueExp(new IntValue(3)), "x")
                        .execute(state));
    }

    @Test
    void fileNotOpenThrows() {
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void nonIntegerLineThrows() throws Exception {
        Path bad = dir.resolve("bad.in");
        Files.writeString(bad, "abc\n");
        StringValue badName = new StringValue(bad.toString());
        ValueExp badExp = new ValueExp(badName);

        new OpenRFileStmt(badExp).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(badExp, "x").execute(state));
        state.getFileTable().lookup(badName).close();
    }

    @Test
    void toStringIsInfixForm() {
        assertEquals("readFile(\"a.in\", x)",
                new ReadFileStmt(new ValueExp(new StringValue("a.in")), "x").toString());
    }
}