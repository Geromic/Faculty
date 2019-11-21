package Controller;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.StmtInterface;
import Repository.RepoInterface;

import java.util.Stack;

public class Controller {
    private RepoInterface repo;

    public Controller(RepoInterface repo) {
        this.repo = repo;
    }

    private PrgState oneStepExecution(PrgState p){
        Stack<StmtInterface> exeStack = p.getExeStack();
        if(exeStack.empty())
            throw new MyException("Stack is empty!");
        StmtInterface toExecute = exeStack.pop();

        return toExecute.execute(p);
    }

    public void executeEverything(){
        try {
            repo.clearLogFile();
            PrgState program = repo.getCurrentState();
            repo.logPrgStateExec();
            System.out.println(program.toString());
            while (!program.getExeStack().empty()) {
                oneStepExecution(program);

                repo.logPrgStateExec();
                System.out.println(program.toString());
            }
            repo.popCurrentState();
        }
        catch(MyException exc){
            System.out.println(exc.getMessage());
        }
    }
}
