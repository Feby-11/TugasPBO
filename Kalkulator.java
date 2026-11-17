import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Kalkulator extends JFrame implements ActionListener {
    private JTextField layar;
    private JButton[] tombol;
    private JButton btnClear;

    private final String[] label = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", ".", "=", "+"
    };

    private double angka1 = 0;
    private String operator = "";
    private boolean inputBaru = true;

    public Kalkulator() {
        super("Kalkulator Sederhana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(5, 5));

        layar = new JTextField("0");
        layar.setEditable(false);
        layar.setHorizontalAlignment(SwingConstants.RIGHT);
        layar.setFont(new Font("Arial", Font.BOLD, 30));
        layar.setPreferredSize(new Dimension(300, 70));

        JPanel panelTombol = new JPanel(new GridLayout(4, 4, 5, 5));
        panelTombol.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));

        tombol = new JButton[label.length];
        for (int i = 0; i < label.length; i++) {
            tombol[i] = buatTombol(label[i]);
            panelTombol.add(tombol[i]);
        }

        btnClear = buatTombol("C");
        btnClear.setBackground(new Color(231, 76, 60));
        btnClear.setForeground(Color.WHITE);

        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        panelBawah.add(btnClear, BorderLayout.CENTER);

        add(layar, BorderLayout.NORTH);
        add(panelTombol, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);

        layar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ("0123456789.+-*/".indexOf(c) >= 0) {
                    proses(String.valueOf(c));
                } else if (c == '=' || c == '\n') {
                    proses("=");
                } else if (c == 'c' || c == 'C') {
                    proses("C");
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private JButton buatTombol(String teks) {
        JButton b = new JButton(teks);
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setFocusable(false);
        b.addActionListener(this);

        b.setOpaque(true);
        b.setBorderPainted(false);
        if ("+-*/".contains(teks)) {
            b.setBackground(new Color(243, 156, 18));
            b.setForeground(Color.WHITE);
        } else if (teks.equals("=")) {
            b.setBackground(new Color(39, 174, 96));
            b.setForeground(Color.WHITE);
        } else {
            b.setBackground(new Color(236, 240, 241));
        }
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        proses(e.getActionCommand());
    }

    private void proses(String perintah) {
        if (layar.getText().equals("Error")) {
            reset();
        }

        if (perintah.equals("C")) {
            reset();
        } else if (perintah.equals("=")) {
            hitung();
            operator = "";
            inputBaru = true;
        } else if ("+-*/".contains(perintah)) {
            if (!operator.isEmpty() && !inputBaru) {
                hitung();
            }
            angka1 = Double.parseDouble(layar.getText());
            operator = perintah;
            inputBaru = true;
        } else {
            if (inputBaru) {
                layar.setText(perintah.equals(".") ? "0." : perintah);
                inputBaru = false;
            } else if (perintah.equals(".")) {
                if (!layar.getText().contains(".")) {
                    layar.setText(layar.getText() + ".");
                }
            } else if (layar.getText().equals("0")) {
                layar.setText(perintah);
            } else {
                layar.setText(layar.getText() + perintah);
            }
        }
    }

    private void hitung() {
        if (operator.isEmpty()) {
            return;
        }
        double angka2 = Double.parseDouble(layar.getText());
        double hasil = 0;

        switch (operator) {
            case "+": hasil = angka1 + angka2; break;
            case "-": hasil = angka1 - angka2; break;
            case "*": hasil = angka1 * angka2; break;
            case "/":
                if (angka2 == 0) {
                    layar.setText("Error");
                    operator = "";
                    inputBaru = true;
                    return;
                }
                hasil = angka1 / angka2;
                break;
        }

        if (hasil == (long) hasil) {
            layar.setText(String.valueOf((long) hasil));
        } else {
            layar.setText(String.valueOf(hasil));
        }
        angka1 = hasil;
    }

    private void reset() {
        layar.setText("0");
        angka1 = 0;
        operator = "";
        inputBaru = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Kalkulator().setVisible(true));
    }
}