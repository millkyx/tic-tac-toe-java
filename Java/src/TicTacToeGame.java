import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToeGame extends JFrame {
    private JButton[][] buttons;
    private char currentPlayer;
    private JLabel statusLabel;
    private String mode;

    public TicTacToeGame(String mode) {
        this.mode = mode;
        setTitle("Tic Tac Toe - " + mode);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        buttons = new JButton[3][3];
        currentPlayer = 'X'; // Начинаем с игрока X

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].setBackground(Color.LIGHT_GRAY);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }

        statusLabel = new JLabel("Текущий игрок: " + currentPlayer);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        // Панель с кнопками управления
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 1 строка, 2 столбца, отступ между кнопками 10

        JButton restartButton = new JButton("Перезапустить");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.addActionListener(e -> restartGame());
        controlPanel.add(restartButton);

        JButton menuButton = new JButton("Вернуться в меню");
        menuButton.setFont(new Font("Arial", Font.BOLD, 16));
        menuButton.addActionListener(e -> {
            dispose(); // Закрываем текущее окно игры
        });
        controlPanel.add(menuButton);

        // Добавляем отступы для панели с кнопками
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(controlPanel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                if (checkForWin()) {
                    showWinner(currentPlayer + " выиграл!", currentPlayer == 'X' ? Color.GREEN : Color.RED);
                } else if (isBoardFull()) {
                    showWinner("Ничья!", Color.ORANGE);
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // Смена игрока
                    statusLabel.setText("Текущий игрок: " + currentPlayer);
                    if (mode.equals("Компьютер") && currentPlayer == 'O') {
                        computerMove();
                    }
                }
            }
        }
    }

    private void computerMove() {
        Random rand = new Random();
        int row, col;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        } while (!buttons[row][col].getText().equals(""));

        buttons[row][col].setText(String.valueOf(currentPlayer));
        if (checkForWin()) {
            showWinner("Компьютер выиграл!", Color.RED);
        } else if (isBoardFull()) {
            showWinner("Ничья!", Color.ORANGE);
        } else {
            currentPlayer = 'X'; // Смена игрока
            statusLabel.setText("Текущий игрок: " + currentPlayer);
        }
    }

    private boolean checkForWin() {
        // Проверка строк, столбцов и диагоналей
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinner(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
        for (JButton[] row : buttons) {
            for (JButton button : row) {
                button.setEnabled(false); // Отключаем кнопки после завершения игры
            }
        }
    }

    private void restartGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        currentPlayer = 'X'; // Сброс текущего игрока
        statusLabel.setText("Текущий игрок: " + currentPlayer);
    }
} 