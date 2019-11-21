package Repository;
import Model.PrgState;

public interface RepoInterface {
    PrgState getCurrentState();
    PrgState popCurrentState();
    void clearLogFile();
    void logPrgStateExec();
}
