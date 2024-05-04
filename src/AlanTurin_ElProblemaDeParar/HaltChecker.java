package AlanTurin_ElProblemaDeParar;
public class HaltChecker {
    private static HaltChecker instance;
    private Handler chain;

    private HaltChecker() {
        buildChain();
    }

    private void buildChain() {
        // Inicializa y conecta la cadena de responsabilidad
        this.chain = new InfiniteLoopHandler();
        // Aquí podríamos añadir más manejadores si fuera necesario
    }

    public static HaltChecker getInstance() {
        if (instance == null) {
            instance = new HaltChecker();
        }
        return instance;
    }

    public boolean willHalt(String programCode) {
        return chain.handle(programCode);
    }
}

