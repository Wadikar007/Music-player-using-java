package jlay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Layer {
    Player player;
    BufferedInputStream bis;
    FileInputStream fis;
    File file;

    Layer() {
        JFrame main = new JFrame();
        main.setVisible(true);
        main.setSize(400, 440);
        main.setLayout(new FlowLayout());
        main.getContentPane().setBackground(Color.MAGENTA);

        JButton choose = new JButton("Choose Your Song");
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                open_dialog();
            }
        });
        main.add(choose);

        JButton play = new JButton("Play");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        });
        main.add(play);

        JButton pause = new JButton("Pause");
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Pausing feature not supported directly
                // You would need to add a more advanced pause and resume functionality
            }
        });
        main.add(pause);

        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void open_dialog() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                file = new File(fc.getSelectedFile().getAbsolutePath());
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                player = new Player(bis);
            } catch (IOException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args) {
       new Layer();
    }
}