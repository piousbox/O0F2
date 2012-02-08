/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.webcam;

import com.piousbox.ImageUtils;
import com.piousbox.graphics.O0Filter;
import com.piousbox.graphics.O0Filter;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ae1
 */
public class WebcamApp extends Frame implements Runnable {

    Thread animation;
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;

    public WebcamApp() {
        setTitle("ae1");

        setVisible(true);
        setSize(300, 300);

        animation = new Thread(this);
        animation.start();

        File file = new File("/dev/video0");

        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebcamApp.class.getName()).log(Level.SEVERE, null, ex);
        }


        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(
                    WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new WebcamApp();
    }

    public void run() {
        while (true) {
            try {

                int numread = is.read(bytes);
                offset += numread;

                InputStream in = new ByteArrayInputStream(bytes);
                in.reset();
                image = ImageIO.read(in);
                O0Filter clusteredImage = new O0Filter(image, 50, 50, 50);
                image = clusteredImage.getImageOut();

            } catch (IOException ex) {
                Logger.getLogger(WebcamApp.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
        }
    }

    @Override
    public void update(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }
}
