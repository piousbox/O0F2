/**
 * 20100708 2:16pm Going to change run() s.t. it paints on correct nwPoints.
 */
package com.piousbox.graphics;

import com.piousbox.ImageUtils;
import com.piousbox.MatrixUtils;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

/**
 *
 * @author ae1
 */
class RunnableFragmenter extends Observable implements Runnable {

    Config config;
    ImageLibrary lib;
    BufferedImage[] partitions;
    Point[] nwPoints;
    Thread T;
    Frag frag;
    ImagePanel imagePanel;
    Frag[] frags;

    public RunnableFragmenter(Config config, ImagePanel imagePanel, ImageFrame frame, ImageLibrary lib, BufferedImage[] partitions) {
        this.config = config;
        this.lib = lib;
        this.partitions = partitions;
        this.imagePanel = imagePanel;
        this.nwPoints = Fragmenter.imageNwPoints(null, config.getSourceImageFragsPerWidth());

        T = new Thread(this, "Runnable Fragmenter");
        T.start();

        this.addObserver(frame);
    }

    public RunnableFragmenter(Config config, ImagePanel imagePanel, ImageFrame frame, ImageLibrary lib, Frag[] frags) {
        this.config = config;
        this.lib = lib;
        this.imagePanel = imagePanel;
        this.frags = frags;

        partitions = new BufferedImage[frags.length];

        nwPoints = new Point[frags.length];
        for (int i = 0; i < frags.length; i++) {
            partitions[i] = frags[i].getSubImg();
            nwPoints[i] = frags[i].getNwPoint();
        }

        this.addObserver(frame);

        T = new Thread(this, "Runnable Fragmenter");
        T.start();


    }

    public void run() {
        config.log("runnableFragmenter running.");

        BufferedImage bestSlice;
        int minDifference, difference;
        Point northWest = null;

        int pWidth = imagePanel.getImage().getWidth() / config.getSourceImageFragsPerWidth();
        int pHeight = pWidth;
        if (pWidth != pHeight) {
            throw new UnsupportedOperationException("partitions are square for now: " + pWidth + " " + pHeight);
        }

        ArrayList<File> isUsed = new ArrayList<File>(frags.length);
        for (int i = 0; i < frags.length; i++) {
            bestSlice = null;
            minDifference = config.infinity;
            lib.setCursor(0);

            int[] in1 = Fragmenter.getComparisonMask(frags[i].getSubImg());
            int[] in2 = null;

            File thisSlice = null;
            while (lib.hasNext()) {
                in2 = lib.getComparisonMask();
                if (!isUsed.contains(lib.thisSlice())) { // not used.

                    difference = Fragmenter.compare(in1, in2);
//                config.log("difference: "+ difference);
                    if (difference < minDifference) {
                        minDifference = difference;
                        thisSlice = lib.thisSlice();
                        bestSlice = ImageUtils.loadBufferedImage(thisSlice);
                        northWest = frags[i].getNwPoint();

//                    setChanged();
//                    notifyObservers(new Frag(bestSlice, northWest));
                    }
                }
            }

            if (bestSlice != null) {
                isUsed.add(thisSlice);
                bestSlice = ImageUtils.scale(bestSlice, pWidth, pHeight);

//                int w = bestSlice.getWidth();
//                int h = bestSlice.getHeight();
//                int xIdx = i % config.getFragsPerWidth();
//                int yIdx = (int) Math.floor((double) i / (double) config.getFragsPerWidth());
//                Point northWest = new Point(w * xIdx, h * yIdx);

                config.log("draw partition (difference) thisSlice: " + northWest.toString() + "(" + minDifference + ") " + thisSlice);
//                imagePanel.draw(bestSlice, northWest);

                setChanged();
                notifyObservers(new Frag(bestSlice, northWest));
            }
        }

        config.log("runnableFragmenter done.");
    }
}
