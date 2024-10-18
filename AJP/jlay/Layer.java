package jlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileFilter;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Layer {
    Player player;
    BufferedInputStream bis;
    FileInputStream fis;
    File file;
    Thread playerThread;
    boolean isPaused = false;
    long pausePosition = 0;

    Layer() {
        JFrame main = new JFrame("Audio Player");
        main.setVisible(true);
        main.setSize(400, 440);
        main.setLayout(new FlowLayout());
        main.getContentPane().setBackground(Color.RED);

        JButton chooseFolder = new JButton("Choose Your Song Folder");
        chooseFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openFolderDialog();
            }
        });
        main.add(chooseFolder);

        JButton play = new JButton("Play");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                playSong();
            }
        });
        main.add(play);

        JButton pause = new JButton("Pause");
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                togglePause();
            }
        });
        main.add(pause);

        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void openFolderDialog() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = folderChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            // Show song selection dialog
            selectSongFromFolder(selectedFolder);
        }
    }

    void selectSongFromFolder(File folder) {
        File[] songs = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                // Accept only mp3 files
                return file.isFile() && file.getName().endsWith(".mp3");
            }
        });

        if (songs != null && songs.length > 0) {
            String[] songNames = new String[songs.length];
            for (int i = 0; i < songs.length; i++) {
                songNames[i] = songs[i].getName();
            }

            // Show song selection dialog
            String selectedSong = (String) JOptionPane.showInputDialog(null, "Select a song", "Song Selection",
                    JOptionPane.PLAIN_MESSAGE, null, songNames, songNames[0]);

            if (selectedSong != null) {
                // Load the selected song
                for (File song : songs) {
                    if (song.getName().equals(selectedSong)) {
                        file = song;
                        try {
                            fis = new FileInputStream(file);
                            bis = new BufferedInputStream(fis);
                            player = new Player(bis);
                        } catch (IOException | JavaLayerException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No MP3 files found in the selected folder.");
        }
    }

    void playSong() {
        if (player != null && !isPaused) {
            // Start a new thread to play the song
            playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            });
            playerThread.start();
        } else if (player != null && isPaused) {
            // Resume playing if it's paused
            isPaused = false;
            // Reopen the stream at the pause position
            try {
                fis = new FileInputStream(file);
                fis.skip(pausePosition);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
                playSong();
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No song selected!");
        }
    }

    void togglePause() {
    if (player != null) {
        if (!isPaused) {
            // Pausing: stop the player and record the position
            isPaused = true;
            try {
                pausePosition = fis.getChannel().position(); // Get the current position
                player.close();  // Close the player, effectively pausing the playback
                JOptionPane.showMessageDialog(null, "Playback paused. Press Play to resume.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Resuming playback
            isPaused = false;
            playSong();  // Resume the playback
        }
    }
}


    public static void main(String[] args) {
        new Layer();
    }
}
