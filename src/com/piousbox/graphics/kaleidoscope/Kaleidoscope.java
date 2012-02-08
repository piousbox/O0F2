/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.kaleidoscope;

import com.piousbox.graphics.webcam.WebcamTriangle;
import com.piousbox.ImageUtils;
import com.piousbox.graphics.Config;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 *
 * @author ae1
 */
public class Kaleidoscope extends Frame implements Runnable {

    Thread animation;
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
    BufferedImage flipImage = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;
    Config c = null;
    WebcamTriangle tri = null;
    int w, h;
    double pi = Math.PI;

    public Kaleidoscope() {
        setTitle("Kaleidoscope");

        c = new Config();

        tri = new WebcamTriangle();

        w = c.getCanvasWidth();
        h = c.getCanvasHeight();

        setVisible(true);
        setSize(w, h);

        animation = new Thread(this);
        animation.start();

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(
                    WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Kaleidoscope();
    }

    public void run() {
        while (true) {

            image = tri.getTriangle();

            repaint();
        }
    }

    @Override
    public void update(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(w, h);

        for (int i = 0; i < 6; i++) {
            g2d.drawImage(image, 0, 0, this);
            g2d.rotate(pi / 3);
        }

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        flipImage = op.filter(image, null);

        for (int i = 0; i < 6; i++) {
            
            g2d.drawImage(flipImage, -flipImage.getWidth(), 0, flipImage.getWidth(), flipImage.getHeight(), this);
            g2d.rotate(pi / 3);
        }
    }
}
