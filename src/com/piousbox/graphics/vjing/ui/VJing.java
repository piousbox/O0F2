/**
 * Works 20100804 1240pm
 *
 */package com.piousbox.graphics.vjing.ui;

import com.piousbox.graphics.*;
import com.piousbox.graphics.vjing.VJControlFrame;
import com.piousbox.graphics.vjing.VJOutputWindow;
import java.awt.BorderLayout;
import java.awt.Container;
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
public class VJing implements ActionListener, ItemListener, Runnable {

    Thread animation;
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;
    Config c;
    JPanel contentPanel;
    JMenuBar menuBar;
    JMenu mainMenu, filtersMenu;
    JMenuItem exitMenuItem;
    JTextArea output;
    JScrollPane scrollPane;
    JFrame controlFrame;
    VJOutputWindow outputWindow;

    public VJing() {
        c = new Config();

        outputWindow = new VJOutputWindow(c);
        controlFrame = new VJControlFrame(c, outputWindow.getPanel());

        File file = new File("/dev/video0");
        
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        animation = new Thread(this);
        animation.start();
    }

    public Container createOutputPane() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        contentPane.add(scrollPane, BorderLayout.SOUTH);

        return contentPane;
    }

    public JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        mainMenu = new JMenu("Main Menu");
        filtersMenu = new JMenu("Filters");
        exitMenuItem.addActionListener(this);
        mainMenu.add(exitMenuItem);
        menuBar.add(mainMenu);
        return menuBar;
    }

    public void run() {
        while (true) {
            try {

                int numread = is.read(bytes);
                offset += numread;

                InputStream in = new ByteArrayInputStream(bytes);
                in.reset();
                image = ImageIO.read(in);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            c.repaint();
        }
    }

    public static void main(String[] args) {
        new VJing();
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae);
    }

    public void itemStateChanged(ItemEvent ie) {
        System.out.println(ie);
    }
}
