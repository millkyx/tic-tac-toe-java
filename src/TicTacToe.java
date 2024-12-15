import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TicTacToe extends JFrame {
    private JButton[][] buttons;
    private char[][] board;
    private char currentPlayer;
    private boolean gameEnded;
    private boolean vsComputer;
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("Крестики-нолики");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Инициализация полей
        buttons = new JButton[3][3];
        board = new char[3][3];
        currentPlayer = 'X';
        gameEnded = false;

        // Создание панелей
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        JPanel controlPanel = new JPanel();

        // Создание кнопок игрового поля
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].setFocusPainted(false);
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(e -> handleButtonClick(row, col));
                gamePanel.add(buttons[i][j]);
                board[i][j] = ' ';
            }
        }

        // Создание элементов управления
        JButton newGameButton = new JButton("Новая игра");
        newGameButton.addActionListener(e -> showGameModeDialog());

        statusLabel = new JLabel("Ход игрока X");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Добавление компонентов на панели
        controlPanel.add(newGameButton);
        controlPanel.add(statusLabel);

        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Настройка окна
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);

        showGameModeDialog();
    }

    private void showGameModeDialog() {
        String[] options = {"Игрок против Игрока", "Игрок против Компьютера"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "Выберите режим игры:",
            "Режим игры",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        vsComputer = (choice == 1);
        resetGame();
    }

    private void handleButtonClick(int row, int col) {
        if (gameEnded || board[row][col] != ' ') {
            return;
        }

        makeMove(row, col);

        if (vsComputer && !gameEnded && currentPlayer == 'O') {
            computerMove();
        }
    }

    private void makeMove(int row, int col) {
        board[row][col] = currentPlayer;
        buttons[row][col].setText(String.valueOf(currentPlayer));
        buttons[row][col].setForeground(currentPlayer == 'X' ? Color.BLUE : Color.RED);

        if (checkWin()) {
            statusLabel.setText("Игрок " + currentPlayer + " победил!");
            gameEnded = true;
        } else if (isBoardFull()) {
            statusLabel.setText("Ничья!");
            gameEnded = true;
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Ход игрока " + currentPlayer);
        }
    }

    private void computerMove() {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != ' ');

        makeMove(row, col);
    }

    private boolean checkWin() {
        // Проверка строк и столбцов
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)) {
                return true;
            }
        }

        // Проверка диагоналей
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
               (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
        }
        currentPlayer = 'X';
        gameEnded = false;
        statusLabel.setText("Ход игрока X");
    }
} 