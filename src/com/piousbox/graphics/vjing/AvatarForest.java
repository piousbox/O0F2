/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import com.piousbox.graphics.vjing.OutputPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author ae1
 */
public class AvatarForest {

    OutputPanel parent;
    Config c;
    int w, h;

    public AvatarForest(Config c, Graphics g) {


        this.c = c;
        w = c.getCanvasWidth();
        h = c.getCanvasHeight();

        // for each row
        for (int nRow = 0; nRow < c.nRows; nRow++) {

            // for each tree
            for (int nTree = 0; nTree < c.nTreesPerRow; nTree++) {
                Point base = new Point(
                        c.rnd.nextInt(w - c.leftRightPadding*2) + c.leftRightPadding,
                        h - c.rowSpacing*nRow - c.bottomPadding);
                AvatarTree tree = new AvatarTree(base, g, c, this);
            }
        }

    }

    public AvatarForest(Config c, Graphics2D g2d, BufferedImage background) {
//        throw new UnsupportedOperationException("Not yet implemented");
        this.c = c;
        w = c.getCanvasWidth();
        h = c.getCanvasHeight();

        // for each row
        for (int nRow = 0; nRow < c.nRows; nRow++) {

            // for each tree
            for (int nTree = 0; nTree < c.nTreesPerRow; nTree++) {
                Point base = new Point(
                        c.rnd.nextInt(w - c.leftRightPadding*2) + c.leftRightPadding,
                        h - c.rowSpacing*nRow - c.bottomPadding);
                AvatarTree tree = new AvatarTree(base, g2d, c, this);
//                g2d.drawImage(background, 0, 0, null);
            }
        }
    }


}
