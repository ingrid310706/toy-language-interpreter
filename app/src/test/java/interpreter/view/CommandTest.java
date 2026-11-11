package interpreter.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void aCommandKnowsItsKeyAndDescription() {
        Command c = new ExitCommand("0", "exit");

        assertEquals("0", c.getKey());
        assertEquals("exit", c.getDescription());
    }
}