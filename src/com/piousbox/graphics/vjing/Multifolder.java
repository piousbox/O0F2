/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import com.piousbox.ImageUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ae1
 */
public class Multifolder {

    Config c = new Config();

    public Multifolder() {
        for (String in : c.inArr) {

            BufferedImage bf = ImageUtils.loadBufferedImage(c.dirIn + in);
            int w = bf.getWidth();
            int h = bf.getHeight();

            BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) out.createGraphics();

            int xSrcL, xSrcR, xDestL, xDestR;

            int stripeWidth = w / c.nStripes;
            for (int i = 0; i < c.nStripes; i++) {
                xDestL = stripeWidth * i;
                xDestR = stripeWidth * (i + 1);

                xSrcL = stripeWidth * i - c.stripeSpread;
                xSrcR = stripeWidth * (i + 1) + c.stripeSpread;

                if (xSrcL < 0) {
                    xSrcL = 0;
                }
                if (xSrcR > w) {
                    xSrcR = w;
                }

                BufferedImage temp = ImageUtils.crop(bf, xSrcL, 0, xSrcR - xSrcL, h);
                g2d.drawImage(temp, xDestL, 0, xDestR - xDestL, h, null);
            }

            g2d.dispose();

            ImageUtils.saveImage(out, c.dirOut + in + ".png"); // I still haven't implemented saving other than PNG.
        }
    }

    public static void main(String[] args) {
        new Multifolder();

    }

    class Config {

        String dirIn = "/home/ae1/data/images/";
        String dirOut = "/home/ae1/data/out/";
        String[] inArr = {"519x573.jpg", "hot_chick.jpg", "LESBIANS.jpg"};
        int nStripes = 10;
        /**
         * how far into other stripes this stripe goes; probably should be less than stripe width.
         */
        int stripeSpread = 40;
    }
}
