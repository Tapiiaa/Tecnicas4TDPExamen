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
        super("Demo Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear los botones
        countUpButton = new JButton("Contar hacia arriba");
        countDownButton = new JButton("Contar hacia abajo");
        reverserButton = new JButton("Reverser");

        // Crear el área de texto
        textArea = new JTextArea(5, 20);  // Establecer tamaño del área de texto
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Crear el panel para los botones y establecer su layout
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Agregar los botones al panel
        buttonPanel.add(countUpButton);
        buttonPanel.add(countDownButton);
        buttonPanel.add(reverserButton);

        // Agregar el panel de botones al sur del JFrame
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ajustar el tamaño de la ventana al contenido
        pack();
        setMinimumSize(getSize()); // Establecer el tamaño mínimo basado en el tamaño del pack

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
        reverserFrame.setSize(200, 100);
        reverserFrame.setLayout(new FlowLayout());
        reverserFrame.setLocationRelativeTo(this);

        JButton newCountUpButton = new JButton("CountUp");
        JButton newCountDownButton = new JButton("CountDown");

        reverserFrame.add(newCountUpButton);
        reverserFrame.add(newCountDownButton);

        reverserFrame.setVisible(true);
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



