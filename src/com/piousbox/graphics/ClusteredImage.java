/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics;

import com.piousbox.ImageUtils;
import java.awt.*;
import java.awt.image.*;
import java.util.*;


/**
 * This class extends Image. This class presents an object that contains the artistic blobs of o0-filer.
 * This is my old o0-filter.
 * @author ae1
 */
public class ClusteredImage extends Image {

    // ImgUtils
    BufferedImage img;
    int w, h;
    Random rnd = new Random();
    ArrayList<Blob> blobs = new ArrayList<Blob>();

    public ClusteredImage(BufferedImage img, Config config) {
        process(img, config.getP(), config.getThresh(), config.getNBlobs());
    }

    public void process(BufferedImage img, int p, int thresh, int nBlobs) {
        this.img = img;
        w = img.getWidth();
        h = img.getHeight();

        // for many Blobs
        for (int k=0; k<nBlobs; k++) {
            // take a random pixel
            int idxX = rnd.nextInt(w-p-p)+p;
            int idxY = rnd.nextInt(h-p-p)+p;

            // check
//            System.out.println("new point: "+ idxX +" "+ idxY);

            //
            // define blob?!
            //
            Blob blob = new Blob();
            blob.addPt(idxX,idxY);

            // take its KNN
            for (int i=idxX-p; i<idxX+p; i++) {
                for (int j=idxY-p; j<idxY+p; j++) {
                    // if below threshold then add to blob
                    int D = ImageUtils.diff(img.getRGB(idxX, idxY), img.getRGB(i, j));
//                    System.out.println("difference is: "+ D);
                    if (D<thresh)
                        blob.addPt(i, j);
                }
            }
            blobs.add(blob);
        }
    }

    public ClusteredImage(BufferedImage img, int p, int thresh, int nBlobs) {
        process(img, p, thresh, nBlobs);
    }

    /**
     * trash
     * @param blob
     * @param addr
     */
    public void PaintBlob(Blob blob, String addr) {
        BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) imgOut.createGraphics();
        // background
        g.setColor(Color.magenta);
        g.fillRect(0, 0, w, h);

        g.setColor(Color.BLACK);
        g.setBackground(Color.BLACK);
        g.setStroke(new BasicStroke(3.0f));

        // draw background
        g.drawImage(img, null, w, h);

        // draw blob
        g.setColor(Color.BLUE);
        for (int i=0; i<blobs.size(); i++) {
            int x = blobs.get(0).pts.get(i).x;
            int y = blobs.get(0).pts.get(i).y;
            imgOut.setRGB(x, y, new Color(255,255,0).getRGB());
            g.drawRect(x, y, 1, 1);
        }

        g.drawString("hello, world!", 20, 20);
        ImageUtils.paintImage(imgOut, addr);
    }

    @Override
    public int getWidth(ImageObserver observer) {
        return w;
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return h;
    }

    @Override
    public ImageProducer getSource() {
        return img.getSource();
    }

    @Override
    public Graphics getGraphics() {
        return img.getGraphics();
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return img.getProperty(name, observer);
    }

    public BufferedImage getImageOut() {
        BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) imgOut.createGraphics();
//        // background
//        g.setColor(Color.magenta);
//        g.fillRect(0, 0, w, h);

        g.setColor(Color.BLACK);
        g.setBackground(Color.BLACK);
        g.setStroke(new BasicStroke(3.0f));

//        // draw background
//        g.drawImage(img, null, w, h);

        for (int r=0; r<blobs.size(); r++) {
            // System.out.println("blobs: "+ blobs.size() +" doing no. "+ r);
            g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
            for (int i=0; i<blobs.get(r).pts.size(); i++) {
                int x = blobs.get(r).pts.get(i).x;
                int y = blobs.get(r).pts.get(i).y;
                g.drawRect(x, y, 1, 1);

//                System.out.println("draw blob: "+ x +" "+ y);
            }

        }

        return imgOut;
    }

    public void paintImage(String addr) {
        ImageUtils.paintImage(getImageOut(), addr);
    }

}
