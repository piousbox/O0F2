/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.webcam;

import com.piousbox.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import com.piousbox.graphics.O0Filter;
import com.piousbox.graphics.kaleidoscope.Triangle;
import com.piousbox.graphics.webcam.TriangleInterface;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ae1
 */
public class WebcamTriangle implements TriangleInterface, Runnable {

    Thread animation;
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;

    public WebcamTriangle() {

        animation = new Thread(this);
        animation.start();

        File file = new File("/dev/video0");

        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    boolean run = true;
    void stop () {
        run = false;
        animation.stop();
    }

    public static void main(String[] args) {
        WebcamTriangle w = new WebcamTriangle();
        BufferedImage out = w.getTriangle();
        ImageUtils.saveImage(out, "/home/ae1/data/out/20100804.trash.png");
        System.exit(0);
    }

    int rndX = 640/2;
    static Random rnd = new Random();

    public void run() {
        while (run) {
            try {

                int numread = is.read(bytes);
                offset += numread;

                InputStream in = new ByteArrayInputStream(bytes);
                in.reset();
                BufferedImage temp = ImageIO.read(in);
                rndX += rnd.nextInt(16) - 8;
                if (rndX < 0) {
                    rndX = 0;
                }
                if (rndX > temp.getWidth() - 200) {
                    rndX = temp.getWidth() - 200;
                }
//                System.out.println(rndX);
                image = Triangle.constructTriangle(temp, rndX);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public BufferedImage getTriangle() {
        
        return image;
    }
}
