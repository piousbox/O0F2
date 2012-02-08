/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author ae1
 */
public class ImageUtils extends Frame {

    /**
     * 
     * @param r int representation of a pixel, 8 bit -16777216 look alike
     * @return 0-255, luminocity
     */
    public static int getLuminocity(int r) {
        return getLuminocity(new Color(r));
    }

    public static int getLuminocity(Color c) {
        int b = c.getBlue() + c.getRed() + c.getBlue();

        b /= 3;

        return b;
    }

    /**
     * Take this int[], w, h, and paint it as an image.
     * @param i
     * @param w
     * @param h
     * @return
     */
    public static BufferedImage intArrToImg(int[] in, int w, int h) {
        BufferedImage bim = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        System.out.println(in[10]);

        return bim;
    }

    public static boolean isImage(File file) {
        String ext = file.getName();
        ext = ext.substring(ext.lastIndexOf(".") + 1, ext.length());
        ext = ext.toLowerCase();

//        System.out.println(ext);
        if (ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {
            return true;
        }
        return false;
    }

    public static BufferedImage loadBufferedImage(File in) {
        BufferedImage bim = null;
        try {
            bim = ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bim;
    }

    /**
     * @deprecated use scale
     * Take a bufferedImage and return the same, but each pixel replicated magnify number of times in x, same in y.
     * @param in
     * @param magnify
     * @return
     */
    public static BufferedImage magnify(BufferedImage in, int magnify) {

        BufferedImage out = new BufferedImage(in.getWidth() * magnify, in.getHeight() * magnify, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < in.getWidth(); x++) {
            String outStr = "";
            for (int y = 0; y < in.getHeight(); y++) {
                outStr += computeGrayValue(in.getRGB(x, y)) + " ";
                for (int i = 0; i < magnify; i++) {
                    for (int j = 0; j < magnify; j++) {
                        out.setRGB(magnify * x + i, magnify * y + j, in.getRGB(x, y));
                    }
                }
            }
            // System.out.println(outStr);
        }
        return out;
    }

    public static int computeGrayValue(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return (red + green + blue) / 3;
    }

    /**
     * In the array, sets maximum black to 0 and maximum white to 255.
     * That is the same is maxing out contrast.
     * @param fragMask
     * @return
     */
    public static int[][] normalize(int[][] in, int min, int max) {
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                int val = in[i][j];
                in[i][j] = (int) (255 * ((double) val - (double) min) / ((double) max - (double) min));
            }
        }
        return in;
    }

    public static int[] normalize(int[] in, int min, int max) {
        for (int i = 0; i < in.length; i++) {
            int val = in[i];
            in[i] = (int) (255 * ((double) val - (double) min) / ((double) max - (double) min));
        }
        return in;
    }

    public static BufferedImage arrayToImage(int[][] in) {
        BufferedImage outImg = new BufferedImage(in.length, in[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                int val = in[i][j];
                Color color = new Color(val, val, val);
                outImg.setRGB(i, j, color.getRGB());
            }
        }
        return outImg;
    }

    public static void saveImage(BufferedImage out, String addr) {
        String ext = addr.substring(addr.length() - 3);
        assert (!ext.toLowerCase().equals("jpg")) : "Let's not use .jpg for now, ok?";

        try {
            ImageIO.write(out, ext, new File(addr));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        System.out.println("saved "+addr);
    }

    /**
     * @deprecated use saveImage(BufferedImage, String)
     * @param outImg
     * @param fileOutAddr
     */
    public static void save(BufferedImage outImg, String fileOutAddr) {
        File outFile = new File(fileOutAddr);
        String ext = fileOutAddr.substring(fileOutAddr.length() - 3);
        ext = ext.toLowerCase();
        if (!ext.equals("jpg") && !ext.equals("gif") && !ext.equals("png")) {
            ext = "png";
        }
        try {
            ImageIO.write(outImg, ext, outFile);
        } catch (IOException ex) {
        }
//        System.out.println(fileOutAddr + " saved.");
    }

    /**
     * DEPRECATED. Use loadBufferedImage()
     * @param sourceImageFrame
     * @return
     */
    public static BufferedImage loadBufferedImage(JInternalFrame sourceImageFrame) {
        return loadBufferedImage();
    }

    /**
     * Uses a filechoser to load a buffered Image.
     * @return
     */
    public static BufferedImage loadBufferedImage() {
        JFileChooser fc = new JFileChooser();
        BufferedImage sourceImage = null;
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                sourceImage = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sourceImage;
    }

    /**
     * Saves image to a file using a filechooser.
     * @param img
     */
    public static void saveImage(BufferedImage img, Component listener) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(listener);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String ext = file.getAbsolutePath();
            ext = ext.substring(ext.length() - 3, ext.length());
            try {
                ImageIO.write(img, ext, file);
            } catch (IOException ex) {
                Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Converts image to integer(?) array.
     * @param img
     * @return
     */
    public static int[] toIntArr(BufferedImage img) {
        return img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());
    }

    public static double similarity(BufferedImage bim1, BufferedImage bim2) {
        int aspectRatio1 = bim1.getWidth() / bim1.getHeight();
        int aspectRatio2 = bim2.getWidth() / bim2.getHeight();
        assert aspectRatio1 == aspectRatio2 : "The aspect ratios of the two images must be the same";

        throw new UnsupportedOperationException("Not yet implemented");

    }

    public static BufferedImage contrastMask(BufferedImage in) {
        int neighborhoodSize = 6;

        int n = neighborhoodSize;
        BufferedImage out = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = n; x < in.getWidth() - 2 * n; x++) {
            for (int y = n; y < in.getHeight() - 2 * n; y++) {
                // now the mask
                int thisHood = 0;
                for (int v = x - n; v < x + n; v++) {
                    for (int w = y - n; w < y + n; w++) {
                        thisHood += in.getRGB(v, w);
                        thisHood -= in.getRGB(x, y);
                        thisHood = (int) ((double) thisHood / (double) ((2 * n + 1) * (2 * n + 1) - 1));
                    }
                }
                int thisPixel = in.getRGB(x, y);
                int thisDelta = diff(thisPixel, thisHood);

                if (thisDelta < 0) {
                    thisDelta = 255 - thisDelta;
                }

                Color c1 = new Color(thisDelta, thisDelta, thisDelta);
                out.setRGB(x, y, c1.getRGB());
            }
        }
        return out;
    }

    /**
     * Tested. this is a shortcut for creating a small image.
     * @return
     */
    public static BufferedImage newImage640x480() {
        return new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
    }

    public static final BufferedImage fillImage(BufferedImage image, Color col) {
        BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        out.getGraphics().setColor(col);
        out.getGraphics().fillRect(0, 0, image.getWidth(), image.getHeight());

        return out;
    }

    public static BufferedImage[] splitToRectangles(BufferedImage canvas, int nRectangles) {
        int w = canvas.getWidth();
        int h = canvas.getHeight();

        int nW = nRectangles;

        int rectangleWidth = w / nW;
        int rW = rectangleWidth;

        int nH = (int) Math.floor(h / rectangleWidth);

        BufferedImage[] out = new BufferedImage[nH * nW];

        /*
         * process loop
         */
        int counter = 0;
        for (int x = 0; x < nW; x++) {
            for (int y = 0; y < nH; y++) {
                BufferedImage piece = ImageUtils.crop(canvas, x * rW, y * rW, rW, rW);
                out[counter++] = piece;
            }

        }

        return out;
    }

    public void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }

    /**
     * Draws a circle CENTERED at x, y
     * @param g the graphics with which do draw ova.
     * @param x center of oval
     * @param y center of oval
     * @param ovalDiam the diameter of the oval.
     */
    public void drawOval(Graphics2D g, int x, int y, int ovalDiam) {
        g.drawOval(x - (ovalDiam / 2), y - (ovalDiam / 2), ovalDiam, ovalDiam);
    }

    /**
     * Take a (grayscape) black and white bufferedImage and threshold it based on thresh parameter.
     * Return an image that is strictly monokrome.
     * @param img
     * @param thresh
     * @return
     */
    public BufferedImage grayscale2Monochrome(BufferedImage img, int thresh) {
        int w = img.getWidth();
        int h = img.getHeight();
        int[] pixels = new int[w * h];

        PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
        try {
            pg.grabPixels();
        } catch (Exception e) {
        }

        // thresh it
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] < thresh) {
                pixels[i] = 0;
            } else {
                pixels[i] = 255;
            }
        }

        Image image = createImage(new MemoryImageSource(w, h, pixels, 0, w));
        BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        System.out.println("writing image...");
        imgOut.createGraphics().drawImage(image, 0, 0, this);

        return imgOut;
    }

    /**
     * Does what is says it does: takes an image and returns int[] of pixels.
     * @param img
     * @return
     */
    public int[] Img2IntArr(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int[] pixels = new int[w * h];

        PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
        try {
            pg.grabPixels();
        } catch (Exception e) {
        }
        return pixels;
    }

    /**
     * Convert color image to grayscale.
     * @param img
     * @return
     */
    public static BufferedImage toGrayscale(BufferedImage raw) {
        BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return op.filter(raw, null);

//        BufferedImage temp = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
//
//        Graphics g = temp.getGraphics();
//        g.drawImage(raw, 0, 0, null);
//        g.dispose();
//
//        return temp;

    }

    public static void drawHistogram(int[] DArr, int DMax, int w, int h, String saveTo) {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = (Graphics2D) bufferedImage.createGraphics();
        // background
        g.setColor(Color.magenta);
        g.fillRect(0, 0, w, h);

        int barWidth = w / DArr.length;
        barWidth = Math.max(barWidth, 1);

        float mult = (float) h / (float) DMax;
        // System.out.println("mult: "+ mult);
        g.setColor(Color.BLACK);
        g.setBackground(Color.BLACK);
        g.setStroke(new BasicStroke(3.0f));
        for (int i = 0; i < DArr.length; i++) {
            int yPos = (int) (((float) w / (float) DArr.length) * (float) i);

            // g.fillRect(0, yPos, barWidth, (int) (DArr[i] * mult));
            // g.drawRect(0, yPos, barWidth, (int) (DArr[i] * mult));
            g.drawString(".", yPos, h - (int) (DArr[i] * mult));
//            // check
//            System.out.println(0 +" "+ yPos +" "+ barWidth +" "+ (int) (DArr[i] * mult));
        }

        ImageUtils.paintImage(bufferedImage, saveTo);
    }

    /**
     * Computes the difference between two pixels in all three: R, G, and B.
     * @param pixel1
     * @param pixel2
     * @return difference between pixel1 and pixel2.
     */
    public static int diff(int pixel1, int pixel2) {
        Color c1 = new Color(pixel1);
        Color c2 = new Color(pixel2);
        int D = 0; // the delta

        D = D + Math.abs(c1.getRed() - c2.getRed());
        D = D + Math.abs(c1.getGreen() - c2.getGreen());
        D = D + Math.abs(c1.getBlue() - c2.getBlue());
        D /= 3;

//        // check
//        System.out.println("difference is "+ D);
        return D;
    }

    public BufferedImage Blur(BufferedImage img, int n, float value) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);


        // init
        float[] elements = {
            .1111f, .1111f, .1111f,
            .1111f, .1111f, .1111f,
            .1111f, .1111f, .1111f};
        Kernel kernel = new Kernel(3, 3, elements);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        cop.filter(img, img2);

        int iw = img.getWidth(this);
        int ih = img.getHeight(this);
        BufferedImage bi = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);

        int bw = bi.getWidth(this);
        int bh = bi.getHeight(this);
        BufferedImageOp biop = null;
        BufferedImage bimg = new BufferedImage(bw, bh, BufferedImage.TYPE_INT_RGB);
        cop.filter(bi, bimg);

        return img2;
    }

    /**
     * Loads image from file. Refutns bufered image.
     * @param addr to load BufferedImage from
     * @return Bufferedimage
     */
    public static BufferedImage loadBufferedImage(String addr) {
        File file = new File(addr);
//        BufferedImage bim = null;
//        try {
//            bim = ImageIO.read(file);
//        } catch (IOException ex) {
//            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return bim;
        return loadBufferedImage(file);
    }

    /**
     * Writes an image to file.
     * @param img image
     * @param addr file
     */
    public static void paintImage(BufferedImage img, String addr) {
//        System.out.println("Saving " + addr);
        File outFile = new File(addr);
        try {
            ImageIO.write((RenderedImage) img, addr.substring(addr.length() - 3), outFile);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage IntArr2Img(int[] pixels, int w, int h) {
        Image image = createImage(new MemoryImageSource(w, h, pixels, 0, w));
        BufferedImage imgOut = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        System.out.println("writing image...");
        imgOut.createGraphics().drawImage(image, 0, 0, this);

        return imgOut;
    }

    /**
     * This method returns a buffered image with the contents of an image.
     * http://www.exampledepot.com/egs/java.awt.image/Image2Buf.html
     *
     * @param image
     * @return
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

    static void SaveIntArrAsImg(int[] in, String imgOutAddr, int w, int h) {
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        int counter = 0;
        for (int col = 0; col < w; col++) {
            for (int row = 0; row < h; row++) {
                out.setRGB(row, col, new Color(in[counter], in[counter], in[counter]).getRGB());
                counter++;
            }
        }

        ImageUtils.saveImage(out, imgOutAddr);
    }

    static void paintSlice(BufferedImage in, BufferedImage slice, Point nw) {
        Graphics g = in.getGraphics();
        g.drawImage(slice, nw.x, nw.y, slice.getWidth(), slice.getHeight(), null);

    }

    /**
     * Take array of pixels and return array of luminocity.
     * @param maskIntArr
     * @return
     */
    static int[] pixelArr2LumArr(int[] in) {
        for (int i = 0; i < in.length; i++) {
            int pixel = in[i];
            int red = (pixel >> 16) & 0xff;
            int green = (pixel >> 8) & 0xff;
            int blue = (pixel) & 0xff;

            int lum = MatrixUtils.average(new int[]{red, green, blue});
            in[i] = lum;
        }

        return in;
    }

    /**
     * @deprecated
     * Well, saves the image!
     * @param outImg
     * @param imgOutAddr
     */
    static void saveImage_trash(BufferedImage outImg, String imgOutAddr) {
        try {
            ImageIO.write(outImg, imgOutAddr.substring(imgOutAddr.length() - 3, imgOutAddr.length()), new File(imgOutAddr));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println(imgOutAddr +" saved.");
    }

    /**
     * It saves the image.
     * @param img
     * @param imgOutAddr
     * @param w
     * @param h
     */
    static void saveImage(Image img, String imgOutAddr, int w, int h) {
        BufferedImage outImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D biContext = outImg.createGraphics();
        biContext.drawImage(img, 0, 0, null);

        try {
            ImageIO.write(outImg, imgOutAddr.substring(imgOutAddr.length() - 3, imgOutAddr.length()), new File(imgOutAddr));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println(imgOutAddr +" saved.");
    }

    /**
     * This method returns true if the specified image has transparent pixels.
     * http://www.exampledepot.com/egs/java.awt.image/HasAlpha.html
     *
     * @param image
     * @return
     */
    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    public static void main(String[] args) {
//        if (isImage(new File("/home/ae1/test/testImg.png"))) {
//            System.out.println("true");
//        }
    }

    public static BufferedImage scale(BufferedImage in, int width) {
        int height = width * in.getHeight() / in.getWidth();
        return scale(in, width, height);
    }

    public static BufferedImage scale(BufferedImage in, int width, int height) {

//        System.out.println(in.getWidth() +" "+ in.getHeight() +" "+ width +" "+ height);

        BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bdest.createGraphics();
        double in1 = (double) width / in.getWidth();
        double in2 = (double) height / in.getHeight();


//        System.out.println(in1 +" "+ in2);

        AffineTransform at = AffineTransform.getScaleInstance(in1, in2);
        g.drawRenderedImage(in, at);
        return bdest;
    }

    public static BufferedImage scale(BufferedImage image, double d) {
        int newWidth = (int) (image.getWidth() * d);
        return scale(image, newWidth);
    }

    /**
     *
     * @param img
     * @param x - top left corner of what you're going to crop in img, x coordinate
     * @param y - top left corner of what you're going to crop in img, y coordinate
     * @param w - width of the part to be cropped
     * @param h - height of the part to be cropped
     * @return
     */
    public static BufferedImage crop(BufferedImage img, int x, int y, int w, int h) {
        BufferedImage dest = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = dest.getGraphics();
        g.drawImage(img, 0, 0, w, h, x, y, x + w, y + h, null);
        g.dispose();
        return dest;
    }
}
