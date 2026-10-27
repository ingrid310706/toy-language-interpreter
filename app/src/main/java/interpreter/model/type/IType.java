
package interpreter.model.type;

import interpreter.model.value.IValue;

public interface IType {
    IValue defaultValue();
    //every type must provide a default value

    //when initializing an int x , with the default 0
    //similarly for bool a , intialized with false


}