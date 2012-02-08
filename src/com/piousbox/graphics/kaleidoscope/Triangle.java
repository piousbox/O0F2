/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.kaleidoscope;

import com.piousbox.ImageUtils;
import com.piousbox.graphics.webcam.TriangleInterface;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author ae1
 */
public abstract class Triangle implements TriangleInterface {

    static final double pi = Math.PI;

    public static BufferedImage constructTriangle(BufferedImage img, int rndX) {
        int h = img.getHeight();
        int w = img.getWidth();

        img = ImageUtils.crop(img, rndX, 0, w, h);

        int halfH = h;

        BufferedImage out = new BufferedImage(
                (int) (halfH * Math.sin(pi / 6.0)),
                halfH,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) out.getGraphics();

        Polygon clipShape = new Polygon();
        clipShape.addPoint(0, 0);
        clipShape.addPoint(0, halfH);

        clipShape.addPoint(
                (int) (halfH * Math.sin(pi / 6.0)) + 6, // the 4 is here to remove
                (int) (halfH * Math.cos(pi / 6.0)) + 5); // the annoying thin line

        g2d.setClip(clipShape);
        g2d.drawImage(img, 0, 0, null);

        g2d.dispose();

        return out;
    }
}
