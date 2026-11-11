package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @TempDir
    Path dir;

    private PrgState createState() {
        return PrgState.of(new NopStmt());
    }

    private String logPath() {
        return dir.resolve("log.txt").toString();
    }

    @Test
    void getCrtPrgReturnsInitialProgram() {
        PrgState state = createState();

        IRepository repo = new Repository(state, logPath());

        assertSame(state, repo.getCrtPrg());
    }

    @Test
    void getCrtPrgReturnsSameStateAfterChanges() throws MyException {
        PrgState state = createState();

        IRepository repo = new Repository(state, logPath());

        state.getSymTable().put("x", new IntValue(5));
        state.getOut().add(new IntValue(10));

        PrgState retrieved = repo.getCrtPrg();

        assertSame(state, retrieved);
        assertEquals(new IntValue(5),
                retrieved.getSymTable().lookup("x"));
        assertEquals(new IntValue(10),
                retrieved.getOut().get(0));
    }

    @Test
    void logWritesTheCurrentStateToTheFile() throws Exception {
        PrgState state = createState();
        IRepository repo = new Repository(state, logPath());

        repo.logPrgStateExec();

        assertEquals(state.toLogString() + System.lineSeparator(),
                Files.readString(dir.resolve("log.txt")));
    }
    @Test
    void logAppendsInsteadOfOverwriting() throws Exception {
        PrgState state = createState();
        IRepository repo = new Repository(state, logPath());

        repo.logPrgStateExec();
        state.getOut().add(new IntValue(10));
        repo.logPrgStateExec();

        String log = Files.readString(dir.resolve("log.txt"));
        assertEquals(2, log.split("ExeStack:", -1).length - 1);
        assertTrue(log.contains("Out:\n10\n"));
    }

    @Test
    void unwritableLogFileThrowsRepositoryException() {
        IRepository repo = new Repository(createState(),
                dir.resolve("missing-folder").resolve("log.txt").toString());

        assertThrows(RepositoryException.class, repo::logPrgStateExec);
    }
}