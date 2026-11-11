
package interpreter.view;

import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;

public class Examples {

    public static IStmt example1() {
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v",
                                new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
    }

    public static IStmt example2() {
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithExp(
                                                ArithOp.ADD,
                                                new ValueExp(new IntValue(2)),
                                                new ArithExp(
                                                        ArithOp.MUL,
                                                        new ValueExp(new IntValue(3)),
                                                        new ValueExp(new IntValue(5))
                                                )
                                        )
                                ),
                                new CompStmt(
                                        new AssignStmt("b",
                                                new ArithExp(
                                                        ArithOp.ADD,
                                                        new VarExp("a"),
                                                        new ValueExp(new IntValue(1))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
    }

    public static IStmt example3() {
        return new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v",
                                                        new ValueExp(new IntValue(2))),
                                                new AssignStmt("v",
                                                        new ValueExp(new IntValue(3)))
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );
    }

    public static final IStmt EX1 = example1();
    public static final IStmt EX2 = example2();
    public static final IStmt EX3 = example3();

    public static IStmt fileExample(String fileName) {
        return new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue(fileName))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));
    }

    public static final IStmt EX4 = fileExample("test.in");

    // int a; int b; a = 5; b = 7; (if a < b then print(a) else print(b))
    public static final IStmt EX5 = new CompStmt(
            new VarDeclStmt("a", new IntType()),
            new CompStmt(
                    new VarDeclStmt("b", new IntType()),
                    new CompStmt(
                            new AssignStmt("a", new ValueExp(new IntValue(5))),
                            new CompStmt(
                                    new AssignStmt("b", new ValueExp(new IntValue(7))),
                                    new IfStmt(
                                            new RelationalExp(RelOp.LT,
                                                    new VarExp("a"), new VarExp("b")),
                                            new PrintStmt(new VarExp("a")),
                                            new PrintStmt(new VarExp("b"))
                                    )
                            )
                    )
            )
    );
}
