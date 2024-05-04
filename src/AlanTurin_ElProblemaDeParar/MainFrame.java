package AlanTurin_ElProblemaDeParar;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MainFrame extends JFrame {
    private JButton countUpButton;
    private JButton countDownButton;
    private JButton reverserButton;
    private JPanel buttonPanel;
    private JTextArea textArea;

    public MainFrame() {
        super("Alan Turing - El Problema de Parar, Por Pedro Tapia Lobo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear los botones
        countUpButton = new JButton("Contar hacia arriba");
        countDownButton = new JButton("Contar hacia abajo");
        reverserButton = new JButton("Reverser");

        // Crear el área de texto
        textArea = new JTextArea(5, 30);  // Ajustado para mayor visibilidad
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Crear el panel para los botones y establecer su layout
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Agregar los botones al panel
        buttonPanel.add(countUpButton);
        buttonPanel.add(countDownButton);
        buttonPanel.add(reverserButton);

        // Agregar el panel de botones al sur del JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ajustar el tamaño de la ventana al contenido
        setSize(500, 350);
        setMinimumSize(getSize());

        // Definir las acciones de los botones
        countUpButton.addActionListener(e -> startCounting(true));
        countDownButton.addActionListener(e -> startCounting(false));
        reverserButton.addActionListener(e -> openReverserWindow());

        // Hacer visible el JFrame
        setVisible(true);
    }

    private void openReverserWindow() {
        JFrame reverserFrame = new JFrame("Reverser Options");
        reverserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reverserFrame.setLayout(new FlowLayout());
        reverserFrame.setLocationRelativeTo(this);

        JButton newCountUpButton = new JButton("CountUp");
        JButton newCountDownButton = new JButton("CountDown");

        newCountUpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(reverserFrame,
                    "Reverser evaluando: CountUp\n" +
                            "Reverser determina que ContarHaciaArriba no se detendrá, terminando inmediatamente.");
            reverserFrame.dispose();
        });

        newCountDownButton.addActionListener(e -> {
            reverserFrame.dispose();
            enterInfiniteLoop();
        });

        reverserFrame.add(newCountUpButton);
        reverserFrame.add(newCountDownButton);

        reverserFrame.pack();
        reverserFrame.setSize(300, 150);
        reverserFrame.setVisible(true);
    }

    private void enterInfiniteLoop() {
        new Thread(() -> {
            while (true) {
                try {
                    SwingUtilities.invokeLater(() -> textArea.append("Reverser entrando a un bucle infinito puesto que el programa se detendrá\n"));
                    Thread.sleep(1000); // Delay para hacer el bucle manejable y evitar sobrecargar la UI
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void startCounting(boolean countUp) {
        new Thread(() -> {
            AtomicInteger count = new AtomicInteger(countUp ? 0 : 10);
            if (countUp) {
                while (true) {
                    int currentCount = count.incrementAndGet();
                    SwingUtilities.invokeLater(() -> textArea.append(currentCount + "\n"));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                while (count.get() >= 0) {
                    int currentCount = count.getAndDecrement();
                    SwingUtilities.invokeLater(() -> textArea.append(currentCount + "\n"));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Programa ha finalizado"));
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}





