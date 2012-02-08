package demos;

/*
 * @(#)fractal.java - Fractal Program Mandelbrot Set
 * www.eckhard-roessel.de
 * Copyright (c) Eckhard Roessel. All Rights Reserved.
 * 06/30/2000 - 03/29/2000
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

/**
 * @version 1.1
 * @author Eckhard Roessel
 * @modified 03/29/2001
 */
public class Mandelbrot extends Applet implements MouseListener, MouseMotionListener {

    private final int MAX = 256;      // max iterations
    private final double SX = -2.025; // start value real
    private final double SY = -1.125; // start value imaginary
    private final double EX = 0.6;    // end value real
    private final double EY = 1.125;  // end value imaginary
    private static int x1, y1, xs, ys, xe, ye;
    private static double xstart, ystart, xende, yende, xzoom, yzoom;
    private static boolean action, rechteck, fertig;
    private static float xy;
    private Image bild;
    private Graphics g1;
    private Cursor c1, c2;

    public void init() // all instances will be prepared
    {
        fertig = false;
        addMouseListener(this);
        addMouseMotionListener(this);
        c1 = new Cursor(Cursor.WAIT_CURSOR);
        c2 = new Cursor(Cursor.CROSSHAIR_CURSOR);
        x1 = getSize().width;
        y1 = getSize().height;
        xy = (float) x1 / (float) y1;
        bild = createImage(x1, y1);
        g1 = bild.getGraphics();
        fertig = true;
    }

    public void destroy() // delete all instances
    {
        if (fertig) {
            removeMouseListener(this);
            removeMouseMotionListener(this);
            bild = null;
            g1 = null;
            c1 = null;
            c2 = null;
            System.gc(); // garbage collection
        }
    }

    public void start() {
        action = false;
        rechteck = false;
        startwerte();
        xzoom = (xende - xstart) / (double) x1;
        yzoom = (yende - ystart) / (double) y1;
        mandelbrot();
    }

    public void stop() {
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void update(Graphics g) {
        g.drawImage(bild, 0, 0, this);
        if (rechteck) {
            g.setColor(Color.white);
            if (xs < xe) {
                if (ys < ye) {
                    g.drawRect(xs, ys, (xe - xs), (ye - ys));
                } else {
                    g.drawRect(xs, ye, (xe - xs), (ys - ye));
                }
            } else {
                if (ys < ye) {
                    g.drawRect(xe, ys, (xs - xe), (ye - ys));
                } else {
                    g.drawRect(xe, ye, (xs - xe), (ys - ye));
                }
            }
        }
    }

    private void mandelbrot() // calculate all points
    {
        int x, y;
        float h, b, alt = 0.0f;

        action = false;
        setCursor(c1);
        showStatus("Mandelbrot-Set will be produced - please wait...");
        for (x = 0; x < x1; x += 2) {
            for (y = 0; y < y1; y++) {
                h = punktfarbe(xstart + xzoom * (double) x, ystart + yzoom * (double) y); // color value
                if (h != alt) {
                    b = 1.0f - h * h; // brightnes
                    g1.setColor(Color.getHSBColor(h, 0.8f, b));
                    alt = h;
                }
                g1.drawLine(x, y, x + 1, y);
            }
        }
        showStatus("Mandelbrot-Set ready - please select zoom area with pressed mouse.");
        setCursor(c2);
        action = true;
    }

    private float punktfarbe(double xwert, double ywert) // color value from 0.0 to 1.0 by iterations
    {
        double r = 0.0, i = 0.0, m = 0.0;
        int j = 0;

        while ((j < MAX) && (m < 4.0)) {
            j++;
            m = r * r - i * i;
            i = 2.0 * r * i + ywert;
            r = m + xwert;
        }
        return (float) j / (float) MAX;
    }

    private void startwerte() // reset start values
    {
        xstart = SX;
        ystart = SY;
        xende = EX;
        yende = EY;
        if ((float) ((xende - xstart) / (yende - ystart)) != xy) {
            xstart = xende - (yende - ystart) * (double) xy;
        }
    }

    public void mousePressed(MouseEvent e) {
        e.consume();
        if (action) {
            xs = e.getX();
            ys = e.getY();
        }
    }

    public void mouseReleased(MouseEvent e) {
        int z, w;

        e.consume();
        if (action) {
            xe = e.getX();
            ye = e.getY();
            if (xs > xe) {
                z = xs;
                xs = xe;
                xe = z;
            }
            if (ys > ye) {
                z = ys;
                ys = ye;
                ye = z;
            }
            w = (xe - xs);
            z = (ye - ys);
            if ((w < 2) && (z < 2)) {
                startwerte();
            } else {
                if (((float) w > (float) z * xy)) {
                    ye = (int) ((float) ys + (float) w / xy);
                } else {
                    xe = (int) ((float) xs + (float) z * xy);
                }
                xende = xstart + xzoom * (double) xe;
                yende = ystart + yzoom * (double) ye;
                xstart += xzoom * (double) xs;
                ystart += yzoom * (double) ys;
            }
            xzoom = (xende - xstart) / (double) x1;
            yzoom = (yende - ystart) / (double) y1;
            mandelbrot();
            rechteck = false;
            repaint();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        e.consume();
        if (action) {
            xe = e.getX();
            ye = e.getY();
            rechteck = true;
            repaint();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public String getAppletInfo() {
        return "fractal.class - Mandelbrot Set a Java Applet by Eckhard Roessel 2000-2001";
    }
}
