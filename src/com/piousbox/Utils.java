/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox;

import com.piousbox.graphics.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.piousbox.graphics.Frag;

/**
 *
 * @author ae1
 */
class Utils {

    static int average(int[] in) {
        int out = 0;
        for (int i = 0; i < in.length; i++) {
            out += in[i];
        }
        return (int) ((double) out / (double) in.length);
    }

    /**
     * computes difference of two arrays of PIXELS!
     * @param in1
     * @param in2
     * @return
     */
    static int computePixelDifference(int[] in1, int[] in2) {
        if (in1.length != in2.length) {
            throw new UnsupportedOperationException("Dimension Mismatch: " + in1.length + " and " + in2.length);
        }

        int out = 0;
        for (int i = 0; i < in1.length; i++) {
            Color c = new Color(in1[i]);
            Color cc = new Color(in2[i]);
            int d = c.getBlue() + c.getRed() + c.getGreen();
            int dd = cc.getBlue() + cc.getRed() + cc.getGreen();
            out += Math.abs(d - dd);
        }
        return out;
    }

    /**
     * Saves an object.
     */
    public static void saveObject(Serializable object, String filename) {
        ObjectOutputStream objstream = null;
        try {
            objstream = new ObjectOutputStream(new FileOutputStream(filename));
            objstream.writeObject(object);
            objstream.close();
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objstream.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Loads an object.
     */
    public static Object loadObject(String filename) {
        try {
            ObjectInputStream objstream = null;
            Object object = objstream.readObject();
            objstream = new ObjectInputStream(new FileInputStream(filename));
            objstream.close();
            return object;
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Find the greatest in array of doubles. Returns the index of that greatest.
     * @param in
     * @return
     */
    static int findGreatestInArray(double[] in) {
        int idx = 0;
        for (int i = 0; i < in.length; i++) {
            idx = (in[i] > in[idx]) ? i : idx;
        }
        return idx;
    }

    static int[] normalize(int[] in, int min, int max) {
        for (int i = 0; i < in.length; i++) {
            in[i] = 255 * (in[i] - min) / (max - min);
//            System.out.println((in[i] - min));
//            System.out.println("max-min: "+ (max-min));
        }
        return in;
    }

    public static String print(int[] in) {
        String out = "";
        for (int i = 0; i < in.length; i++) {
            out += in[i] + " ";
        }

        return out;
    }

    /**
     * Prints a long array in like a box for width w. Parameter h is potional.
     * @param in
     * @param w
     * @param h
     * @return
     */
    public static String print(int[] in, int w, int h) {
        return print(in, w);
    }

    /**
     * Prints a long array in like a box for width w.
     * @param in
     * @param w
     * @return
     */
    public static String print(int[] in, int w) {
        String out = "";

        for (int i = 0; i < in.length; i++) {
            out += in[i] + "\t";
            if ((i + 1) % w == 0) {
                out += "\n";
            }
        }

        return out;
    }

    /**
     * Take a directory and return only image files in this directory (non-recursively).
     * tested.
     * @param libDir
     * @return
     */
    public static File[] imgFilesInDir(String libDir) {
        File startDir = new File(libDir);
        File[] listOfFiles = startDir.listFiles();
        ArrayList<File> out = new ArrayList<File>(listOfFiles.length);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (isImage(listOfFiles[i])) {
                out.add(listOfFiles[i]);
//                System.out.println(listOfFiles[i].getPath());
            }
        }

        File[] outArr = new File[out.size()];
        return out.toArray(outArr);
    }

    /**
     * sees if the file is a file and an image (jpg, gif for png).
     * tested.
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        if (file.isFile()) {
            String fileName = file.getName().toLowerCase();
            if (fileName.endsWith("png") || fileName.endsWith("gif") || fileName.endsWith("jpg")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * Computes frag size based on img width and how many frags there're per width (8 is reasonable).
     * Tested. Done.
     * @param width
     * @param fragsPerWidth
     * @return
     */
    public static int fragSize(int width, int fragsPerWidth) {
        return (int) Math.floor((double) width / (double) fragsPerWidth);
    }

    public static Frag[] img2frags(BufferedImage img, String filename) {
        return img2frags(img, filename, 8);
    }

    /**
     * Take an image and return the array of fragments.
     *
     * @param file
     * @return
     */
    public static Frag[] img2frags(BufferedImage img, String filename, int fragsPerWidth) { /* 1/2 ok */
        int imgW = img.getWidth();
        int imgH = img.getHeight();

        int fragSize = fragSize(img.getWidth(), fragsPerWidth);

        int nFragsW = nFrags(imgW, fragSize);
        int nFragsH = nFrags(imgH, fragSize);

        Frag[] _frags = new Frag[nFragsW * nFragsH];

        int counter = 0;
        for (int fragXIdx = 0; fragXIdx < nFragsW; fragXIdx++) {
            for (int fragYIdx = 0; fragYIdx < nFragsH; fragYIdx++) {
                int fragX = fragXIdx * fragSize;
                int fragY = fragYIdx * fragSize;
                BufferedImage subImg = img.getSubimage(fragX, fragY, fragSize, fragSize);
                _frags[counter++] = new Frag(subImg, filename, fragX, fragY);
            }
        }

        return _frags;
    }

    /**
     * Returns number of imgFrags of this fragSize that will fit in this image width (imgW).
     * Tested, done.
     * @param imgW
     * @param fragSize
     * @return
     */
    public static int nFrags(int imgW, int fragSize) {
        return fragSize(imgW, fragSize);
    }

    /**
     * Put in two masks and take out how different they are.
     * @param mask1
     * @param mask2
     * @return
     */
    public static double compareMasks(int[] mask1, int[] mask2) {
        int diff = 0;

        for (int i = 0; i < mask1.length; i++) {
            int tempDiff = Math.abs(mask1[i] - mask2[i]);
            diff += tempDiff;
        }

        return (double) diff / (double) mask1.length;
    }

    public static double computeDifference(Frag f1, Frag f2) {
        checkDimensions(f1, f2);
        int[] intArr1 = f1.getMaskIntArr();
        int[] intArr2 = f2.getMaskIntArr();

        checkDimensions(f1, f2);

        return Utils.computePixelDifference(intArr1, intArr2);
    }

    public static void checkDimensions(Frag f1, Frag f2) {
        if (f1.getMaskW() != f2.getMaskW() || f1.getMaskH() != f2.getMaskH()) {
            throw new UnsupportedOperationException("dimension mismatch in compasiron masks: " + f1.getMaskW() + " and " + f2.getMaskW() + " -OR- " + f1.getMaskH() + " and " + f2.getMaskH());
        }
    }

}




