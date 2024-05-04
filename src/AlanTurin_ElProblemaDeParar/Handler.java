package AlanTurin_ElProblemaDeParar;

public interface Handler {
    void setNext(Handler next);
    boolean handle(String code);
}
