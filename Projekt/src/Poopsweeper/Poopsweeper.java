package Poopsweeper;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Poopsweeper extends JFrame {
    private JLabel statusbar;

    public Poopsweeper() {
        initUI();
    }

    private void initUI() {
        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        add(new Board(statusbar));
        setResizable(false);
        pack();
        setTitle("Poopsweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new Poopsweeper();
            ex.setVisible(true);
        });
    }
}