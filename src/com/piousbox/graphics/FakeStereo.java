/*
 * @TODO: it colorizes, whereas it shouldn't colorize.
 * 
 */
package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;

/**
 *
 * @author ae1
 */
public class FakeStereo {

    private void processFolder() {
        /**
         * Collect files in the input folder.
         * Create new folder, ./fakeStereo-out
         * for each file, churn it and spit it out.
         */
        int counter = 0;
        String outDir = c.inDir + "fakeStereo-out/";
        (new File(outDir)).mkdir();
        File[] files = IOUtils.imagesInDir(c.inDir, false);
        for (File file : files) {
            in = ImageUtils.loadBufferedImage(file);

            out = fakeStereoDoProcess(c, in);
            // this must be TYPE_INT_RGB b/c rgb[2|3].length==3 != 4

            ImageUtils.saveImage(out, outDir + counter++ + c.outImgFormat);
        }
    }
    Config c = new Config();
    BufferedImage in, out;
//    Graphics2D g2d;

    FakeStereo() {
//        processFolder();
        fileToVideo(c.getFileIn());

    }

    public static void main(String[] args) {
        new FakeStereo();
    }

    private BufferedImage fakeStereoDoProcess(Config c, BufferedImage in) {

        int w, h;
        w = in.getWidth();
        h = in.getHeight();

        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

//        g2d = out.createGraphics();

        WritableRaster wr = out.getRaster();

        int[][][] array = new int[w][h][3]; // only 3 color channels

        //
        // left-hand side
        //
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                // this pixel of source
                int rgb = in.getRGB(x, y);
                int r = (rgb & 0x00ff0000) >> 16;
                int g = (rgb & 0x0000ff00) >> 8;
                int b = rgb & 0x000000ff;


                int r2 = r - (255 - c.fakeStereoMask[0]);
                int g2 = g - (255 - c.fakeStereoMask[1]);
                int b2 = b - (255 - c.fakeStereoMask[2]);

                if (r2 < 0) {
                    r2 = 0;
                }
                if (g2 < 0) {
                    g2 = 0;
                }
                if (b2 < 0) {
                    b2 = 0;
                }

                int[] rgb2 = {r2, g2, b2};

                int newX = (x - fakeStereoOffset) >= 0 ? x - fakeStereoOffset : 0;
//                wr.setPixel(newX, y, rgb2);
                array[newX][y] = rgb2;
            }
        }

        //
        // right-hand side
        //
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
// this pixel of source
                int rgb = in.getRGB(x, y);
                int r = (rgb & 0x00ff0000) >> 16;
                int g = (rgb & 0x0000ff00) >> 8;
                int b = rgb & 0x000000ff;

                int r3 = r - (c.fakeStereoMask[0]);
                int g3 = g - (c.fakeStereoMask[1]);
                int b3 = b - (c.fakeStereoMask[2]);

                if (r3 < 0) {
                    r3 = 0;
                }
                if (g3 < 128) {
                    g3 = 128;
                }
//                if (b3<c.mask[0]) b3 = 255-c.mask[0];

                int newX = (x + fakeStereoOffset) < w ? x + fakeStereoOffset : w - 1;
//                wr.setPixel(newX, y, rgb3);

                int[] rgb3 = {r3 + array[newX][y][0],
                    g3 + array[newX][y][1],
                    b3 + array[newX][y][2]};

                array[newX][y] = rgb3;

            }
        }

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                wr.setPixel(x, y, array[x][y]);
            }
        }
        return out;

    }

    int fakeStereoOffset = c.fakeStereoOffsetInit;
    
    private void fileToVideo(File fileIn) {
        /**
         * Collect files in the input folder.
         * Create new folder, ./fakeStereo-out
         * for each file, churn it and spit it out.
         */
//        c.log("this file's in "+ fileIn.getParent());
        String outDir = fileIn.getParent() + "/fakeStereo-outSeq0/";
        (new File(outDir)).mkdir();
        in = ImageUtils.loadBufferedImage(fileIn);

        for (int i = 0; i < c.fakeStereoNFrames; i++) {
            fakeStereoOffset += c.rnd.nextInt(6) - 3;
            if (fakeStereoOffset <= 0) {
                fakeStereoOffset = c.fakeStereoOffsetInit;
            }

            out = fakeStereoDoProcess(c, in);
            // this must be TYPE_INT_RGB b/c rgb[2|3].length==3 != 4

            ImageUtils.saveImage(out, outDir.toString()+ "/" + i + c.outImgFormat);
        }
    }
}
