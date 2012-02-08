/*
 * ImageUtilsTest.java
 *
 * Copyright (c) 2009 Victor Pudeyev <piousbox@gmail.com>. All rights reserved.
 *
 * This file is part of FinFanFun.
 *
 * FinFanFun is free software; it may not be used in commercial
 * setting without prior written permission from the author. To
 * obtain written permission to use the software commercially,
 * please contact Victor Pudeyev at piousbox@gmail.com.
 *
 * Warning: All your base are belong to us.
 *
 */
package com.piousbox;

import com.piousbox.ImageUtils;
import com.piousbox.graphics.Config;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JInternalFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Victor Pudeyev <piousbox@gmail.com>
 */
public class ImageUtilsTest {

    Config config;
    private String testDir;
    BufferedImage img1, img2, in = null;

    public ImageUtilsTest() {
    }

    @Before
    public void setUp() {
        config = new Config();
        testDir = config.getTestDir();

        img1 = ImageUtils.loadBufferedImage(testDir + "20100628/blah.jpg");
        img2 = ImageUtils.loadBufferedImage(testDir + "20100628/blah.jpg");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetLuminocity() {
        System.out.println("getLuminocity");
        Color c = new Color(255, 255, 255);
        int expResult = 255;
        int result = ImageUtils.getLuminocity(c);
        assertEquals(expResult, result);
    }

    @Test
    public void testMagnify() {
        System.out.println("magnify");
        BufferedImage in = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        Color c = new Color(0, 0, 0);
        in.setRGB(1, 1, c.getRGB());
        int magnify = 2;
        BufferedImage expResult = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
        BufferedImage result = ImageUtils.magnify(in, magnify);

        assertEquals(expResult.getWidth(), result.getWidth());
        assertEquals(expResult.getRGB(2, 2), result.getRGB(2, 2));
    }

    @Test
    public void testDrawOval() {
        System.out.println("drawOval");
        assertEquals("I'm lazy to test this", 1, 1);
    }

    @Test
    public void testGrayscale2Monochrome() {
        System.out.println("Grayscale2Monochrome");
        BufferedImage img = null;
        int thresh = 0;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.grayscale2Monochrome(img, thresh);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testImg2IntArr() {
        System.out.println("Img2IntArr");
        BufferedImage img = null;
        ImageUtils instance = new ImageUtils();
        int[] expResult = null;
        int[] result = instance.Img2IntArr(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testColor2Grayscale() {
        System.out.println("Color2Grayscale");
        BufferedImage img = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.toGrayscale(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDrawHistogram() {
        System.out.println("drawHistogram");
        int[] DArr = null;
        int DMax = 0;
        int w = 0;
        int h = 0;
        String saveTo = "";
        ImageUtils.drawHistogram(DArr, DMax, w, h, saveTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDiff() {
        System.out.println("diff");

        int i = img1.getRGB(5, 5);
        int ii = img2.getRGB(5, 5);
        assertEquals(0, ImageUtils.diff(i, ii));

        ii = img2.getRGB(10, 10);
        assertFalse(0 == ImageUtils.diff(i, ii));

        for (int k = 0; k < 10; k++) {
            i = img1.getRGB(k, k);
            ii = img2.getRGB(k + 10, k + 10);
            System.out.println("Difference between " + i + " and " + ii + " is " + ImageUtils.diff(i, ii));
        }
    }

    @Test
    public void testBlur() {
        System.out.println("Blur");
        BufferedImage img = null;
        int n = 0;
        float value = 0.0F;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.Blur(img, n, value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoadBufferedImage() throws IOException {
        System.out.println("LoadBufferedImage");
        String addr = "/home/ae1/test/testImg.png";
        BufferedImage expResult = ImageIO.read(new File(addr));
        BufferedImage result = ImageUtils.loadBufferedImage(addr);
        assertEquals(expResult.getClass(), result.getClass());
        assertEquals(expResult.getMinX(), result.getMinX());
    }

    @Test
    public void testPaintImage() {
        System.out.println("paintImage");
        Image img = null;
        String addr = "";
//    ImageUtils.paintImage(img, addr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testIntArr2Img() {
        System.out.println("IntArr2Img");
        int[] pixels = null;
        int w = 0;
        int h = 0;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.IntArr2Img(pixels, w, h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetLuminocity_int() {
        System.out.println("getLuminocity");
        int r = 0;
        int expResult = 0;
        int result = ImageUtils.getLuminocity(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMagnify_BufferedImage_int() {
        System.out.println("magnify");
        BufferedImage in = null;
        int magnify = 0;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.magnify(in, magnify);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testComputeGrayValue() {
        System.out.println("computeGrayValue");
        int pixel = 0;
        int expResult = 0;
        int result = ImageUtils.computeGrayValue(pixel);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNormalize() {
        System.out.println("normalize");
        int[][] in = null;
        int min = 0;
        int max = 0;
        int[][] expResult = null;
        int[][] result = ImageUtils.normalize(in, min, max);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testArrayToImage() {
        System.out.println("arrayToImage");
        int[][] in = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.arrayToImage(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSave() {
        try {
            System.out.println("save");
            String addr = testDir + "20100618/blah.jpg";
            System.out.println(addr);
            File fileIn = new File(addr);
            assertTrue(fileIn.exists());

            BufferedImage outImg = ImageUtils.loadBufferedImage(fileIn);
            //            System.out.println("pixels: "+ outImg.getRGB(50, 50));
            //            System.out.println("pixels: "+ outImg.getRGB(60, 50));
            //            System.out.println("pixels: "+ outImg.getRGB(70, 50));
            assertNotNull(outImg);

            //
            //             in jpg.
            //
            String fileOutAddr = testDir + "20100619/out_2.jpg";
            ImageUtils.save(outImg, fileOutAddr);
            BufferedImage result = ImageIO.read(new File(fileOutAddr));
            assertNotNull(result);


            assertEquals(outImg.getRGB(50, 50), result.getRGB(50, 50));
            assertEquals(outImg.getRGB(60, 50), result.getRGB(60, 50));
            assertEquals(outImg.getRGB(50, 54), result.getRGB(50, 54));
            assertTrue(outImg.getRGB(100, 54) != result.getRGB(54, 100));

            //
            //            now in gif
            //
            fileOutAddr = testDir + "20100619/out_2.gif";
            ImageUtils.save(outImg, fileOutAddr);
            result = ImageIO.read(new File(fileOutAddr));
            assertNotNull(result);


            assertEquals(outImg.getRGB(50, 50), result.getRGB(50, 50));
            assertEquals(outImg.getRGB(60, 50), result.getRGB(60, 50));
            assertEquals(outImg.getRGB(50, 54), result.getRGB(50, 54));
            assertTrue(outImg.getRGB(100, 54) != result.getRGB(54, 100));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtilsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testPrintPixelARGB() {
        System.out.println("printPixelARGB");
        int pixel = 0;
        ImageUtils instance = new ImageUtils();
        instance.printPixelARGB(pixel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDrawOval_4args() {
        System.out.println("drawOval");
        Graphics2D g = null;
        int x = 0;
        int y = 0;
        int ovalDiam = 0;
        ImageUtils instance = new ImageUtils();
        instance.drawOval(g, x, y, ovalDiam);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGrayscale2Monochrome_BufferedImage_int() {
        System.out.println("Grayscale2Monochrome");
        BufferedImage img = null;
        int thresh = 0;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.grayscale2Monochrome(img, thresh);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testImg2IntArr_BufferedImage() {
        System.out.println("Img2IntArr");
        BufferedImage img = null;
        ImageUtils instance = new ImageUtils();
        int[] expResult = null;
        int[] result = instance.Img2IntArr(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testColor2Grayscale_BufferedImage() {
        System.out.println("Color2Grayscale");
        BufferedImage img = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.toGrayscale(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDrawHistogram_5args() {
        System.out.println("drawHistogram");
        int[] DArr = null;
        int DMax = 0;
        int w = 0;
        int h = 0;
        String saveTo = "";
        ImageUtils.drawHistogram(DArr, DMax, w, h, saveTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDiff_int_int() {
        System.out.println("diff");
        int r1 = 0;
        int r2 = 0;
        int expResult = 0;
        int result = ImageUtils.diff(r1, r2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testBlur_3args() {
        System.out.println("Blur");
        BufferedImage img = null;
        int n = 0;
        float value = 0.0F;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.Blur(img, n, value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoadBufferedImage_String() {
        System.out.println("loadBufferedImage");
        String addr = "";
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.loadBufferedImage(addr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPaintImage_Image_String() {
        System.out.println("paintImage");
        Image img = null;
        String addr = "";
//    ImageUtils.paintImage(img, addr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testIntArr2Img_3args() {
        System.out.println("IntArr2Img");
        int[] pixels = null;
        int w = 0;
        int h = 0;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.IntArr2Img(pixels, w, h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetLuminocity_int_1args() {
        System.out.println("getLuminocity");
        int r = 0;
        int expResult = 0;
        int result = ImageUtils.getLuminocity(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsImage() {
        System.out.println("isImage");
        File file = new File(testDir + "testImg.png");
        File file2 = new File(testDir + "blah.txt");
        assertEquals(true, ImageUtils.isImage(file));
        assertEquals(false, ImageUtils.isImage(file2));
    }

    @Test
    public void testMagnify_BufferedImage_int_2args() {
        System.out.println("magnify");
        BufferedImage in = null;
        int magnify = 0;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.magnify(in, magnify);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testComputeGrayValue_int() {
        System.out.println("computeGrayValue");
        int pixel = 0;
        int expResult = 0;
        int result = ImageUtils.computeGrayValue(pixel);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNormalize_3args() {
        System.out.println("normalize");
        int[][] in = null;
        int min = 0;
        int max = 0;
        int[][] expResult = null;
        int[][] result = ImageUtils.normalize(in, min, max);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testArrayToImage_intArrArr() {
        System.out.println("arrayToImage");
        int[][] in = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.arrayToImage(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSave_BufferedImage_String() {
        System.out.println("save");
        BufferedImage outImg = null;
        String fileOutAddr = "";
        ImageUtils.save(outImg, fileOutAddr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPrintPixelARGB_int() {
        System.out.println("printPixelARGB");
        int pixel = 0;
        ImageUtils instance = new ImageUtils();
        instance.printPixelARGB(pixel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDrawOval_4args_1() {
        System.out.println("drawOval");
        Graphics2D g = null;
        int x = 0;
        int y = 0;
        int ovalDiam = 0;
        ImageUtils instance = new ImageUtils();
        instance.drawOval(g, x, y, ovalDiam);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGrayscale2Monochrome_BufferedImage_int_2args() {
        System.out.println("Grayscale2Monochrome");
        BufferedImage img = null;
        int thresh = 0;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.grayscale2Monochrome(img, thresh);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testImg2IntArr_BufferedImage_1args() {
        System.out.println("Img2IntArr");
        BufferedImage img = null;
        ImageUtils instance = new ImageUtils();
        int[] expResult = null;
        int[] result = instance.Img2IntArr(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testColor2Grayscale_BufferedImage_1args() {
        System.out.println("Color2Grayscale");
        BufferedImage img = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.toGrayscale(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDrawHistogram_5args_1() {
        System.out.println("drawHistogram");
        int[] DArr = null;
        int DMax = 0;
        int w = 0;
        int h = 0;
        String saveTo = "";
        ImageUtils.drawHistogram(DArr, DMax, w, h, saveTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDiff_int_int_2args() {
        System.out.println("diff");
        int r1 = 0;
        int r2 = 0;
        int expResult = 0;
        int result = ImageUtils.diff(r1, r2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testBlur_3args_1() {
        System.out.println("Blur");
        BufferedImage img = null;
        int n = 0;
        float value = 0.0F;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.Blur(img, n, value);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoadBufferedImage_String_1args() {
        System.out.println("loadBufferedImage");
        String addr = "";
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.loadBufferedImage(addr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPaintImage_Image_String_2args() {
        System.out.println("paintImage");
        Image img = null;
        String addr = "";
//    ImageUtils.paintImage(img, addr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testIntArr2Img_3args_1() {
        System.out.println("IntArr2Img");
        int[] pixels = null;
        int w = 0;
        int h = 0;
        ImageUtils instance = new ImageUtils();
        BufferedImage expResult = null;
        BufferedImage result = instance.IntArr2Img(pixels, w, h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testIntArrToImg() {
        System.out.println("intArrToImg");
        int[] in = null;
        int w = 0;
        int h = 0;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.intArrToImg(in, w, h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoadBufferedImage_File() {
        System.out.println("loadBufferedImage");
        File in = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.loadBufferedImage(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNormalize_3args_1() {
        System.out.println("normalize");
        int[][] in = null;
        int min = 0;
        int max = 0;
        int[][] expResult = null;
        int[][] result = ImageUtils.normalize(in, min, max);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNormalize_3args_2() {
        System.out.println("normalize");
        int[] in = null;
        int min = 0;
        int max = 0;
        int[] expResult = null;
        int[] result = ImageUtils.normalize(in, min, max);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoadBufferedImage_JInternalFrame() {
        System.out.println("loadBufferedImage");
        JInternalFrame sourceImageFrame = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.loadBufferedImage(sourceImageFrame);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testLoadBufferedImage_0args() {
        System.out.println("loadBufferedImage");
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.loadBufferedImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSaveImage_BufferedImage_Component() {
        System.out.println("saveImage");
        BufferedImage img = null;
        Component listener = null;
        ImageUtils.saveImage(img, listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testToIntArr() {
        System.out.println("toIntArr");
        BufferedImage img = null;
        int[] expResult = null;
        int[] result = ImageUtils.toIntArr(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testToGrayscale() {
        System.out.println("toGrayscale");
        BufferedImage img = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.toGrayscale(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testToBufferedImage() {
        System.out.println("toBufferedImage");
        Image image = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.toBufferedImage(image);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSaveIntArrAsImg() {
        System.out.println("SaveIntArrAsImg");
        int[] in = null;
        String imgOutAddr = "";
        int w = 0;
        int h = 0;
        ImageUtils.SaveIntArrAsImg(in, imgOutAddr, w, h);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPixelArr2LumArr() {
        System.out.println("pixelArr2LumArr");
        int[] in = null;
        int[] expResult = null;
        int[] result = ImageUtils.pixelArr2LumArr(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSaveImage_BufferedImage_String() {
        System.out.println("saveImage");
        BufferedImage outImg = null;
        String imgOutAddr = "";
        ImageUtils.saveImage(outImg, imgOutAddr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSaveImage_4args() {
        System.out.println("saveImage");
        Image img = null;
        String imgOutAddr = "";
        int w = 0;
        int h = 0;
        ImageUtils.saveImage(img, imgOutAddr, w, h);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testHasAlpha() {
        System.out.println("hasAlpha");
        Image image = null;
        boolean expResult = false;
        boolean result = ImageUtils.hasAlpha(image);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testScale_BufferedImage_int() {
        System.out.println("scale");
        BufferedImage in = null;
        int width = 0;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.scale(in, width);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testScale_3args() {
        System.out.println("scale");
        BufferedImage in = null;
        int width = 0;
        int height = 0;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.scale(in, width, height);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSimilarity() {
        System.out.println("similarity");
        BufferedImage bim1 = null;
        BufferedImage bim2 = null;
        double expResult = 0.0;
        double result = ImageUtils.similarity(bim1, bim2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testContrastMask() {
        System.out.println("contrastMask");
        BufferedImage result = ImageUtils.contrastMask(img1);
        ImageUtils.saveImage(result, testDir + "/20100706/test1.png");
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPaintSlice() {
        System.out.println("paintSlice");
        BufferedImage in = null;
        BufferedImage slice = null;
        Point nw = null;
        ImageUtils.paintSlice(in, slice, nw);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSaveImage_trash() {
        System.out.println("saveImage_trash");
        BufferedImage outImg = null;
        String imgOutAddr = "";
        ImageUtils.saveImage_trash(outImg, imgOutAddr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ImageUtils.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testCrop() {
        System.out.println("crop");
        BufferedImage img = null;
        int x = 0;
        int y = 0;
        int w = 0;
        int h = 0;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.crop(img, x, y, w, h);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNewImage640x480() {
        System.out.println("newImage640x480");
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.newImage640x480();
        assertEquals(640, result.getWidth());
        assertEquals(480, result.getHeight());
    }

    @Test
    public void testFillImage() {
        System.out.println("fillImage");
        BufferedImage image = null;
        Color col = null;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.fillImage(image, col);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSplitToRectangles() {
        System.out.println("splitToRectangles");
        BufferedImage canvas = ImageUtils.newImage640x480();
        int nRectangles = 10;
        BufferedImage[] expResult = new BufferedImage[7 * 10];
        BufferedImage[] result = ImageUtils.splitToRectangles(canvas, nRectangles);
        assertEquals(expResult.length, result.length);
        assertEquals(64, result[0].getWidth());
        assertEquals(64, result[0].getHeight());
    }

    @Test
    public void testScale_BufferedImage_double() {
        System.out.println("scale");
        BufferedImage image = null;
        double d = 0.0;
        BufferedImage expResult = null;
        BufferedImage result = ImageUtils.scale(image, d);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    // */
}
