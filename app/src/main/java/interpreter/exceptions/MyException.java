package interpreter.exceptions;

public class MyException extends Exception{
    public MyException(String message){
        super(message);
        //super(message) passes that message to the parent Exception class.
    }
}
