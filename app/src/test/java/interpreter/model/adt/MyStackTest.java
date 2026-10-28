package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {
     @Test
     void stackLIFOorder()
         throws ADTException {
         IStack<Integer> stack = new MyStack<>();
         stack.push(10);
         stack.push(20);
         stack.push(30);

         assertEquals(30, stack.pop());
         assertEquals(20, stack.pop());
         assertEquals(10, stack.pop());
     }

     @Test
     void popFromEmptyStack() {
         IStack<Integer> stack = new MyStack<>();
         ADTException ex = assertThrows(ADTException.class,() -> stack.pop());
         assertEquals("pop from an empty stack", ex.getMessage());
     }

     @Test
     void isEmptyCWorksCorrectly() throws ADTException {
         IStack<Integer> stack = new MyStack<>();
         assertTrue(stack.isEmpty());
         stack.push(5);
         assertFalse(stack.isEmpty());
         stack.pop();
         assertTrue(stack.isEmpty());
     }

}
