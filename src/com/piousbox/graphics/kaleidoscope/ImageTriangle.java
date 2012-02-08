package com.piousbox.graphics.kaleidoscope;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class ImageTriangle extends Triangle {

    BufferedImage out = null;
    int w, h;
    double pi = Math.PI;

    public ImageTriangle() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public ImageTriangle(BufferedImage img) {
        w = img.getWidth();
        h = img.getHeight();

        out =  constructTriangle(img, 0);
        
    }

    public BufferedImage getTriangle() {
        
        return out;
    }
    
}
