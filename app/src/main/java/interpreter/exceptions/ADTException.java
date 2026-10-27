package interpreter.exceptions;

public class ADTException extends MyException{
    public ADTException(String mess){
        super(mess);
    }
    //used for invalid operations
    // like empty stack
    //non existent key
    //out of bounds

}
