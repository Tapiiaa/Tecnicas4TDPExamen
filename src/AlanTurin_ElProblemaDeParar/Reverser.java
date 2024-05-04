package AlanTurin_ElProblemaDeParar;
public class Reverser {
    public static void main(String[] args) {
        HaltFacade haltFacade = new HaltFacade();
        String programCode = "while (true) { System.out.println(\"Running...\"); }";

        boolean willHalt = haltFacade.checkProgram(programCode);

        if (willHalt) {
            while (true) {
                System.out.println("No se detiene");
            }
        } else {
            System.out.println("Se detiene");
        }
    }
}
