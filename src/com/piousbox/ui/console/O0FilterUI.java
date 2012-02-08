package com.piousbox.ui.console;

/**
 * At 20091012.18:42 it works. Now I'm going to wrap a main() around O0FilterUI().
 * @author Victor Pudeyev <piousbox@gmail.com>
 */

import com.piousbox.ImageUtils;
import com.piousbox.graphics.O0Filter;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author ae1
 */
public class O0FilterUI {

    /**
     * This is deprecated to be used with O0FilterUI();
     */
    String addr1;
    /**
     * This is deprecated to be used with O0FilterUI();
     */
    String addr2;

    /**
     * Global parameters; these are passed as args[] to constructor.
     */
    static int p = 180; // padding, also size of convolution mask
    /**
     * Global parameters; these are passed as args[] to constructor.
     */
    static int thresh = 10; // threshold
    /**
     * Global parameters; these are passed as args[] to constructor.
     */
    static int nBlobs = 200; // how many random blobs to look for?

    /**
     * Main entry point for the program; call this function once to process one image.
     * @param addr1
     * @param p
     * @param thresh
     * @param nBlobs
     */
    public O0FilterUI(String addr1, int p, int thresh, int nBlobs) {
        // input image
        // blur
        BufferedImage img = ImageUtils.loadBufferedImage(addr1);
        // cluster image
        O0Filter img2 = new O0Filter(img, p, thresh, nBlobs);
        img2.paintImage(addr2);
        // save
        // ImgUtils.paintImage(img, addr2);
        
    }
    
    /**
     * The o0-filer.
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 4)
            printusage();

        String startDir = args[0];
        File folder = new File(startDir);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                // System.out.println(startDir+listOfFiles[i].getName());
                // System.exit(0);

                new File(startDir+"/"+"o0-out").mkdir();
                String[] vp_args = {startDir+"/"+listOfFiles[i].getName(), 
                    startDir+"/"+"o0-out"+"/"+listOfFiles[i].getName(),
                    args[1],
                    args[2],
                    args[3]};
                new O0FilterUI(vp_args);
            } else if (listOfFiles[i].isDirectory()) {
                // System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }

    /**
     * What arguments does it take?
     * @param args args(imgInAddr, imgOutAddr, p, thresh, nBlobs);
     */
    private O0FilterUI(String[] args) {
        // input image
        // blur
        BufferedImage img = ImageUtils.loadBufferedImage(args[0]);
        // cluster image
        O0Filter img2 = null;
        if (args.length==2) {
            img2 = new O0Filter(img, p, thresh, nBlobs); // I changed this. 2009102.18:34
        } else if (args.length==5) {
            img2 = new O0Filter(img, Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        } else {
            printusage();
        }
        img2.paintImage(args[1]);
        // save
        // ImgUtils.paintImage(img, addr2);
    }

    private static void printusage() {
        System.out.println("Usage: java -Xmx1024m -jar o0-filter.jar inputFolder 80 10 50\n" +
                "Where 80 stands for the size of convolution mask (the size of the square),\n" +
                "10 stands for threshold (decrease to get more detail)\n" +
                "and 50 stands for the number of blobs (increase to get denser image).\n" +
                "So not put slash at the end of the inputFolder string.\n" +
                "-Xmx1024m is the flag setting memory heap size to 1Gb. If you don't do that, you cannot process even a moderately-large image.");
        System.exit(0);
    }

}
