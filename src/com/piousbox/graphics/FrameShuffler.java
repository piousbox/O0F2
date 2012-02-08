package com.piousbox.graphics;

import com.piousbox.IOUtils;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author piousbox
 */
public final class FrameShuffler {

    int padding;
    Frame[] frames;
    Random rnd = new Random();

    /* config */
//    String inPrefix = "/home/ae1/archive/2010/data/video/lucia_100/image";
    String inPrefix = "/home/ae1/archive/2010/data/video/lucia_shuffle_out3/frame";
    String inSuffix = ".jpg";
    int nTimesToShuffle = 10;
    int nFrames = 1143;
    String prefix = "/home/ae1/archive/2010/data/video/lucia_shuffle_out4/frame";
    String suffix = ".jpg";

    public FrameShuffler() {
        padding = computePadding(nFrames);

        // 1. collect all the frames in an array of frames.
        frames = collectFrames(inPrefix, inSuffix, nFrames);

        // 2. shuffle several times
        for (int i = 0; i < nTimesToShuffle; i++) {
            int splitPoint = rnd.nextInt(nFrames - 2) + 1;
            Frame[] tempFrames = shuffle(frames, splitPoint);
            frames = tempFrames.clone();
            System.out.println("split at "+ splitPoint +", 5th frame is "+ frames[4].getIn());
        }

//        for (int i = 0; i < nFrames; i++) {
//            System.out.println(frames[i].getIn());
//        }

//        // 2a. Shuffle and reverse several times
//        for (int i = 0; i < nTimesToShuffle; i++) {
//            int counter = 0;
//            int splitPoint = rnd.nextInt(nFrames - 1);
//
//            while (splitPoint > 0) {
//                tempFrames[counter++] = frames[splitPoint--];
//            }
//
//            splitPoint = nFrames - 1;
//            while (counter < nFrames) {
//                tempFrames[counter++] = frames[splitPoint--];
//            }
//            frames = tempFrames;
//        }

        // 3. save the images.
        saveFrames(frames, prefix, suffix);

    }

    public static void main(String[] args) {

        new FrameShuffler();
    }

    /**
     * returns the order of the input. for 1, returns 1. for 10, returns 2.
     * Tested, works.
     * @param i integer, the order of which is to be calculated
     * @return the order
     */
    static int computePadding(int i) {
        int padding = 1;
        while (i >= Math.pow(10, padding)) {
            padding++;
        }

        return padding;
    }

    /**
     * tested, works.
     * @param nFrames
     * @param thisFrame
     * @return
     */
    static String computePaddingZeroes(int nFrames, int thisFrame) {
        String out = "";
        int padding = computePadding(nFrames);
        int notPadding = computePadding(thisFrame);
        int realPadding = padding - notPadding;
        for (int i = 0; i < realPadding; i++) {
            out += "0";
        }
        return out;
    }

    /**
     * Tested, works.
     * @param inPrefix
     * @param inSuffix
     * @param nFrames
     * @return
     */
    public static Frame[] collectFrames(String inPrefix, String inSuffix, int nFrames) {
        Frame[] frames = new Frame[nFrames];
        for (int i = 1; i <= nFrames; i++) {
            String in = inPrefix;
            in += computePaddingZeroes(nFrames, i);
            in += i;
            in += inSuffix;
            frames[i - 1] = new Frame(in);
        }

        return frames;
    }

    /**
     * tested, works
     * @param frames
     * @param splitPoint
     * @return
     */
    public static Frame[] shuffle(Frame[] frames, int splitPoint) {
        int nFrames = frames.length;
        Frame[] out = new Frame[nFrames];
        int counter = 0;
        int cc = 0;

        while (splitPoint < frames.length) {
            out[counter++] = new Frame(frames[splitPoint++].getIn());
        }

        while (counter < nFrames) {
            out[counter++] = new Frame(frames[cc++].getIn());
        }

        return out;
    }

    void saveFrames(Frame[] frames, String prefix, String suffix) {
        int n = frames.length;
        for (int i = 1; i <= n; i++) {
            String outAddr = prefix;
            String zeroes = computePaddingZeroes(n, i);
            outAddr += zeroes;
            outAddr += i;
            outAddr += suffix;

            try {
                String inAddr = frames[i - 1].getIn();
                IOUtils.copyFile(inAddr, outAddr);
//                System.out.println("copy "+ inAddr +" to "+ outAddr);
            } catch (Exception ex) {
                Logger.getLogger(FrameShuffler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
