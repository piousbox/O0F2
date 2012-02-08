/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.ImageUtils;
import com.piousbox.MatrixUtils;
import com.piousbox.graphics.Config;
import com.piousbox.graphics.Fragmenter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Used for source Image
 * @author ae1
 */
public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener {

    private Image img;
    ArrayList<Point> points = new ArrayList<Point>();
    ArrayList<Line2D> linesArrList = new ArrayList<Line2D>();
    Point[] tempNorthWestPoints;
    /**
     * a method of partitioning.
     */
    boolean isDrawTwoclick = false;
    Point start, end;
    /**
     * A method of partitioning.
     */
    boolean isDrawLimitedLines = false;
    Point northPoint = null, southPoint, eastPoint, westPoint;
    Random rnd = new Random();
    Config config;

    public ImagePanel(Image img, Config config) {
        this.img = img;
        this.config = config;

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public BufferedImage getImage() {
        return (BufferedImage) img;
    }

    public void setIsDrawLimitedLines(boolean in) {
        isDrawLimitedLines = in;
    }

    public void toggleIsDrawLimitedLines() {
        if (isDrawLimitedLines == true) {
            isDrawLimitedLines = false;
        } else {
            isDrawLimitedLines = true;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null); // this draws gray background.
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) img.getGraphics();

        if (isDrawTwoclick) {
            g2d.drawLine(start.x, start.y, end.x, end.y);
            isDrawTwoclick = false;
        }

        if (isDrawLimitedLines && northPoint != null) {
            g2d.drawLine(westPoint.x, westPoint.y, eastPoint.x, eastPoint.y);
            g2d.drawLine(northPoint.x, northPoint.y, southPoint.x, southPoint.y);
        }

        // if nothing else happens
        super.paint(g);
    }

    public void mouseClicked(MouseEvent e) {
        if (isDrawTwoclick) {
            drawLinesTwoClickMouseEvent(e);
        }

        if (isDrawLimitedLines) {
            drawLimitedLines(e);
        }

        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
    }

    public void mouseMoved(MouseEvent me) {
    }

    /**
     * @deprecated probably use drawLimitedLinesMouseEvent.
     * The first variation. Click button1 to draw horizontal line across
     * the whole image, and click button3 to draw vertical line across img.
     * @param e
     */
    private void drawLinesTwoClickMouseEvent(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // ---
//                Config.e("button1");
            int y = e.getY() - this.getInsets().top;
            start = new Point(0, y);
            end = new Point(img.getWidth(null), y);

        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            // |||
//                Config.e("button3");
            int x = e.getX() - this.getInsets().left;
            start = new Point(x, 0);
            end = new Point(x, img.getHeight(null));


        }
    }

    /**
     * A single button1 click gives you a point, linesArrList extending to closest
     * other limiting linesArrList in all (four) directions.
     * @param e
     */
    private void drawLimitedLines(MouseEvent e) {
        // record the click, right?

        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX() - this.getInsets().left;
            int y = e.getY() - this.getInsets().top;

            northPoint = findNorth(new Point(x, y));
            southPoint = findSouth(new Point(x, y));
            westPoint = findWest(new Point(x, y));
            eastPoint = findEast(new Point(x, y));

            linesArrList.add(new Line2D.Double(westPoint.x, westPoint.y, eastPoint.x, eastPoint.y));
            linesArrList.add(new Line2D.Double(northPoint.x, northPoint.y, southPoint.x, southPoint.y));
        }

        /*
         * compute north: find a line s.t.
         * line.x1 < point.x &&
         * line.x2 > point.x &&
         * line.y2 MAX of all linesArrList &&
         * line.y2 < point.y
         */

        /*
         * compute south: find a line s.t.
         * line.x1 < point.x &&
         * line.x2 > point.x &&
         * line.y2 MIN of all linesArrList &&
         * line.y2 > point.y
         */
    }

    private Point findNorth(Point in) {

        Line2D.Double lineMaxY = new Line2D.Double(0, 0, img.getWidth(this), 0); // lineMaxY

        /*
         * Iterate through linesArrList
         */
        Iterator it = linesArrList.iterator();
        while (it.hasNext()) {
            Line2D.Double thisLine = (Line2D.Double) it.next();
            if (thisLine.x1 < in.x && thisLine.x2 > in.x && thisLine.y1 < in.y) {
                if (thisLine.y1 > lineMaxY.y1) {
                    lineMaxY = thisLine;
                }
            }
        }

        Point out = new Point(in.x, (int) lineMaxY.y1);
        return out;
    }

    private Point findSouth(Point in) {
        Line2D.Double lineMinY = new Line2D.Double(0, img.getHeight(this), img.getWidth(this), img.getHeight(this)); // lineMinY

        /*
         * Iterate through linesArrList
         */
        Iterator it = linesArrList.iterator();
        while (it.hasNext()) {
            Line2D.Double thisLine = (Line2D.Double) it.next();
            if (thisLine.x1 < in.x && thisLine.x2 > in.x && thisLine.y1 > in.y) {
                if (thisLine.y1 < lineMinY.y1) {
                    lineMinY = thisLine;
                }
            }
        }

        Point out = new Point(in.x, (int) lineMinY.y1);
        return out;
    }

    private Point findWest(Point in) {
        Line2D.Double lineMaxX = new Line2D.Double(0, 0, 0, img.getHeight(this)); // lineMaxX

        /*
         * Iterate through linesArrList
         */
        Iterator it = linesArrList.iterator();
        while (it.hasNext()) {
            Line2D.Double thisLine = (Line2D.Double) it.next();
            if (thisLine.y1 < in.y && thisLine.y2 > in.y && thisLine.x1 < in.x) {
                if (thisLine.x1 > lineMaxX.x1) {
                    lineMaxX = thisLine;
                }
            }
        }

        Point out = new Point((int) lineMaxX.x1, in.y);
        return out;
    }

    private Point findEast(Point in) {
        Line2D.Double lineMinX = new Line2D.Double(img.getWidth(this), 0, img.getWidth(this), img.getHeight(this)); // lineMinX

        /*
         * Iterate through linesArrList
         */
        Iterator it = linesArrList.iterator();
        while (it.hasNext()) {
            Line2D.Double thisLine = (Line2D.Double) it.next();
            if (thisLine.y1 < in.y && thisLine.y2 > in.y && thisLine.x1 > in.x) {
                if (thisLine.x1 < lineMinX.x1) {
                    lineMinX = thisLine;
                }
            }
        }

        Point out = new Point((int) lineMinX.x1, in.y);
        return out;
    }

    public BufferedImage[] getPartitions() {
//        ArrayList<BufferedImage> partitionsArrList = new ArrayList<BufferedImage>();

        int nPartitions = (linesArrList.size() - 2) * 3 + 4;
        Point[] tempPoints = new Point[nPartitions];
        BufferedImage partitions[] = new BufferedImage[nPartitions];

        int counter = 0;
        int thisX = 0;
        int thisY = 0;

//        for (int i = 0; i < nPartitions; i++) {
        while (thisX < img.getWidth(null) && thisY < img.getHeight(null)) {
            /**
             * check points in increments of 10px.
             */
            thisX += 10;
            thisY += 10;

            Point thisPoint = new Point(thisX, thisY);
            Point north = findNorth(thisPoint);
            Point northWest = findWest(north);
            Point south = findSouth(thisPoint);
            Point southEast = findEast(south);

            if (!MatrixUtils.isIn(northWest, tempPoints)) {
                /**
                 * this is a new slice.
                 */
                tempPoints[counter] = northWest;

                BufferedImage thisImg = ImageUtils.crop((BufferedImage) img, northWest.x, northWest.y, southEast.x - northWest.x, southEast.y - northWest.y);
                partitions[counter] = thisImg;

                counter++;
            }
        }

        tempNorthWestPoints = tempPoints;

        return partitions;
    }

    public Point[] getNorthWestPoints() {
        assert tempNorthWestPoints != null : "call getPartitions() first.";
        return tempNorthWestPoints;
    }

    public void draw(BufferedImage slice, Point point) {
        Graphics2D g2d = (Graphics2D) img.getGraphics();
        g2d.drawImage(slice, point.x, point.y, null);
        repaint();
    }
}
