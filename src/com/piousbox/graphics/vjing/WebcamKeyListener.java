/**
 * This one actually works. 20100811 1245pm
 * @DONE [] stream video continuously;
 * [] key listener to switch between webcam filters
 * [] be able to turn from webcam input to plain drawing.
 * [] put flash here
 * 
 * @TODO [] put treeGrowdth here
 * 
 *
 *
 */
package com.piousbox.graphics.vjing;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.piousbox.ImageUtils;
import com.piousbox.graphics.Config;
import com.piousbox.graphics.kaleidoscope.Triangle;
import com.piousbox.graphics.webcam.WebcamTriangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author ae1
 */
public class WebcamKeyListener extends Frame implements Runnable, KeyListener {

    Thread animation;
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;
    WebcamTriangle tri = null;
    /**
     * Config
     */
    Config c = new Config();
    int canvasWidth = c.getCanvasWidth();
    int canvasHeight = c.getCanvasHeight();
    double kaleidoscopeScale = 1.0;
    public Filter1 filter1 = Filter1.PLAIN;
    Graphics2D outG;

    public WebcamKeyListener() {
        setTitle("ae1");

        setVisible(true);
        setSize(300, 300);

//        tri = new WebcamTriangle();
        c.setPainter(this);

        animation = new Thread(this);
        animation.start();

        File file = new File("/dev/video0");

        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(
                    WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        outputWindow = new VJOutputWindow(c);
        outG = (Graphics2D) outputWindow.getGraphics();
        outG = (Graphics2D) outputWindow.getPanel().getGraphics();
//        this.addKeyListener(new VJingKeyListener(outputWindow.getPanel(), c));
        this.addKeyListener(this);
    }
    VJOutputWindow outputWindow;

    public static void main(String[] args) {
        new WebcamKeyListener();
    }
    int rndX = 640 / 2;

    public void run() {
        while (true) {

            try {
                int numread = is.read(bytes);
                offset += numread;

                InputStream in = new ByteArrayInputStream(bytes);
                in.reset();
                BufferedImage imageSrc = ImageUtils.scale(ImageIO.read(in), this.canvasWidth);

                switch (filter1) {
                    case PLAIN:
                        image = imageSrc;
                        break;
                    case KALEIDOSCOPE:
                        image = kaleidoscope(imageSrc);
                        break;

                    default:
                    //
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            outG.drawImage(image, 0, 0, null);
            outputWindow.getPanel().repaint();

            repaint();
        }
    }

    @Override
    public void update(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(image, 0, 0, this);

        outG.drawImage(image, 0, 0, null);
        if (flashT) {
            flash(outG);
//            flash(g2d);
        }
        if (growForestT) {
            growForestUpdate(g2d, image);
        }
//        if (goldfishT) {
//            goldfish(g2d);
//        }
    }

    public void flash(Graphics2D g2d) {
        for (int i = 0; i < 3; i++) {
            try {
                g2d.setColor(Color.white);
                g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
                repaint();
                Thread.sleep(100);
                g2d.setColor(Color.black);
                g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
                repaint();
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebcamKeyListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        flashT = false;
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        System.out.println(ke);

        switch (ke.getKeyChar()) {
            case 'w':
            case 'W':
                filter1 = Filter1.PLAIN;
                break;
            case 'r':
            case 'R':
                filter1 = Filter1.KALEIDOSCOPE;
                break;
            ////////////////////////
            case 'a':
            case 'A':
                flashT = true;
                outputWindow.getPanel().flash((Graphics2D) outputWindow.getPanel().getGraphics());
                break;
            case 's':
            case 'S':
                if (growForestT) {
                    growForestT = false;
                } else {
                    growForestT = true;
                }
                break;
            case 'd':
            case 'D':
                goldfishT = true;
                this.outputWindow.getPanel().printTest();
                goldfishT = false;
                break;
            case 'q':
            case 'Q':
                break;
            default:
        }
    }
    boolean growForestT = false;
    boolean flashT = false;
    boolean goldfishT = false;

    public void keyReleased(KeyEvent ke) {
    }
    boolean rndXMovingLeft = true;

    private BufferedImage kaleidoscope(BufferedImage in) {
        in = ImageUtils.scale(in, canvasWidth / 2);

        int rndXDelta = c.rnd.nextInt(8);
        if (rndXMovingLeft) {
            rndX -= rndXDelta;
        } else {
            rndX += rndXDelta;
        }
        if (rndX <= 0) {
            rndX = 1;
            rndXMovingLeft = false;
        }
        if (rndX > in.getWidth() - 200) { // the 200 is there b/c I'm counting from the top-left corner, not from the middle for the constructed triangle.
            rndX = in.getWidth() - 200;
            rndXMovingLeft = true;
        }

        in = Triangle.constructTriangle(in, rndX);

        BufferedImage out = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) out.getGraphics();

        g2d.translate(canvasWidth / 2, canvasHeight / 2);

        for (int i = 0; i < 6; i++) {
            g2d.drawImage(in, 0, 0, this);
            g2d.rotate(Math.PI / 3);
        }

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-in.getWidth(), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage flipImage = op.filter(in, null);

        for (int i = 0; i < 6; i++) {

            g2d.drawImage(flipImage, -flipImage.getWidth(), 0, flipImage.getWidth(), flipImage.getHeight(), this);
            g2d.rotate(Math.PI / 3);
        }


        return out;
    }

    private void growForestUpdate(Graphics2D g2d, BufferedImage background) {
        try {

            // for each row
            for (int nRow = 0; nRow < c.nRows; nRow++) {

                // for each tree
                for (int nTree = 0; nTree < c.nTreesPerRow; nTree++) {
                    Point base = new Point(
                            c.rnd.nextInt(c.getCanvasWidth() - c.leftRightPadding * 2) + c.leftRightPadding,
                            c.getCanvasHeight() - c.rowSpacing * nRow - c.bottomPadding);

                    AvatarTree tree = new AvatarTree(base, g2d, c);
                    repaint();
                    Thread.sleep(200);

                }
            }


            growForestT = false;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void goldfish(Graphics2D g2d) {
        String tempAddr = (c.rnd.nextBoolean() == true) ? "goldfish.png" : "goldfish_flip.png";
        for (int i = 0; i < c.nGoldfish; i++) {
            int x = c.rnd.nextInt(c.w) - 100;
            int y = c.rnd.nextInt(c.h) - 78;

            g2d.drawImage(ImageUtils.loadBufferedImage(c.imgLib + tempAddr), x, y, null);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebcamKeyListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(WebcamKeyListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        goldfishT = false;
    }
}
