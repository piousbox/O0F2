/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.piousbox.ImageUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
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
import com.piousbox.ImageUtils;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import com.piousbox.graphics.Config;
import com.piousbox.graphics.webcam.WebcamTriangle;
import com.piousbox.graphics.webcam.WebcamBase;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

/**
 *
 * @author ae1
 */
class KaleidoscopeRunnable extends WebcamBase {

    Graphics2D g2d;
    Thread animation;
    public static final int FLASH_WHITE_3 = 1;
    private int which = 0;
    int ms = 50;
    int randomSize = 100;
    boolean flag1 = false;
    VJOutputWindow outer;
    BufferedImage webcamImage, flipImage;
    boolean webcamAscii = false;
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;

    /*
     * From avatarTree
     */
    int w, h, drawingTime;
    OutputPanel panel;
    Point base;
    Config c;
    AvatarForest forest;
    int padding;
    WebcamTriangle tri;
    double pi = Math.PI;

    public KaleidoscopeRunnable(Config c, OutputPanel panel) {
//        webcam = new WebcamRetypar();

//        File file = new File("/dev/video0");
//
//        try {
//            is = new FileInputStream(file);
//        } catch (FileNotFoundException ex) {
//            ex.printStackTrace();
//        }
//
        g2d = (Graphics2D) panel.getGraphics();
        this.panel = panel;
        this.c = c;
        this.drawingTime = c.getDrawingDelay();

        tri = new WebcamTriangle();

        w = c.getCanvasWidth();
        h = c.getCanvasHeight();

        animation = new Thread(this);
        animation.start();
        g2d.translate(w / 2, h / 2);
    }

    @Override
    public void run() {
        while (run) {
//            try {
//
//                int numread = is.read(bytes);
//                offset += numread;
//
//                InputStream in = new ByteArrayInputStream(bytes);
//                in.reset();
//                image = ImageIO.read(in);
//
//
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }

            image = tri.getTriangle();

//            g2d.drawImage(image, 0, 0, null);



            for (int ii = 0; ii < 6; ii++) {
                g2d.drawImage(image, 0, 0, null);
                g2d.rotate(pi / 3);
            }

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(), 0);
            AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            flipImage = op.filter(image, null);

            for (int iii = 0; iii < 6; iii++) {

                g2d.drawImage(flipImage, -flipImage.getWidth(), 0, flipImage.getWidth(), flipImage.getHeight(), null);
                g2d.rotate(pi / 3);
            }

        }
    }
    private boolean run = true;

    void start() {
        run = true;
    }

    void stop() {
        run = false;
    }
}
