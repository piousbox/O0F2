/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author ae1
 */
public class Frag {

    private BufferedImage subImg;
    private Point northWest;

    public Frag(BufferedImage subImg, Point northWest) {
        this.subImg = subImg;
        this.northWest = northWest;
    }

    public Point getNwPoint() {
        return northWest;
    }

    public BufferedImage getSubImg() {
        return subImg;
    }

    /**
     * @deprecated use Frag(BufferedImage, Point).
     * @param subImg
     * @param filename
     * @param fragX
     * @param fragY
     */
    public Frag(BufferedImage subImg, String filename, int fragX, int fragY) {
        // this is from old?
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int[] getMaskIntArr() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getMaskW() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getMaskH() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
