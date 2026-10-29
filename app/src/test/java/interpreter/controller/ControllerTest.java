
package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.repository.Repository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private PrgState createState(IStmt program) {
        return new PrgState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                program
        );
    }

    @Test
    void oneStepExecutesTopStatement() throws MyException {
        PrgState state = createState(
                new VarDeclStmt("v", new IntType())
        );

        Controller controller = new Controller(
                new Repository(state), false
        );

        controller.oneStep(state);

        assertEquals(new IntValue(0),
                state.getSymTable().lookup("v"));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void oneStepOnEmptyStackThrows() throws MyException {
        PrgState state = createState(new NopStmt());
        Controller controller = new Controller(
                new Repository(state), false
        );

        controller.oneStep(state);

        assertThrows(StatementException.class,
                () -> controller.oneStep(state));
    }

    @Test
    void example1Produces2() throws MyException {
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v",
                                new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        PrgState state = createState(ex1);
        Controller controller = new Controller(
                new Repository(state), false
        );

        controller.allStep();

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void example2Produces18() throws MyException {
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithExp(ArithOp.ADD,
                                                new ValueExp(new IntValue(2)),
                                                new ArithExp(ArithOp.MUL,
                                                        new ValueExp(new IntValue(3)),
                                                        new ValueExp(new IntValue(5))
                                                )
                                        )
                                ),
                                new CompStmt(
                                        new AssignStmt("b",
                                                new ArithExp(ArithOp.ADD,
                                                        new VarExp("a"),
                                                        new ValueExp(new IntValue(1))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );

        PrgState state = createState(ex2);
        Controller controller = new Controller(
                new Repository(state), false
        );

        controller.allStep();

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18), state.getOut().get(0));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void example3Produces2() throws MyException {
        IStmt ex3 = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v",
                                                        new ValueExp(new IntValue(2))),
                                                new AssignStmt("v",
                                                        new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );

        PrgState state = createState(ex3);
        Controller controller = new Controller(
                new Repository(state), false
        );

        controller.allStep();

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
        assertTrue(state.getExeStack().isEmpty());
    }
}
