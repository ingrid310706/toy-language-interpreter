package interpreter.model.type;

import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class IntTypeTest {
     @Test
     void defaultValueZero(){
         IntType type = new IntType();

         assertEquals(new IntValue(0),type.defaultValue());
     }
}
