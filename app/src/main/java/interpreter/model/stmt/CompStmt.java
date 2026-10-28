
package interpreter.model.stmt;

import interpreter.model.PrgState;



//It combines two statements into one.
// ex : int v;
//      v = 2;
public record CompStmt(IStmt first, IStmt second) implements IStmt {

    @Override
    public PrgState execute(PrgState state) {
        state.getExeStack().push(second);
        state.getExeStack().push(first);
        return state;
        //The method changes the existing program state's execution stack and returns that same state
        //required by the IStmt
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}
