package View;

import Controller.Controller;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.ValueInterface;
import Repository.Repository;
import Repository.*;
import View.Commands.ExitCommand;
import View.Commands.RunCommand;
import View.Menus.TextMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Stack;

public class Interpreter {
    private static StmtInterface firstExample(){
        //example for int v; v=2;Print(v);

        VariableExp varV = new VariableExp("v");
        IntValue valV = new IntValue(2);
        ValueExp expV = new ValueExp(valV);
        AssignStmt assignStmtV = new AssignStmt("v", expV);

        PrintStmt printV = new PrintStmt(varV);
        CompStmt compStmtV = new CompStmt(assignStmtV, printV);
        IntType typeV = new IntType();
        DeclStmt declStmtV = new DeclStmt(typeV, "v");

        return new CompStmt(declStmtV, compStmtV);
    }

    private static StmtInterface secondExample() {
        //example for int a;int b; a=2+3*5;b=a+1;Print(b)
        IntType intType = new IntType();
        DeclStmt declStmtA = new DeclStmt(intType, "a");
        DeclStmt declStmtB = new DeclStmt(intType, "b");

        IntValue int1 = new IntValue(1);
        IntValue int2 = new IntValue(2);
        IntValue int3 = new IntValue(3);
        IntValue int5 = new IntValue(5);

        ValueExp valueExp1 = new ValueExp(int1);
        ValueExp valueExp2 = new ValueExp(int2);
        ValueExp valueExp3 = new ValueExp(int3);
        ValueExp valueExp5 = new ValueExp(int5);

        ArithmExp prod = new ArithmExp(valueExp3, valueExp5, 3);
        ArithmExp sum = new ArithmExp(prod, valueExp2, 1);

        CompStmt declStmt = new CompStmt(declStmtA, declStmtB);

        AssignStmt assignmentStatementA = new AssignStmt("a", sum);

        ArithmExp sumAB = new ArithmExp(sum, valueExp1, 1);

        AssignStmt assignStmtB = new AssignStmt("b", sumAB);

        CompStmt computeCalculus = new CompStmt(declStmt, new CompStmt(assignmentStatementA, assignStmtB));

        VariableExp variableExpB = new VariableExp("b");
        PrintStmt printStmtB = new PrintStmt(variableExpB);

        return new CompStmt(computeCalculus, printStmtB);
    }

    private static StmtInterface thirdExample() {
        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        BoolType boolType = new BoolType();
        IntType intType = new IntType();
        DeclStmt variableDeclarationStatementA = new DeclStmt(boolType, "a");
        DeclStmt variableDeclarationStatementB = new DeclStmt(intType, "v");

        CompStmt declaration = new CompStmt(variableDeclarationStatementA, variableDeclarationStatementB);

        BoolValue trueValue = new BoolValue(true);
        BoolValue falseValue = new BoolValue(false);
        IntValue intValue2 = new IntValue(2);
        IntValue intValue3 = new IntValue(3);

        ValueExp valueExpressionTrue = new ValueExp(trueValue);
        ValueExp valueExpressionFalse = new ValueExp(falseValue);
        ValueExp valueExpression2 = new ValueExp(intValue2);
        ValueExp valueExpression3 = new ValueExp(intValue3);

        AssignStmt assignmentStatementA = new AssignStmt("a", valueExpressionTrue);
        CompStmt initializing = new CompStmt(declaration, assignmentStatementA);
        AssignStmt ifTrue = new AssignStmt("v", valueExpression2);
        AssignStmt ifFalse = new AssignStmt("v", valueExpression3);

        LogicExp condition = new LogicExp(new VariableExp("a"), valueExpressionTrue, 2);
        IfStmt ifStatement = new IfStmt(condition, ifTrue, ifFalse);
        CompStmt afterIf = new CompStmt(initializing, ifStatement);
        PrintStmt printStatement = new PrintStmt(new VariableExp("v"));

        return new CompStmt(afterIf, printStatement);
    }

    private static StmtInterface fileExample() {
        /*string varf;
        varf="test.in";
        openRFile(varf);
        int varc;
        readFile(varf,varc);print(varc);
        readFile(varf,varc);print(varc)
        closeRFile(varf)*/

        StringType sType = new StringType();
        DeclStmt sDecl = new DeclStmt(sType, "varf");

        StringValue sVal = new StringValue("test.in");
        ValueExp sValExp = new ValueExp(sVal);
        VariableExp sVarExp = new VariableExp("varf");

        AssignStmt sAssign = new AssignStmt("varf", sValExp);

        openRFileStmt oStmt = new openRFileStmt(sVarExp);

        CompStmt initOpen = new CompStmt(new CompStmt(sDecl, sAssign), oStmt);

        IntType iType = new IntType();
        DeclStmt iDecl = new DeclStmt(iType, "varc");

        CompStmt inits = new CompStmt(initOpen, iDecl);

        readFileStmt rd1 = new readFileStmt(sVarExp, "varc");
        PrintStmt prnt1 = new PrintStmt(new VariableExp("varc"));
        CompStmt cStmt1 = new CompStmt(rd1, prnt1);

        readFileStmt rd2 = new readFileStmt(sVarExp, "varc");
        PrintStmt prnt2 = new PrintStmt(new VariableExp("varc"));
        CompStmt cStmt2 = new CompStmt(rd2, prnt2);

        CompStmt readPrint = new CompStmt(cStmt1, cStmt2);

        CompStmt initToPrint = new CompStmt(inits, readPrint);

        return new CompStmt(initToPrint, new closeRFileStmt(sVarExp));
    }

    private static StmtInterface relationalExample(){
        //int a; a=3; int b; b=6; print(a==b)

        IntType intType = new IntType();
        DeclStmt aDecl = new DeclStmt(intType, "a");
        DeclStmt bDecl = new DeclStmt(intType, "b");

        VariableExp aVar = new VariableExp("a");
        VariableExp bVar = new VariableExp("b");

        AssignStmt aAssign = new AssignStmt("a", new ValueExp(new IntValue(3)));
        AssignStmt bAssign = new AssignStmt("b", new ValueExp(new IntValue(6)));

        RelationalExp rel = new RelationalExp(aVar, bVar, 5);

        CompStmt stmtA = new CompStmt(aDecl, aAssign);
        CompStmt stmtB = new CompStmt(bDecl, bAssign);

        CompStmt declAssign = new CompStmt(stmtA,stmtB);

        PrintStmt prnt = new PrintStmt(rel);

        return new CompStmt(declAssign, prnt);
    }

    public static void main(String[] args){
        StmtInterface fileEx = fileExample();

        Stack<StmtInterface> fileStack = new Stack<>();
        Hashtable<String, ValueInterface> symbolTableFile = new Hashtable<String, ValueInterface>();
        ArrayList<ValueInterface> outputFile = new ArrayList<>();
        Hashtable<String, BufferedReader> filetableFile = new Hashtable<String, BufferedReader>();

        PrgState prgFile = new PrgState(symbolTableFile,fileStack,outputFile,filetableFile);
        prgFile.pushProgram(fileEx);

        ArrayList<PrgState> prgArr = new ArrayList<PrgState>();
        prgArr.add(prgFile);

        RepoInterface repo = new Repository(prgArr, "log.txt");
        Controller ctrl = new Controller(repo);


        //relational example

        StmtInterface relEx = relationalExample();

        Stack<StmtInterface> relStack = new Stack<>();
        Hashtable<String, ValueInterface> symbolTable1 = new Hashtable<String, ValueInterface>();
        ArrayList<ValueInterface> output1 = new ArrayList<>();
        Hashtable<String, BufferedReader> filetable1 = new Hashtable<String, BufferedReader>();

        PrgState prg1 = new PrgState(symbolTable1,relStack,output1,filetable1);
        prg1.pushProgram(relEx);

        ArrayList<PrgState> prgArrRel = new ArrayList<PrgState>();
        prgArrRel.add(prg1);

        Scanner input = new Scanner(System.in);
        System.out.print("Give log file name: ");
        String path = input.nextLine();


        RepoInterface repoRel = new Repository(prgArrRel, path);
        Controller ctrlRel = new Controller(repoRel);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1", fileEx.toString(), ctrl));
        menu.addCommand(new RunCommand("2", relEx.toString(), ctrlRel));
        menu.show();
    }
}
