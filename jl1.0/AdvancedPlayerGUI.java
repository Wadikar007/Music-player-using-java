import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdvancedPlayerGUI {
    JFrame frame;
    JButton chooseButton, playButton, pauseButton;

    public AdvancedPlayerGUI() {
        // Create and configure the JFrame
        frame = new JFrame("JFrame");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(194, 57, 68)); // Similar magenta shade
        frame.setLayout(new GridBagLayout()); // Using GridBagLayout for custom alignment

        // GridBagConstraints for layout control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between buttons
        gbc.gridx = 0; // All components in one column
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create buttons with icons and advanced styling
        chooseButton = createStyledButton("Choose Your Song", "🎵");
        playButton = createStyledButton("Play", "▶");
        pauseButton = createStyledButton("Pause", "⏸");

        // Add buttons to the frame with appropriate GridBag constraints
        gbc.gridy = 0;
        frame.add(chooseButton, gbc);

        gbc.gridy = 1;
        frame.add(playButton, gbc);

        gbc.gridy = 2;
        frame.add(pauseButton, gbc);

        // Button Action Listeners
        chooseButton.addActionListener(e -> chooseSong());
        playButton.addActionListener(e -> playSong());
        pauseButton.addActionListener(e -> pauseSong());

        // Set the frame to be visible
        frame.setVisible(true);
    }

    // Helper method to create a styled button
    private JButton createStyledButton(String text, String emoji) {
        JButton button = new JButton(emoji + " " + text);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Custom padding
        return button;
    }

    // ActionListener Methods (currently with placeholders)
    private void chooseSong() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Selected file: " + filePath);
        }
    }

    private void playSong() {
        System.out.println("Playing song...");
    }

    private void pauseSong() {
        System.out.println("Pausing song...");
    }

    // Main method to launch the application
    public static void main(String[] args) {
        new AdvancedPlayerGUI();
    }
}

    

