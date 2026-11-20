package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NewStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTest {

    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
    }

    private ReadHeapExp rH(IExp e) {
        return new ReadHeapExp(e);
    }

    @Test
    void readsTheValueAtTheAddress() throws MyException {
        assertEquals(new IntValue(20),
                rH(new VarExp("v")).eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void readsThroughTwoLevelsOfReferences() throws MyException {
        new VarDeclStmt("a", new RefType(new RefType(new IntType()))).execute(state);
        new NewStmt("a", new VarExp("v")).execute(state);

        assertEquals(new IntValue(20),
                rH(rH(new VarExp("a"))).eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void anExpressionThatIsNotAReferenceThrows() {
        assertThrows(ExpressionException.class,
                () -> rH(new ValueExp(new IntValue(3)))
                        .eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void anAddressThatIsNotInTheHeapThrows() {
        var exp = rH(new ValueExp(new RefValue(7, new IntType())));

        ExpressionException e = assertThrows(ExpressionException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap()));
        assertEquals("rH: address 7 is not in the heap", e.getMessage());
    }

    @Test
    void theNullAddressThrows() throws MyException {
        new VarDeclStmt("p", new RefType(new IntType())).execute(state);

        assertThrows(ExpressionException.class,
                () -> rH(new VarExp("p")).eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void canBeUsedInsideAnArithmeticExpression() throws MyException {
        var exp = new ArithExp(ArithOp.ADD, rH(new VarExp("v")),
                new ValueExp(new IntValue(5)));

        assertEquals(new IntValue(25), exp.eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void toStringIsInfixForm() {
        assertEquals("rH(v)", rH(new VarExp("v")).toString());
    }
}