package View.Menus;

import View.Commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(){
        this.commands = new HashMap<>();
    }

    public void addCommand(Command c){
        this.commands.put(c.getKey(), c);
    }

    private void printMenu(){
        for(Command c : this.commands.values()){
            String line = String.format("%4s : %s", c.getKey(), c.getDesc());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Give command key: ");
            String key = scanner.nextLine();
            Command c = commands.get(key);
            if(c == null){
                System.out.println("Unknown command");
            }
            else {
                c.execute();
                commands.remove(key);
            }
        }
    }
}
