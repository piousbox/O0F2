/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;
import com.piousbox.graphics.Config;
import com.piousbox.graphics.vjing.OutputPanel;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author ae1
 */
public class AvatarTree implements Runnable {

    Graphics2D g2d;
    int w, h, i, drawingTime;
    Point base;
    Thread animation;
    Config c;
    Random rnd;
    // temp
    int padding;
    boolean run = true;

    public AvatarTree(Point base, Graphics g, Config c) {
        g2d = (Graphics2D) g;
        this.c = c;
        rnd = c.rnd;
        this.base = base;
        this.drawingTime = c.getDrawingDelay();
        this.padding = c.leftRightPadding;
        w = c.getCanvasWidth();
        h = c.getCanvasHeight();

        animation = new Thread(this);
        animation.start();
    }

    /**
     *
     * @deprecated use without forest.
     * @param base
     * @param g
     * @param c
     * @param forest
     */
     AvatarTree(Point base, Graphics g, Config c, AvatarForest forest) {
        this(base, g, c);
    }

    public void run() {
        while (run) {

            Point[] forks = new Point[rnd.nextInt(c.nForksDistr) - c.nForksDistr / 2 + c.nForksCenter];
            int[] trunkWidths = new int[forks.length];
            int forksCount = 0;

            int trunkSize = rnd.nextInt(c.trunkSizeDist) - c.trunkSizeDist / 2 + c.trunkSizeCenter;
            int grayness = rnd.nextInt(128) + 128;
            Color trunkColor = new Color(grayness, grayness, grayness);
//            Color trunkColor = new Color(
//                    rnd.nextInt((256 - brightness) / c.nRows * i / c.nTreesPerRow + 1) + brightness - 1,
//                    rnd.nextInt((256 - brightness) / c.nRows * i / c.nTreesPerRow + 1) + brightness - 1,
//                    rnd.nextInt((256 - brightness) / c.nRows * i / c.nTreesPerRow + 1) + brightness - 1);
//            Color trunkColor = new Color(rnd.nextInt(256 - brightness) + brightness,
//                    rnd.nextInt(256 - brightness) + brightness,
//                    rnd.nextInt(256 - brightness) + brightness);
            g2d.setColor(trunkColor);

            // for each trunk piece:
            int trunkHeight = rnd.nextInt(c.nTrunkHeightDistr) + c.nTrunkHeightCenter;
            for (int nHeight = 0; nHeight < trunkHeight; nHeight++) {
                try {
                    int pieceHeight = rnd.nextInt(50) + 10;
                    int diversionDelta = rnd.nextInt(c.baseTreeDelta);
                    diversionDelta = (rnd.nextInt(2) == 1) ? diversionDelta : -diversionDelta;
                    if (diversionDelta < 2) {
                        diversionDelta = 2;
                    }
                    if (trunkSize < 2) {
                        break;
                    }
                    g2d.setStroke(new BasicStroke(trunkSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    trunkSize -= rnd.nextInt(15);
                    int x2;
                    x2 = base.x + diversionDelta;

                    if (x2 <= 0) {
                        x2 = rnd.nextInt(diversionDelta / 2);
                    }
                    if (x2 >= w) {
                        x2 = w - rnd.nextInt(diversionDelta / 2);
                    }
                    int y2 = base.y - pieceHeight;
                    g2d.drawLine(base.x, base.y, x2, y2);
                    Thread.sleep(100);
                    c.repaint();

                    g2d.drawLine(base.x, base.y, x2, y2);
                    Thread.sleep(100);
                    c.repaint();

                    base = new Point(x2, y2);
                    if (forksCount < forks.length && rnd.nextInt(c.branchingFreq) == 1) {
                        trunkWidths[forksCount] = trunkSize;
                        forks[forksCount] = new Point(base);
                        forksCount++;
                    }
                } catch (NullPointerException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // now grow branches
            for (int branch = 0; branch < forks.length; branch++) {
                base = forks[branch];
                for (int nHeight = 0; nHeight < rnd.nextInt(c.branchNLengthDist) - c.branchNLengthDist / 2 + c.branchNLengthCenter; nHeight++) {
                    try {
                        int pieceHeight = rnd.nextInt(50) + 10;
                        int diversionDelta = rnd.nextInt(c.baseTreeDelta);
                        diversionDelta = (rnd.nextInt(2) == 1) ? diversionDelta : -diversionDelta;
                        trunkSize = trunkWidths[branch];
                        if (trunkSize < 2) {
                            continue;
                        }
                        g2d.setStroke(new BasicStroke(trunkSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                        trunkSize -= rnd.nextInt(15);
                        int x2 = base.x + diversionDelta;
                        int y2 = base.y - pieceHeight;
                        g2d.drawLine(base.x, base.y, x2, y2);
                        Thread.sleep(100);
                        c.repaint();
                        base = new Point(x2, y2);
                        if (forksCount < forks.length && rnd.nextInt(c.branchingFreq) == 1) {
                            forks[forksCount++] = new Point(base);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
//            run = false;
//            c.sleepNpaint();
        }
    }
}
