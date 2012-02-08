/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics;

import com.piousbox.ImageUtils;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
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
public class ImagePanelTest {
    Config config;
    ImagePanel instance = null;
    BufferedImage sourceImg;
    String testDir;

    public ImagePanelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        config = new Config();
        testDir = config.getTestDir();
        sourceImg = ImageUtils.loadBufferedImage(new File(testDir + "20100705/blah.jpg"));
        instance = new ImagePanel(sourceImg, config);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetImage() {
        System.out.println("getImage");
        ImagePanel instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetIsDrawLimitedLines() {
        System.out.println("setIsDrawLimitedLines");
        boolean in = false;
        ImagePanel instance = null;
        instance.setIsDrawLimitedLines(in);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testToggleIsDrawLimitedLines() {
        System.out.println("toggleIsDrawLimitedLines");
        ImagePanel instance = null;
        instance.toggleIsDrawLimitedLines();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");
        Graphics g = null;
        ImagePanel instance = null;
        instance.paintComponent(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPaint() {
        System.out.println("paint");
        Graphics g = null;
        ImagePanel instance = null;
        instance.paint(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseClicked() {
        System.out.println("mouseClicked");
        MouseEvent e = null;
        ImagePanel instance = null;
        instance.mouseClicked(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMousePressed() {
        System.out.println("mousePressed");
        MouseEvent e = null;
        ImagePanel instance = null;
        instance.mousePressed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseReleased() {
        System.out.println("mouseReleased");
        MouseEvent me = null;
        ImagePanel instance = null;
        instance.mouseReleased(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseEntered() {
        System.out.println("mouseEntered");
        MouseEvent me = null;
        ImagePanel instance = null;
        instance.mouseEntered(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseExited() {
        System.out.println("mouseExited");
        MouseEvent me = null;
        ImagePanel instance = null;
        instance.mouseExited(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseDragged() {
        System.out.println("mouseDragged");
        MouseEvent me = null;
        ImagePanel instance = null;
        instance.mouseDragged(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseMoved() {
        System.out.println("mouseMoved");
        MouseEvent me = null;
        ImagePanel instance = null;
        instance.mouseMoved(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetPartitions() {
        System.out.println("getPartitions");

        BufferedImage[] expResult = new BufferedImage[4];

        instance.linesArrList.add(new Line2D.Double(0, 100, sourceImg.getWidth(), 100));
        instance.linesArrList.add(new Line2D.Double(100, 0, 100, sourceImg.getHeight()));

        BufferedImage[] result = instance.getPartitions();
        assertEquals(4, result.length);

        assertEquals(result[0].getWidth(), 100);
        assertEquals(result[0].getHeight(), 100);

        for (int i=0; i<result.length; i++) {
            ImageUtils.save(result[i], "/var/www/test/20100628/result_"+ i +".jpg");
        }


        assertEquals(result[1].getWidth(), sourceImg.getWidth()-100);
        assertEquals(result[1].getHeight(), 100);
    }

    @Test
    public void testGetNorthWestPoints() {
        System.out.println("getNorthWestPoints");
        ImagePanel instance = null;
        Point[] expResult = null;
        Point[] result = instance.getNorthWestPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDraw() {
        System.out.println("draw");
        BufferedImage slice = null;
        Point point = null;
        ImagePanel instance = null;
        instance.draw(slice, point);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}