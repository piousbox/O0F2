package com.piousbox.graphics.vjing;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.piousbox.ImageUtils;
import com.piousbox.graphics.Config;
import com.piousbox.graphics.kaleidoscope.Kaleidoscope;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class OutputPanel extends JPanel implements Runnable {

    Config c;
    public static final int FLASH_WHITE_3 = 1;
    private int which = 0;
    boolean flag1 = false;
    BufferedImage webcamImage;
    public Filter1 filter1 = Filter1.PLAIN;
    Graphics2D g2d;
    Thread animation;

    /*
     * webcam
     */
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;

    public OutputPanel(Config c) {
        this.c = c;
        setBackground(Color.black);

        animation = new Thread(this);
        animation.start();
    }
    public boolean goldfishT = true;

    private void goldfish(Graphics2D g2d) {
        String tempAddr = (c.rnd.nextBoolean() == true) ? "goldfish.png" : "goldfish_flip.png";
        for (int i = 0; i < c.nGoldfish; i++) {
            int x = c.rnd.nextInt(c.w) - 100;
            int y = c.rnd.nextInt(c.h) - 78;

            g2d.drawImage(ImageUtils.loadBufferedImage(c.imgLib + tempAddr), x, y, null);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        goldfishT = false;
    }

    public void flash(Graphics2D g2d) {
        for (int i = 0; i < 3; i++) {
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, c.w, c.h);
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, c.w, c.h);
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        repaint();
    }

    public void growForest() {
//        AvatarForest forest = new AvatarForest(outer.c, this.getGraphics());
    }

    public void rerepaint() {
        Graphics g = getGraphics();
        if (g != null) {
            paintComponent(g);
        } else {
            repaint();
        }
    }
    KaleidoscopeRunnable kaleidoscope = null;

    public void webcamKaleidoscope() {
//        if (kaleidoscope == null) {
//            kaleidoscope = new KaleidoscopeRunnable(outer.c, this);
//        } else {
//            kaleidoscope.start();
//        }
    }

    public void resetImage() {
//        System.out.println("Reset Image");
//        BufferedImage tempImage = new BufferedImage(outer.w, outer.h, BufferedImage.TYPE_INT_ARGB);
//        g2d = tempImage.createGraphics();
//        if (webcamAscii != null) {
//            webcamAscii.stop();
//
//        }
//        if (kaleidoscope != null) {
//            kaleidoscope.stop();
//        }
    }

    public void run() {
//        rerepaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.black);
        repaint();
//        g.drawString("hello", 50, 50);
    }

    @Override
    public void update(Graphics g) {
        c.log("updatE)");
    }

    void printTest() {
        goldfish((Graphics2D) this.getGraphics());
        repaint();
    }
}
