package interpreter.repository;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    //get current program

    void logPrgStateExec() throws MyException; // new in A3
}