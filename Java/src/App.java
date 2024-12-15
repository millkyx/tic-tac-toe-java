import javax.swing.*;
import java.awt.*;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createMenu();
        });
    }

    private static void createMenu() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1));

        JLabel title = new JLabel("<html><span style='color:blue;'>Tic</span> <span style='color:red;'>Tac</span> <span style='color:black;'>Toe</span></html>", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        menuPanel.add(title);

        // Установим общий стиль для кнопок
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        Color buttonColor = Color.LIGHT_GRAY;

        JButton playButton = new JButton("Играть");
        playButton.setFont(buttonFont);
        playButton.setBackground(buttonColor);
        playButton.setForeground(Color.BLACK);
        playButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        playButton.addActionListener(e -> {
            // Показать диалоговое окно выбора режима игры
            String[] options = {"Игрок против Компьютера", "Игрок против Игрока"};
            int choice = JOptionPane.showOptionDialog(frame, "Выберите режим игры:", "Режим игры",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                // Запуск игры против компьютера
                new TicTacToeGame("Компьютер").setVisible(true);
            } else if (choice == 1) {
                // Запуск игры против игрока
                new TicTacToeGame("Игрок").setVisible(true);
            }
        });
        menuPanel.add(playButton);

        JButton aboutButton = new JButton("Об игре");
        aboutButton.setFont(buttonFont);
        aboutButton.setBackground(buttonColor);
        aboutButton.setForeground(Color.BLACK);
        aboutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Tic Tac Toe - классическая игра для двух игроков.\n" +
                    "Цель игры - собрать три своих символа в ряд.\n" +
                    "Управление: нажмите на ячейку, чтобы сделать ход.\n" +
                    "Создатель: Millkyx\n" +
                    "Удачи!", "Об игре", JOptionPane.INFORMATION_MESSAGE);
        });
        menuPanel.add(aboutButton);

        JButton exitButton = new JButton("Выход");
        exitButton.setFont(buttonFont);
        exitButton.setBackground(buttonColor);
        exitButton.setForeground(Color.BLACK);
        exitButton.addActionListener(e -> System.exit(0));
        menuPanel.add(exitButton);

        JLabel footer = new JLabel("© 2024 Tic Tac Toe", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 12));
        footer.setForeground(Color.BLACK);
        menuPanel.add(footer);

        frame.add(menuPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
