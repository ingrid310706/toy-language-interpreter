package interpreter.view;

import interpreter.exceptions.MyException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String,Command> commands =new LinkedHashMap<>();
    public void addCommand(Command command){
        commands.put(command.getKey(), command);
    }

    private void printMenu(){
        System.out.println("\nToy Language Interpreter");

        for (Command command : commands.values()) {
            System.out.println(
                    command.getKey() + ". " + command.getDescription()
            );
        }
    }
    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            System.out.print("Choose an option: ");
            String key = scanner.nextLine();

            Command command = commands.get(key);

            if (command == null) {
                System.out.println("Invalid option!");
                continue;
            }

            try {
                command.execute();
            } catch (MyException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
