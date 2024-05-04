package AlanTurin_ElProblemaDeParar;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

public class MainFrame extends JFrame {
    private JButton countUpButton;
    private JButton countDownButton;
    private JButton reverserButton;
    private JTextArea outputArea;
    private JPanel buttonPanel;

    public MainFrame() {
        super("Demo Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(380, 200));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        countUpButton = new JButton("Contar hacia arriba");
        countDownButton = new JButton("Contar hacia abajo");
        reverserButton = new JButton("Reverser");

        countUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread countUpThread = new Thread(MainFrame.this::countUp);
                countUpThread.start();
            }
        });

        countDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread countDownThread = new Thread(MainFrame.this::countDown);
                countDownThread.start();
            }
        });

        reverserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupReverser();
            }
        });

        buttonPanel.add(countUpButton);
        buttonPanel.add(countDownButton);
        buttonPanel.add(reverserButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void countUp() {
        AtomicInteger count = new AtomicInteger();
        while (true) {
            SwingUtilities.invokeLater(() -> outputArea.append(count.getAndIncrement() + "\n"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void countDown() {
        AtomicInteger count = new AtomicInteger(10);
        while (count.get() >= 0) {
            SwingUtilities.invokeLater(() -> outputArea.append(count.getAndDecrement() + "\n"));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Ha finalizado el programa"));
    }

    private void setupReverser() {
        JFrame reverserFrame = new JFrame("Reverser Options");
        reverserFrame.setSize(300, 200);
        reverserFrame.setLayout(new FlowLayout());
        reverserFrame.setLocationRelativeTo(this);

        JButton upButton = new JButton("Contar hacia arriba");
        JButton downButton = new JButton("Contar hacia abajo");

        upButton.addActionListener(e -> {
            if (!HaltChecker.getInstance().willHalt("while (true) { }")) {
                while (true) {
                    SwingUtilities.invokeLater(() -> outputArea.append("Reverser Loop\n"));
                }
            } else {
                outputArea.append("Reverser Ends\n");
                reverserFrame.dispose();
            }
        });

        downButton.addActionListener(e -> {
            if (HaltChecker.getInstance().willHalt("int i = 10; while (i > 0) { i--; }")) {
                while (true) {
                    SwingUtilities.invokeLater(() -> outputArea.append("Reverser Loop\n"));
                }
            } else {
                outputArea.append("Reverser Ends\n");
                reverserFrame.dispose();
            }
        });

        reverserFrame.add(upButton);
        reverserFrame.add(downButton);
        reverserFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

