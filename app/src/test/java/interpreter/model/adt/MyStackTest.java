package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import java.util.List;



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


    @Test
    void toListReturnsTopFirst() {
        MyStack<Integer> stack = new MyStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(List.of(30, 20, 10), stack.toList());
    }

    @Test
    void toListReturnsEmptyListForEmptyStack() {
        MyStack<Integer> stack = new MyStack<>();

        assertEquals(List.of(), stack.toList());
    }

    @Test
    void toListDoesNotModifyOriginalStack() throws ADTException {
        MyStack<Integer> stack = new MyStack<>();

        stack.push(10);
        stack.push(20);

        List<Integer> result = stack.toList();

        assertThrows(UnsupportedOperationException.class,
                () -> result.add(30));

        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }


}
