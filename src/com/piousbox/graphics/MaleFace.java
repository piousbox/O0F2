/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.ImageUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ae1
 */
public class MaleFace {

    Config c;
    int wIn, hIn;
    int sizeOfRectangle;

    public MaleFace() {
        c = new Config();

        /*
         * load a canvas.
         */
        BufferedImage canvas = ImageUtils.loadBufferedImage("/home/ae1/archive/2010/data/male_face/in_7.jpg");
        wIn = canvas.getWidth();
        hIn = canvas.getHeight();

        sizeOfRectangle = wIn / c.getNRectangles();

        BufferedImage out = newImageOut(canvas, c);
        Graphics2D g2d = (Graphics2D) out.getGraphics();
        
        /*
         * White background
         */
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, out.getWidth(), out.getHeight());

        /*
         * Split into rectangles
         */
        BufferedImage[] rectangles = ImageUtils.splitToRectangles(canvas, c.getNRectangles());

        /*
         * Processing loop
         */
        int counter = 0;
        for (int x = 0; x < c.getNRectangles(); x++) {
            for (int y = 0; y < nRectangesInHeight(); y++) {
                int size = (int) Math.floor(out.getWidth()/c.getNRectangles());
                /*
                 * compute average color of the rectangle
                 */
                Color tempColor = computeAverageColor(rectangles[counter++]);
                /*
                 * Paint this color
                 */
                g2d.setColor(tempColor);
                g2d.fillOval(x*size, y*size, size, size);
            }


        }

        /*
         * Save
         */
        ImageUtils.saveImage(out, c.getImgOutAddr());

    }

    public static void main(String[] args) {
        new MaleFace();
    }

    /**
     * Tested. Returns the canvas out buffered image with correct dimensions (usually very large, for printing).
     * @param canvas
     * @param c
     * @return
     */
    BufferedImage newImageOut(BufferedImage canvas, Config c) {
        int wIn = canvas.getWidth();
        int hIn = canvas.getHeight();
        return new BufferedImage(c.getMaleFaceWidthOut(), c.getMaleFaceWidthOut() * hIn / wIn, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Tested. Returns the average color of an image.
     * @param in
     * @return
     */
    public Color computeAverageColor(BufferedImage in) {
        int r = 0;
        int g = 0;
        int b = 0;

        int w = in.getWidth();
        int h = in.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Color c = new Color(in.getRGB(x, y));
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
            }
        }

        r /= w;
        r /= h;

        g /= w;
        g /= h;

        b /= w;
        b /= h;

        return new Color(r, g, b);
    }

    private int nRectangesInHeight() {
        return (int) Math.floor(hIn / sizeOfRectangle);
    }
}
