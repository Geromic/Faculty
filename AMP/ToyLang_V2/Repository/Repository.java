package Repository;

import Model.Exceptions.MyException;
import Model.PrgState;
import Model.Statements.StmtInterface;
import Model.Values.ValueInterface;

import java.io.*;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

public class Repository implements RepoInterface {
    private List<PrgState> prgState;
    private String logFilePath;

    public Repository(List<PrgState> programs, String logFilePath) {
        this.prgState = programs;
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCurrentState() {
        if(prgState.isEmpty())
            throw new MyException("Empty repo!");
        else{
            return prgState.get(0);
        }
    }

    @Override
    public PrgState popCurrentState() {
        if(prgState.isEmpty())
            throw new MyException("Empty repo!");
        else{
            return prgState.remove(0);
        }
    }

    @Override
    public void clearLogFile() {
        try {
            PrintWriter clr = new PrintWriter(logFilePath);
        } catch (FileNotFoundException e) {
            throw new MyException("Error clearing log file");
        }
    }

    @Override
    public void logPrgStateExec() {
        if(logFilePath == null)
            return;

        try {
            PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            PrgState p = prgState.get(0);
            Stack<StmtInterface> stack = p.getExeStack();
            //write exeStack to file
            logFile.write("ExeStack:\n");
            for(int i = stack.size()-1; i>=0; i--){
                logFile.write(stack.get(i).toString() + "\n");
            }

            //write symTable to file
            logFile.write("SymTable:\n");
            Dictionary<String, ValueInterface> symTable = p.getSymTable();
            Enumeration<String> keys = symTable.keys();
            while(keys.hasMoreElements()){
                String key = keys.nextElement();
                logFile.write(key + " --> " + symTable.get(key).toString() + "\n");
            }

            //write output to file
            logFile.write("Out:\n");
            List<ValueInterface> out = p.getOut();
            for(ValueInterface val : out){
                logFile.write(val.toString() + "\n");
            }

            //write fileTable to file
            logFile.write("FileTable:\n");
            Enumeration<String> fileKeys = p.getFileTable().keys();
            while(fileKeys.hasMoreElements()){
                String key = fileKeys.nextElement();
                logFile.write(key + "\n");
            }

            logFile.write("\n");

            logFile.close();
        } catch (IOException e) {
            throw new MyException("Error writing to log file");
        }
    }
}
