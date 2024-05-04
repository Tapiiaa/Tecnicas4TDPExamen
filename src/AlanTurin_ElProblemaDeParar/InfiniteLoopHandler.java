package AlanTurin_ElProblemaDeParar;

public class InfiniteLoopHandler implements Handler {
    private Handler next;

    @Override
    public void setNext(Handler next) {
        this.next = next;
    }

    @Override
    public boolean handle(String code) {
        if (code.contains("while (true)") || code.contains("for (;;)") ) {
            return false; // Indica que no se detendrá
        }
        if (next != null) {
            return next.handle(code);
        }
        return true; // Si no se detecta un bucle infinito, se asume que se detendrá
    }
}
