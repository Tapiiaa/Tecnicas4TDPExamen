package AlanTurin_ElProblemaDeParar;

public class HaltFacade {
    private HaltChecker haltChecker;

    public HaltFacade() {
        this.haltChecker = HaltChecker.getInstance();
    }

    public boolean checkProgram(String programCode) {
        return haltChecker.willHalt(programCode);
    }
}
