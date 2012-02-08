/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.finance;

import com.piousbox.ImageUtils;
import com.piousbox.MatrixUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ae1
 */
class GraphUtils {

    static void drawGraph(double[] prices, String imgOutAddr) {
        int nPrices = prices.length;
        double maxPrice = MatrixUtils.max(prices);
        double minPrice = MatrixUtils.min(prices);

        // check width of image
        // how many prices are there.
        int w = nPrices;

        // check height of image
        // the difference between min and max.
        int h = (int) (maxPrice - minPrice);

        // draw every price
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.red);
        for (int i=0; i<nPrices; i++) {
            g2d.setColor(Color.red);
            g2d.drawOval(i, h - (int)prices[i], 2, 2);
        }

        // draw an annotation.

        // save image
        ImageUtils.saveImage(img, imgOutAddr);
    }

}
