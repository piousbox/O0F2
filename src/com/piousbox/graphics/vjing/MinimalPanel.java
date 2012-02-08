/**
 * Works 20100804 1240pm
 *
 */package com.piousbox.graphics.vjing;

import com.piousbox.graphics.*;
import com.piousbox.graphics.vjing.OutputPanel;
import com.piousbox.graphics.vjing.VJControlFrame;
import com.piousbox.graphics.vjing.VJOutputWindow;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author ae1
 */
public class MinimalPanel extends JFrame implements Runnable {

    Thread animation;
    Config c = new Config();
    OutputPanel panel;

    public MinimalPanel() {
        setSize(c.w, c.h);
        setVisible(true);
        
        panel = new OutputPanel(c);
        this.getContentPane().add(panel);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        animation = new Thread(this);
        animation.start();

//        outputWindow = new VJOutputWindow(c);
//        controlFrame = new VJControlFrame(c, outputWindow.getPanel());
//
//        File file = new File("/dev/video0");
//
//        try {
//            is = new FileInputStream(file);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//
//        animation = new Thread(this);
//        animation.start();
    }
//
//    public Container createOutputPane() {
//        JPanel contentPane = new JPanel(new BorderLayout());
//        contentPane.setOpaque(true);
//
//        output = new JTextArea(5, 30);
//        output.setEditable(false);
//        scrollPane = new JScrollPane(output);
//
//        contentPane.add(scrollPane, BorderLayout.SOUTH);
//
//        return contentPane;
//    }
//
//    public JMenuBar createMenuBar() {
//        menuBar = new JMenuBar();
//        mainMenu = new JMenu("Main Menu");
//        filtersMenu = new JMenu("Filters");
//        exitMenuItem.addActionListener(this);
//        mainMenu.add(exitMenuItem);
//        menuBar.add(mainMenu);
//        return menuBar;
//    }
//
//    public void run() {
//        while (true) {
//            try {
//
//                int numread = is.read(bytes);
//                offset += numread;
//
//                InputStream in = new ByteArrayInputStream(bytes);
//                in.reset();
//                image = ImageIO.read(in);
//
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
////            c.repaint();
//        }
//    }

    public static void main(String[] args) {
        new MinimalPanel();
    }

    public void run() {
        panel.repaint();
    }

    @Override
    public void update(Graphics g) {

        repaint();
    }
}
