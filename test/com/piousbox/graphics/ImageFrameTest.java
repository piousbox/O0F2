/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
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
public class ImageFrameTest {

    public ImageFrameTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetImagePanel() {
        System.out.println("getImagePanel");
        ImageFrame instance = null;
        ImagePanel expResult = null;
        ImagePanel result = instance.getImagePanel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetImage() {
        System.out.println("getImage");
        ImageFrame instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent ae = null;
        ImageFrame instance = null;
        instance.actionPerformed(ae);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseClicked() {
        System.out.println("mouseClicked");
        MouseEvent me = null;
        ImageFrame instance = null;
        instance.mouseClicked(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMousePressed() {
        System.out.println("mousePressed");
        MouseEvent e = null;
        ImageFrame instance = null;
        instance.mousePressed(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseReleased() {
        System.out.println("mouseReleased");
        MouseEvent e = null;
        ImageFrame instance = null;
        instance.mouseReleased(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseEntered() {
        System.out.println("mouseEntered");
        MouseEvent me = null;
        ImageFrame instance = null;
        instance.mouseEntered(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseExited() {
        System.out.println("mouseExited");
        MouseEvent me = null;
        ImageFrame instance = null;
        instance.mouseExited(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseDragged() {
        System.out.println("mouseDragged");
        MouseEvent me = null;
        ImageFrame instance = null;
        instance.mouseDragged(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMouseMoved() {
        System.out.println("mouseMoved");
        MouseEvent me = null;
        ImageFrame instance = null;
        instance.mouseMoved(me);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testItemStateChanged() {
        System.out.println("itemStateChanged");
        ItemEvent ie = null;
        ImageFrame instance = null;
        instance.itemStateChanged(ie);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFitFragmenter() {
        System.out.println("fitFragmenter");
        ImageFrame instance = null;
        instance.fitFragmenter();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}