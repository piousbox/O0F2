/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ae1
 */
public class Fragmenter {

    static String getMaskAddr(File thisFile) {
        return thisFile.getParent() + "/_a_" + thisFile.getName() + "_mask16x16";
    }

    public static Point[] imageNwPoints(BufferedImage image, int fragsPerWidth) {
        int fragSize = image.getWidth() / fragsPerWidth;
        int fragsPerHeight = image.getHeight() / fragSize;

        int counter = 0;
        Point[] out = new Point[fragsPerHeight * fragsPerWidth];
        for (int xIdx = 0; xIdx < fragsPerWidth; xIdx++) {
            for (int yIdx = 0; yIdx < fragsPerHeight; yIdx++) {
                out[counter++] = new Point(xIdx * fragSize, yIdx * fragSize);
            }
        }

        return out;
    }
    Config config = null;
    Random rnd = new Random();

    public static double compare(BufferedImage bim1, BufferedImage bim2) {
        int[] mask1 = getComparisonMask(bim1);
        int[] mask2 = getComparisonMask(bim2);
        return compare(mask1, mask2);
    }

    /**
     * The greater the value, the more different they are. The smaller the value, the less similar.
     * @param mask1
     * @param mask2
     * @return
     */
    static int compare(int[] mask1, int[] mask2) {
        assert mask1.length == mask2.length : "mask sizes should be the same";

        int totalDiff = 0;
        for (int i = 0; i < mask1.length; i++) {
            int tmp = ImageUtils.diff(mask1[i], mask2[i]);
            totalDiff += Math.abs(tmp);
        }

//        totalDiff = (totalDiff == 0) ? 1 : totalDiff; // Avoid division by zero.

        return totalDiff;
    }

    /**
     
     * Variate-size buffered image partition!!!!!!!!!!
     * width = 16
     * height = whatever to match 16.
     * @param in
     * @return
     */
//    public static int[] comparisonMask16x16_1(BufferedImage in) {
//        in = ImageUtils.scale(in, 16, 16);
//        in = ImageUtils.toGrayscale(in);
//        int[] out = ImageUtils.toIntArr(in);
////        out = ImageUtils.normalize(out, 0, 1024);
//        return out;
//    }

    /**
     * 16x16
     * @param in
     * @return
     */
    public static int[] getComparisonMask(BufferedImage in) {
        in = ImageUtils.scale(in, Config.getMaskSize(), Config.getMaskSize());
        in = ImageUtils.toGrayscale(in);
        int[] out = ImageUtils.toIntArr(in);
        return out;
    }

    

    public Fragmenter() {
        this(new Config());
    }

    public Fragmenter(Config config) {
        this.config = config;
    }

    public Fragmenter(Config config, String dirAddr) {
        config.setFragmenterDir(dirAddr);
        this.config = config;
    }

    public void fragmentFolder(File file) {

        // collect all images in this folder.
        File[] images = IOUtils.imagesInDir(file, false);
        config.log("Processing frag lib " + file + ", nImages " + images.length);

        // for each image
        for (int i = 0; i < images.length; i++) {
            //check if this image has been processed
            if (!isProcessed(images[i])) {
                config.log("Processing new file: " + images[i]);
                fragmentImageFile(images[i]);
                setProcessed(images[i]);
            }
        }

        config.log("Done processing");
    }

    public void fragmentFolder() {
        File whichFolder = new File(config.getFragmenterDir());
        fragmentFolder(whichFolder);
    }

    /**
     * I think, return partitions of arbitrary size & aspect ratio
     * @param thisImage image to partition
     * @param width width of partition
     * @param height height of partition
     * @return
     */
    public static BufferedImage[] fragmentImage(BufferedImage thisImage, int width, int height) {
        int fragsPerWidth = thisImage.getWidth() / width;
        int fragsPerHeight = thisImage.getHeight() / height;
        int nFrags = fragsPerWidth * fragsPerHeight;

        BufferedImage[] out = new BufferedImage[nFrags];

        int counter = 0;
        for (int xIdx = 0; xIdx < fragsPerWidth; xIdx++) {
            for (int yIdx = 0; yIdx < fragsPerHeight; yIdx++) {
                out[counter++] = ImageUtils.crop(thisImage, xIdx * width, yIdx * height, width, height);
            }
        }
        return out;
    }

    /**
     * Returns square frags of the image. This is the workhorse.
     * @param img the image to fragment.
     * @return square frags of the image, order like in reading English, increasing x increasing y.
     */
    public static BufferedImage[] imageToPartitions(BufferedImage img, int fragsPerWidth) {
        int fragSize = fragSize(img, fragsPerWidth);
        int fragsPerHeight = fragsPerHeight(img, fragSize);
        int nFrags = fragsPerWidth * fragsPerHeight;

        BufferedImage[] out = new BufferedImage[nFrags];

        int counter = 0;
        for (int xIdx = 0; xIdx < fragsPerWidth; xIdx++) {
            for (int yIdx = 0; yIdx < fragsPerHeight; yIdx++) {
                out[counter++] = ImageUtils.crop(img, xIdx * fragSize, yIdx * fragSize, fragSize, fragSize);
            }
        }
        return out;
    }

    /**
     * gives out random frags, length nRndPartitions.
     * @param image
     * @param fragsPerWidth
     * @param nRndPartitions how many random frags to give you.
     * @return
     */
    public static Frag[] imageToFrags(BufferedImage image, int fragsPerWidth, int nRndPartitions) {
        BufferedImage[] bim1 = imageToPartitions(image, fragsPerWidth);
        Point[] nwPoints = imageNwPoints(image, fragsPerWidth);

//        Frag[] out = new Frag[nRndPartitions];
//
////        for (int i=0; i<bim1.length; i++) {
////            out[i] = new Frag(bim1[i], nwPoints[1]);
////        }
//
//        Random rnd = new Random();
//        int[] listOfPoints = new int[nRndPartitions];
//
//        int counter = 0;
//        while (counter < nRndPartitions) {
//            int i = rnd.nextInt(bim1.length);
//            if (Arrays.binarySearch(listOfPoints, i) < 0) {
//                listOfPoints[counter] = i;
//                out[counter] = new Frag(bim1[i], nwPoints[i]);
//                counter++;
//            }
//        }

        Frag[] out = new Frag[bim1.length];
        for(int i=0; i<bim1.length; i++) {
            out[i] = new Frag(bim1[i], nwPoints[i]);
        }

        return out;
    }

    private String sliceFilename(File srcImg, int count) {
        String out = srcImg.getParent() + "/";
        out += "slices/";
        out += IOUtils.fileNameWithoutExtention(srcImg);
        out += "_" + count + ".";
        out += IOUtils.fileNameExtention(srcImg);
        return out;
    }

    /**
     * This extracts random tiles from an image and puts them back in, in random places.
     * @param config
     */
    public BufferedImage shuffleRandomBits(BufferedImage img) {
        int shuffleBitSize = config.getShuffleBitSize();
//        int shuffleBitSize = 50;

        for (int i = 0; i < config.getNRandomBits(); i++) {
            int rndX = rnd.nextInt(img.getWidth());
            int rndY = rnd.nextInt(img.getHeight());
            int rndZ = rnd.nextInt(img.getWidth() - shuffleBitSize);
            int rndW = rnd.nextInt(img.getHeight() - shuffleBitSize);

            BufferedImage crop = ImageUtils.crop(img, rndX, rndY, shuffleBitSize, shuffleBitSize);
            Graphics2D g2d = (Graphics2D) img.getGraphics();
            g2d.drawImage(crop, rndZ, rndW, null);
        }


        return img;

    }

    /**
     * Assumes that it's been tested that this image file hasn't been processed.
     * @param imgFile
     */
    public void fragmentImageFile(File imgFile) {
        // slice up the image.
        BufferedImage[] slices = imageToPartitions(ImageUtils.loadBufferedImage(imgFile), config.getFragsPerWidth());

        // save 'em
        for (int i = 0; i < slices.length; i++) {
            ImageUtils.save(slices[i], sliceFilename(imgFile, i));
        }

        // mark this file as processed.
        setProcessed(imgFile);
    }

    private void setProcessed(File in) {
        try {
            File mark = imagesInfoFile(in);
            mark.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Fragmenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks if this algorithm has been applied to this image yet.
     * @param image
     * @return
     */
    private boolean isProcessed(File image) {
        File dir = imagesInfoFile(image);
        return dir.exists();
    }

    public static void main(String[] args) {
//        File srcImg = new File("/var/www/test/20100618/blah.jpg");
//        int count = 0;
//        String out = srcImg.getParent() + "/";
//        out += "slices/";
//        out += IOUtils.fileNameWithoutExtention(srcImg);
//        out += "_" + count + ".";
//        out += IOUtils.fileNameExtention(srcImg);
//        System.out.println(out);

        File imagesInfoFile = imagesInfoFile(new File("/var/www/test/20100618/blah.jpg"));
    }

    private static File imagesInfoFile(File image) {
        String parentDirAddr = image.getParent();
        String sliceDirName = parentDirAddr + "/";
        sliceDirName += Config.getFragmenterSliceDirName();
//        sliceDirName += config.getSlicesDirName();
        File sliceDirNameFile = new File(sliceDirName);
        if (!sliceDirNameFile.exists()) {
            sliceDirNameFile.mkdir();
        }
        return new File(sliceDirName + image.getName() + ".txt");
    }

    public static int fragsPerHeight(BufferedImage img, int fragSize) {
        int height = img.getHeight();
        int fragsPerHeight = height / fragSize;
        return fragsPerHeight;
    }

    public static int fragSize(BufferedImage img, int fragsPerWidth) {
        int out = img.getWidth() / fragsPerWidth;
        return out;
    }

    /**
     * Just deletes the slices directory.
     */
    public void clearSlices() {
        File slicesDir = new File(config.getLibIn() + "/slices/");
        if (slicesDir.exists()) {
            slicesDir.delete();
        }
    }
}
