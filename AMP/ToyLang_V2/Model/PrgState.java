package Model;

import Model.Statements.StmtInterface;
import Model.Values.StringValue;
import Model.Values.ValueInterface;

import java.io.BufferedReader;
import java.util.Dictionary;
import java.util.List;
import java.util.Stack;

public class PrgState {
    private Dictionary<String, ValueInterface> symTable;
    private Stack<StmtInterface> exeStack;
    private List<ValueInterface> out;
    private Dictionary<String, BufferedReader> fileTable;

    public PrgState(Dictionary<String, ValueInterface> symTable, Stack<StmtInterface> exeStack, List<ValueInterface> out, Dictionary<String, BufferedReader> fileTable){
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.out = out;
        this.fileTable = fileTable;
    }

    public void pushProgram(StmtInterface prg){
        this.exeStack.push(prg);
    }

    public Dictionary<String, ValueInterface> getSymTable() {
        return symTable;
    }

    public Stack<StmtInterface> getExeStack() {
        return exeStack;
    }

    public List<ValueInterface> getOut() {
        return out;
    }

    public void setSymTable(Dictionary<String, ValueInterface> symTable) {
        this.symTable = symTable;
    }

    public void setExeStack(Stack<StmtInterface> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOut(List<ValueInterface> out) {
        this.out = out;
    }

    public Dictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public void setFileTable(Dictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void pushFile(String fileName, BufferedReader buff){
        this.fileTable.put(fileName, buff);
    }

    @Override
    public String toString() {
        StringBuilder stackString = new StringBuilder();
        for(StmtInterface iStatement : exeStack){
            stackString.append(iStatement).append(';');
        }
        return  "\nStack: " + stackString + " \nSymTable:" + symTable + " \nOutput:" + out + " \nFileTable: " + fileTable;
    }
}
