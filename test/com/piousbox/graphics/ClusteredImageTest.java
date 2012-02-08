/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ae1
 */
public class ClusteredImageTest {

    O0Filter instance;
    String img_addr = "/var/www/test/20100614/blah.jpg";
    int p = 20;
    int thresh = 50;
    int nBlobs = 10;

    public ClusteredImageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        instance = new O0Filter(ImageUtils.loadBufferedImage(img_addr), p, thresh, nBlobs);
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getWidth method, of class ClusteredImage.
     */
    @Test
    public void testGetWidth() {
        System.out.println("getWidth");
        ImageObserver observer = null;
        int expResult = 387;
        int result = instance.getWidth(observer);
        assertEquals(expResult, result);
    }

    /**
     * Test of getHeight method, of class ClusteredImage.
     */
    @Test
    public void testGetHeight() {
        System.out.println("getHeight");
        ImageObserver observer = null;
        int expResult = 480;
        int result = instance.getHeight(observer);
        assertEquals(expResult, result);
    }

    /**
     * Test of getImageOut method, of class ClusteredImage.
     */
    @Test
    public void testPaintImage() {
        System.out.println("PaintImage");
        String out_addr = "/var/www/test/20100614/out_1.jpg";
        instance.paintImage(out_addr);
        BufferedImage actual = ImageUtils.loadBufferedImage(out_addr);
        assertEquals(actual.getWidth(), instance.getWidth(null));
        assertTrue(ImageUtils.isImage(new File(out_addr)));
    }

}