package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    // runs the program completely: pop a statement, execute it, until the stack is empty
    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        while (!state.getExeStack().isEmpty())
            state.getExeStack().pop().execute(state);
        return state;
    }

    // v = start; while (v > 0) { print(v); v = v - 1 }
    private IStmt countdown(int start) {
        var v = new VarExp("v");
        return new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(start))),
                        new WhileStmt(
                                new RelationalExp(RelOp.GT, v, new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(v),
                                        new AssignStmt("v", new ArithExp(ArithOp.SUB, v,
                                                new ValueExp(new IntValue(1))))))));
    }

    @Test
    void falseConditionPushesNothing() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();

        new WhileStmt(new ValueExp(new BoolValue(false)), new NopStmt()).execute(state);

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void trueConditionPushesTheLoopAndThenTheBody() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        IStmt body = new NopStmt();
        var loop = new WhileStmt(new ValueExp(new BoolValue(true)), body);

        loop.execute(state);

        assertSame(body, state.getExeStack().pop());      // body runs first
        assertSame(loop, state.getExeStack().pop());      // then the loop is checked again
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void nonBooleanConditionThrows() {
        PrgState state = PrgState.of(new NopStmt());

        assertThrows(StatementException.class,
                () -> new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt())
                        .execute(state));
    }

    @Test
    void theBodyIsNotExecutedWhenTheConditionIsFalseFromTheStart() throws MyException {
        PrgState state = run(countdown(0));

        assertTrue(state.getOut().toList().isEmpty());
    }

    @Test
    void theLoopRunsOnce() throws MyException {
        PrgState state = run(countdown(1));

        assertEquals(java.util.List.of(new IntValue(1)), state.getOut().toList());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void theLoopRunsSeveralTimes() throws MyException {
        PrgState state = run(countdown(3));

        assertEquals(java.util.List.of(new IntValue(3), new IntValue(2), new IntValue(1)),
                state.getOut().toList());
    }

    @Test
    void toStringShowsConditionAndBody() {
        assertEquals("(while (true) no operation)",
                new WhileStmt(new ValueExp(new BoolValue(true)), new NopStmt()).toString());
    }
}