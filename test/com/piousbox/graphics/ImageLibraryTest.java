/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
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
public class ImageLibraryTest {

    ImageLibrary instance = null;
    Config config;
    String testDir;
    File thisFile;

    public ImageLibraryTest() {
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
        config.setLibIn(testDir + "20100628/");
        instance = new ImageLibrary(config);
        thisFile = new File(testDir + "20100705/out1.jpg");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testThisSlice() {
        System.out.println("thisSlice");
        config.setLibIn(testDir + "/20100619/");
        instance = new ImageLibrary(config);
        instance.setCursor(0);
        File[] files = new File[instance.getNImages()];
        int counter = 0;
        while(instance.hasNext()) {
//            instance.nextComparisonMask16x16();
            files[counter++] = instance.next();
//            System.out.println(file);
        }
        Arrays.sort(files);
        File[] exp = IOUtils.imagesInDir(new File(testDir+"/20100619/slices/"), false);
        Arrays.sort(exp);
        assertEquals(instance.getNImages(), exp.length);
        assertEquals(exp, files);
        
//        File expResult = new File(testDir + "/20100619/slices/out_1_31.jpg");
//        File result = instance.thisSlice();
//        assertEquals(expResult, result);

//        assertEquals(expResult.getRGB(5, 5), result.getRGB(5,5));
//        assertEquals(expResult.getRGB(25, 5), result.getRGB(25,5));
    }

    @Test
    public void testIterator() {
        System.out.println("hasNext, next");
        File[] expected = new File[6];
        expected[0] = new File(testDir + "20100628/blah.jpg");
        expected[1] = new File(testDir + "20100628/out1.jpg");
        expected[2] = new File(testDir + "20100628/result_0.jpg");
        expected[3] = new File(testDir + "20100628/result_1.jpg");
        expected[4] = new File(testDir + "20100628/result_2.jpg");
        expected[5] = new File(testDir + "20100628/result_3.jpg");
        Arrays.sort(expected);
        int counter = 0;
        File[] resultArr = new File[6];

        while (instance.hasNext()) {
            File result = instance.next();
            resultArr[counter++] = result;

        }

        Arrays.sort(resultArr);
        assertEquals(expected, resultArr);

    }

    @Test
    public void testRemove() {
        System.out.println("remove");
        ImageLibrary instance = null;
        instance.remove();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetCursor() {
        System.out.println("setCursor");
        int i = 0;
        ImageLibrary instance = null;
        instance.setCursor(i);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetCursor() {
        System.out.println("getCursor");
        ImageLibrary instance = null;
        int expResult = 0;
        int result = instance.getCursor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testHasNext() {
        System.out.println("hasNext");
        ImageLibrary instance = null;
        boolean expResult = false;
        boolean result = instance.hasNext();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNext() {
        System.out.println("next");
        int[] expResult = null;
        int[] result = instance.nextComparisonMask16x16();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNextComparisonMask16x16() {
        System.out.println("nextComparisonMask16x16");
        ImageLibrary instance = null;
        int[] expResult = null;
        int[] result = instance.nextComparisonMask16x16();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testExistsMask() {
        System.out.println("existsMask");

        instance.saveMask(thisFile);

        boolean expResult = true;

        boolean result = instance.existsMask(thisFile);
        assertEquals(expResult, result);

    }

    @Test
    public void testLoadMask() {
        System.out.println("loadMask");

        String maskAddr = Fragmenter.getMaskAddr(thisFile);

//        IOUtils.delete(new File(maskAddr));

        int[] expResult = Fragmenter.getComparisonMask(ImageUtils.scale(ImageUtils.loadBufferedImage(thisFile), 16, 16));
        int[] result = instance.loadMask(thisFile);
        assertEquals(expResult[5], result[5]);
        assertEquals(expResult[2], result[2]);
        assertEquals(expResult[8], result[8]);
        assertTrue(expResult[8] != result[0]);
    }

    @Test
    public void testSaveMask() {
        System.out.println("saveMask");
        File thisFile = new File(testDir + "20100705/out1.jpg");
        int[] expResult = null;
        int[] result = instance.saveMask(thisFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
