package View.Commands;

import Controller.Controller;
import Model.Exceptions.MyException;

public class RunCommand extends Command {
    private Controller ctrl;
    public RunCommand(String key, String desc, Controller ctrl) {
        super(key, desc);
        this.ctrl = ctrl;
    }

    @Override
    public void execute() {
        try{
            this.ctrl.executeEverything();
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }
    }
}
