package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import com.piousbox.MatrixUtils;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

/**
 *
 * @author root
 */
public class MultisLI extends Component {

    private void multisLIRunner() {

        File[] inFilesArr = IOUtils.imagesInDir("/home/ae1/public_domain/uscites.gov/tiger1/", false);
        Arrays.sort(inFilesArr);
        String outAddr = "/home/ae1/public_domain/uscites.gov/tiger1/multisLI/";

//        File[] inFilesArr = new File[1];
//        inFilesArr[0] = new File("/home/ae1/trash/hammer.jpg");
        
//        File[] inFilesArr = IOUtils.imagesInDir("/home/ae1/archive/2010/data/images/logos", false);
//        String outAddr = "/home/ae1/trash/";

        // process
        for (int i = 0; i < inFilesArr.length; i++) {
//            String zeroes = "";
//            if (i<1000) zeroes += "0";
//            if (i<100) zeroes += "0";
//            if (i<10) zeroes += "0";
            
            BufferedImage out = process(ImageUtils.loadBufferedImage(inFilesArr[i]));

            ImageUtils.saveImage(out, outAddr + inFilesArr[i].getName() + ".png");
        }

        // output files
    }
    Config c = new Config();
    /**
     * diameter of clipping shape
     */
    int d;
    static double pi = Math.PI;
    int w;
    int h;

    public BufferedImage process(BufferedImage img) {
        w = img.getWidth();
        h = img.getHeight();
//        d = img.getHeight() / 6; // diameter
        d = (int) (h/6 / Math.sin(pi / 3)); // q
        // determine points, centers of cells
        Point[] centers = getCentersOfCells(img, c.multisN);

        // construct a new image
        BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Polygon clipShape = computeClipShape();

        Graphics2D g2d = newImg.createGraphics();
        g2d.drawImage(img, 0, 0, this);
        for (Point center : centers) {
//            System.out.println("centers: " + center.x + " " + center.y);
            g2d = newImg.createGraphics();
            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke(5));
            g2d.translate(center.x, center.y);

            AffineTransform transformer = new AffineTransform();
            transformer.scale(c.multisPercentOut, c.multisPercentOut);
            transformer.translate(-center.x, -center.y);

//            g2d.draw(clipShape);

            g2d.setClip(clipShape);
            g2d.drawImage(img, transformer, this);
        }


        return newImg;
    }

    public MultisLI() {
        multisLIRunner();

//        BufferedImage img = ImageUtils.loadBufferedImage(c.inAddr);
//
//        BufferedImage newImg = process(img);


//        w = img.getWidth();
//        h = img.getHeight();
//        d = img.getHeight() / 6; // diameter
//        // determine points, centers of cells
//        Point[] centers = getCentersOfCells(img, c.multisN);
//
//        // construct a new image
//        BufferedImage newImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Polygon clipShape = computeClipShape();
//
//
//
//        for (Point center : centers) {
//            System.out.println("centers: " + center.x + " " + center.y);
//            Graphics2D g2d = newImg.createGraphics();
//            g2d.translate(center.x, center.y);
//
//            AffineTransform transformer = new AffineTransform();
//            transformer.scale(c.multisPercentOut, c.multisPercentOut);
//            transformer.translate(-center.x, -center.y);
//
//            g2d.setClip(clipShape);
//            g2d.drawImage(img, transformer, this);
//
//        }
        /**
         *
         */
//        Graphics2D g2d = newImg.createGraphics();
//        Graphics2D g2d1 = newImg.createGraphics();
//        Graphics2D g2d2 = newImg.createGraphics();
//        Graphics2D g2d3 = newImg.createGraphics();
//
//        g2d.setColor(Color.blue);
//        g2d1.setColor(Color.blue);
//        g2d2.setColor(Color.blue);
//        g2d3.setColor(Color.red);
//
////        g2d.drawImage(img, 0, 0, null);
//
//        Point center = centers[0];
//        Point center1 = centers[1];
//        Point center2 = centers[2];
//        Point center3 = centers[3];
//
//        g2d.translate(center.x, center.y);
//        g2d1.translate(center1.x, center1.y);
//
//        g2d.setClip(computeClipShape());
//        g2d1.setClip(computeClipShape());
//
//        g2d.drawImage(img, -center.x, -center.y, this);
//        g2d1.drawImage(img, -center1.x, -center1.y, this);
//        ImageUtils.saveImage(newImg, c.outAddr);
    }

    public Point[] getCentersOfCells(BufferedImage img, int nCells) {
//        assert nCells == 7 : "this only works right now for 7 cells";
//
//        // I think the valid numbers of cells are 1+6*0, 1+6*1, 1+6*4, ...
//        assert nCells > 0 : "number of cells must be positive!";
//
//        if (nCells != 1 && nCells != 13) {
//            assert (nCells - 1) % 6 == 0 : "I think number of cells minus one should be divisible by 6.";
//        }

        Point[] out = new Point[nCells];
        /*
         * From test
         */
        int deltaX = (int) (h/6 * Math.tan(pi / 3));
        int deltaY = (int) (h/6 / Math.cos(pi / 3));
        

        out[0] = new Point(w / 2, h / 2);
        out[1] = new Point(w / 2, h / 6);
        out[2] = new Point(w / 2, 5 * h / 6);

        out[3] = new Point(w / 2 - deltaX, h/6 * 2);
        out[4] = new Point(w / 2 - deltaX, h/6 * 4);
        out[5] = new Point(w / 2 + deltaX, h/6 * 2);
        out[6] = new Point(w / 2 + deltaX, h/6 * 4);


        int smallW = w / 2 - (3*d);
        int largeW = w / 2 + (3*d);

        out[7] = new Point(smallW, h / 6);
        out[8] = new Point(smallW, h / 2);
        out[9] = new Point(smallW, 5 * h / 6);

        out[10] = new Point(largeW, h / 6);
        out[11] = new Point(largeW, h / 2);
        out[12] = new Point(largeW, 5 * h / 6);

        return out;
    }

    public static void main(String[] args) {
//        BufferedImage img = ImageUtils.loadBufferedImage(c.inAddr);
//        BufferedImage newImg = process(img);
//        ImageUtils.saveImage(newImg, c.outAddr);

//        multisLIRunner();

        MultisLI mLI = new MultisLI();
    }

    /**
     * Computes clip shape for one cell.
     * @param p
     * @return
     * @deprecated
     */
    private Polygon computeClipShape(Point p) {
        int deltaX = (int) (d * Math.cos(pi / 3));
        int deltaY = (int) (d * Math.sin(pi / 3));
        Polygon clipShape = new Polygon();
        clipShape.addPoint(p.x + d, p.y);
        clipShape.addPoint(p.x + deltaX, p.y + deltaY);
        clipShape.addPoint(p.x - deltaX, p.y + deltaY);
        clipShape.addPoint(p.x - d, p.y);
        clipShape.addPoint(p.x - deltaX, p.y - deltaY);
        clipShape.addPoint(p.x + deltaX, p.y - deltaY);
        return clipShape;
    }

    /**
     * This one assumes that p.x == 0 and p.y == 0 (b/g g2d's center has been translated.
     * @return
     */
    private Polygon computeClipShape() {
//        d = (int) (d / c.multisPercentOut);
        int deltaX = (int) (d * Math.cos(pi / 3));
        int deltaY = (int) (d * Math.sin(pi / 3));
        Polygon clipShape = new Polygon();
        clipShape.addPoint(0 + d, 0);
        clipShape.addPoint(0 + deltaX, 0 + deltaY);
        clipShape.addPoint(0 - deltaX, 0 + deltaY);
        clipShape.addPoint(0 - d, 0);
        clipShape.addPoint(0 - deltaX, 0 - deltaY);
        clipShape.addPoint(0 + deltaX, 0 - deltaY);
        return clipShape;
    }
}
