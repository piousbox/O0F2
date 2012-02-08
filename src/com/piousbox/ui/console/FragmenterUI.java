/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.ui.console;

import com.piousbox.ImageUtils;
import com.piousbox.MatrixUtils;
import com.piousbox.graphics.Fragmenter;
import com.piousbox.graphics.ImageLibrary;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author ae1
 */
public class FragmenterUI {

    static boolean debug = true;

    public static void main(String[] args) {
        /**
         * [] take an image and split it into fragments.
         * [] load a library.
         * [] for each fragment on an image,
         * [] [] look at each fragment in the library and match the best one.
         * [] [] paint the best fragment.
         * [] save image.
         */
        if (debug) {
            String[] args2 = {"/home/ae1/1.jpg", "/var/www/data/images/", "8", "/home/ae1/2_out.png"};
            args = args2;
        } else if (args.length == 0) {
            printUsage();
        }

        new FragmenterUI(args);
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar FragmenterUI.java (?) srcImg.jpg /home/ae1/libdir/ num_slices_per_width save_as.jpg");
        System.exit(0);
    }

    private FragmenterUI(String[] args) {
        BufferedImage srcImg = ImageUtils.loadBufferedImage(args[0]);
        ImageLibrary lib = new ImageLibrary(args[1]);

        int fragsPerWidth = Integer.parseInt(args[2]);
        BufferedImage[] fragments = Fragmenter.imageToPartitions(srcImg, fragsPerWidth);

        BufferedImage bestSlice;
        double maxSimilarity, similarity;

        BufferedImage out = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = out.getGraphics();

        for (int i = 0; i < fragments.length; i++) {
            // init
            maxSimilarity = 0.0;
            lib.setCursor(0);
            bestSlice = null;

            int[] mask = Fragmenter.getComparisonMask(fragments[i]);
            int pWidth = fragments[i].getWidth();
            int pHeight = fragments[i].getHeight();

            while (lib.hasNext()) {
                int[] mask2 = Fragmenter.getComparisonMask(ImageUtils.loadBufferedImage(lib.next()));
                similarity = MatrixUtils.similarity(mask, mask2);
                if (similarity > maxSimilarity) {
                    maxSimilarity = similarity;
                    bestSlice = ImageUtils.loadBufferedImage(lib.thisSlice());
                }
            }

            bestSlice = ImageUtils.scale(bestSlice, pWidth, pHeight);

            int w = bestSlice.getWidth();
            int h = bestSlice.getHeight();
            int xIdx = i % fragsPerWidth;
            int yIdx = (int) Math.floor((double) i / (double) fragsPerWidth);
            Point northWest = new Point(w * xIdx, h * yIdx);

            g.drawImage(bestSlice, northWest.x, northWest.y, null);

        }
        
        ImageUtils.saveImage(out, args[3]);
    }
}
