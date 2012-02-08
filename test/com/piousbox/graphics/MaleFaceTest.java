/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.ImageUtils;
import java.awt.Color;
import java.awt.image.BufferedImage;
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
public class MaleFaceTest {

    MaleFace instance;
    String testDir;
    Config c;

    public MaleFaceTest() {
    }

    @Before
    public void setUp() {
        instance = new MaleFace();
        c = new Config();
        testDir = c.getTestDir();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        MaleFace.main(args);
        fail("The test case is a prototype.");
    }

    @Test
    public void testNewImageOut() {
        System.out.println("newImageOut");
        BufferedImage canvas = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        c.setMaleFaceWidthOut(100);

        BufferedImage expResult = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        BufferedImage result = instance.newImageOut(canvas, c);
        assertEquals(expResult.getWidth(), result.getWidth());
        assertEquals(expResult.getHeight(), result.getHeight());
    }

    @Test
    public void testComputeAverageColor() {
        System.out.println("computeAverageColor");
        BufferedImage bufferedImage = ImageUtils.loadBufferedImage(testDir + "blackSquare.png");
        Color expResult = Color.BLACK;
        Color result = instance.computeAverageColor(bufferedImage);
        assertEquals(expResult, result);

        bufferedImage = ImageUtils.loadBufferedImage(testDir + "whiteSquare.png");
        expResult = Color.WHITE;
        result = instance.computeAverageColor(bufferedImage);
        assertEquals(expResult, result);

        bufferedImage = ImageUtils.loadBufferedImage(testDir + "redSquare.png");
        expResult = Color.RED;
        result = instance.computeAverageColor(bufferedImage);
        assertEquals(expResult, result);
    }
}
