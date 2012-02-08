/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import com.piousbox.PrivateAccessor;
import java.awt.Point;
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
public class FragmenterTest {

    Config config;
    Fragmenter fragmenter;
    String testDir;
    BufferedImage adam;

    public FragmenterTest() {
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
        fragmenter = new Fragmenter(config);
        testDir = config.getTestDir();
        adam = ImageUtils.loadBufferedImage(testDir+"20100618/blah.jpg");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fragmentFolder method, of class Fragmenter.
     */
    @Test
    public void testFragmentFolder_File() {
        System.out.println("fragmentFolder");
        File file = new File(testDir+"20100619/");
        fragmenter.fragmentFolder(file);
        File processedFile1 = new File(testDir +"20100619/slices/out_1.jpg.txt");
        File processedFile2 = new File(testDir +"20100619/slices/out_2.gif.txt");
        File processedFile3 = new File(testDir +"20100619/slices/out_2.jpg.txt");

        assertTrue(processedFile1.exists());
        assertTrue(processedFile2.exists());
        assertTrue(processedFile3.exists());
    }

    /**
     * Test of fragmentImageFile method, of class Fragmenter.
     */
    @Test
    public void testFragmentImage_BufferedImage() {
        System.out.println("fragmentImage");
        BufferedImage img = ImageUtils.loadBufferedImage(testDir+"20100618/blah.jpg");
        Object[] args = new Object[1];
        args[0] = img;
        BufferedImage[] result = (BufferedImage[]) PrivateAccessor.invokePrivateMethod(fragmenter, "fragmentImage", args);

        assertEquals(result.length, 80);
        assertEquals(result[0].getWidth(), 48);

        assertEquals(img.getRGB(40, 40), result[0].getRGB(40, 40));
    }

    @Test
    public void testSliceFilename_File_Int() {
        //private String sliceFilename(File srcImg, int count) {
        File srcImg = new File(testDir+"20100618/blah.jpg");
        int count = 0;
        String expected = testDir+"20100618/slices/blah_0.jpg";
        Object[] args = new Object[2];
        args[0] = srcImg;
        args[1] = new Integer(count);
        Object result = PrivateAccessor.invokePrivateMethod(fragmenter, "sliceFilename", args);
//        result = fragmenter.sliceFilename(srcImg, count);
        System.out.println(result);
        assertEquals(expected, result);
    }


    /**
     * Test of fragmentImageFile method, of class Fragmenter.
     */
    @Test
    public void testFragmentImageFile() {
        System.out.println("fragmentImage");
        File imgFile = new File(testDir+"20100618/blah.jpg");

        fragmenter.fragmentImageFile(imgFile);
        BufferedImage before = ImageUtils.loadBufferedImage(imgFile);

        BufferedImage result1 = ImageUtils.loadBufferedImage(testDir+"20100618/slices/blah_0.jpg");
//        result1 = new ImageUtils().grayscale2Monochrome(result1, 5000);
//        assertEquals(result1.getRGB(1, 1), before.getRGB(1, 1));

        result1 = ImageUtils.loadBufferedImage(testDir+"20100618/slices/blah_1.jpg");
//        assertEquals(result1.getRGB(5, 5), before.getRGB(48+5, 5));
    }

//    @Test
//    public void testFragSize_BufferedImage() {
//        Object[] args = new Object[1];
//        args[0] = adam;
//        int result = (Integer) PrivateAccessor.invoke_trash(fragmenter, "fragSize", args);
//        assertEquals(result, 48);
//    }

    @Test
    public void testFragSize_BufferedImage() {
        assertEquals(1, 48);
    }

    @Test
    public void testFragsPerHeight_BufferedImage_Int() {
        int result = fragmenter.fragsPerHeight(adam, 48);
        assertEquals(result, 10);
    }

    @Test
    public void testIsProcessed() {
        boolean value1 = true;
        boolean value2 = false;

        File fileIn = new File("/var/www/test/20100617/blah.png");
        File fileIn2 = new File("/var/www/test/20100617/blah.jpg");

        Object[] objArr = new Object[1];
        objArr[0] = fileIn;

        value1 = (Boolean) PrivateAccessor.invokePrivateMethod(fragmenter, "isProcessed", objArr);

        objArr[0] = fileIn2;

        value2 = (Boolean) PrivateAccessor.invokePrivateMethod(fragmenter, "isProcessed", objArr);

        assertEquals(true, value1);
        assertEquals(false, value2);
    }

    @Test
    public void testSetProcessed() {
        File markFile = new File(testDir+"20100618/slices/blah.jpg.txt");
        if (markFile.exists()) {
            markFile.delete();
        }
        File imgFile = new File(testDir+"20100618/blah.jpg");

        Object[] parameters = new Object[1];
        parameters[0] = imgFile;
        PrivateAccessor.invokePrivateMethod(fragmenter, "setProcessed", parameters);

        markFile = new File(testDir+"20100618/slices/blah.jpg.txt");
        assertTrue(markFile.exists());
    }

    @Test
    public void testImagesInfoFile() {
        Object[] params = new Object[1];
        params[0] = new File(testDir+"20100618/blah.jpg");
        File result = (File) PrivateAccessor.invokePrivateMethod(fragmenter, "imagesInfoFile", params);
        File expected = new File(testDir+"20100618/slices/blah.jpg.txt");
        assertEquals(result.getAbsoluteFile(), expected.getAbsoluteFile());
    }

    @Test
    public void testComparisonMask16x16_BufferedImage() {
        int[] result = Fragmenter.getComparisonMask(adam);
        assertEquals(result.length, 256);
        assertTrue(result[10] != 0);
        assertTrue(result[100] != 0);
    }

    @Test
    public void testGetMaskAddr() {
        System.out.println("getMaskAddr");
        File thisFile = new File(testDir + "20100705/out1.jpg");
        String expResult = testDir + "20100705/.out1.jpg_mask16x16";
        File exp = new File(expResult);
        String result = Fragmenter.getMaskAddr(thisFile);
        File res = new File(result);
        assertEquals(exp, res);
    }

    @Test
    public void testCompare() {
        System.out.println("compare");
        BufferedImage bim1 = null;
        BufferedImage bim2 = null;
        double expResult = 0.0;
        double result = Fragmenter.compare(bim1, bim2);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testComparisonMask16x16() {
        System.out.println("comparisonMask16x16");
        BufferedImage in = null;
        int[] expResult = null;
        int[] result = Fragmenter.getComparisonMask(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFragmentFolder_0args() {
        System.out.println("fragmentFolder");
        Fragmenter instance = new Fragmenter();
        instance.fragmentFolder();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFragmentImage_3args() {
        System.out.println("fragmentImage");
        BufferedImage thisImage = null;
        int width = 0;
        int height = 0;
        BufferedImage[] expResult = null;
        BufferedImage[] result = Fragmenter.fragmentImage(thisImage, width, height);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFragmentImage_BufferedImage_int() {
        System.out.println("fragmentImage");
        BufferedImage img = null;
        int fragsPerWidth = 0;
        BufferedImage[] expResult = null;
        BufferedImage[] result = Fragmenter.imageToPartitions(img, fragsPerWidth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testShuffleRandomBits() {
        System.out.println("shuffleRandomBits");
        BufferedImage img = null;
        Fragmenter instance = new Fragmenter();
        BufferedImage expResult = null;
        BufferedImage result = instance.shuffleRandomBits(img);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Fragmenter.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFragsPerHeight() {
        System.out.println("fragsPerHeight");
        BufferedImage img = null;
        int fragSize = 0;
        int expResult = 0;
        int result = Fragmenter.fragsPerHeight(img, fragSize);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testFragSize() {
        System.out.println("fragSize");
        BufferedImage img = null;
        int fragsPerWidth = 0;
        int expResult = 0;
        int result = Fragmenter.fragSize(img, fragsPerWidth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
    public void testFragmentImage() {
        System.out.println("fragmentImage");
        BufferedImage thisImage = null;
        int width = 0;
        int height = 0;
        BufferedImage[] expResult = null;
        BufferedImage[] result = Fragmenter.fragmentImage(thisImage, width, height);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testImageToPartitions() {
        System.out.println("imageToPartitions");
        BufferedImage img = null;
        int fragsPerWidth = 0;
        BufferedImage[] expResult = null;
        BufferedImage[] result = Fragmenter.imageToPartitions(img, fragsPerWidth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testImageToFrags() {
        System.out.println("imageToFrags");
        BufferedImage image = adam;
        int fragsPerWidth = 3;
        int nRndPartitions = 2;
        Frag[] expResult = new Frag[2];
        Frag[] result = Fragmenter.imageToFrags(image, fragsPerWidth, nRndPartitions);
        assertEquals(expResult.length, result.length);
    }

    @Test
    public void testClearSlices() {
        System.out.println("clearSlices");
        config.setLibIn(testDir+"/20100708/");
        Fragmenter instance = new Fragmenter(config);
        instance.clearSlices();
        File slicesDir = new File(testDir + "/20100708/slices/");
        assertTrue(!slicesDir.exists());
    }

    @Test
    public void testImageNwPoints() {
        System.out.println("imageNwPoints");
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        int fragsPerWidth = 3;
        Point[] exp = new Point[9];
        exp[0] = new Point(0, 0);
        exp[1] = new Point(0, 66);
        exp[2] = new Point(0, 132);
        exp[3] = new Point(66, 0);
        exp[4] = new Point(66, 66);
        exp[5] = new Point(66, 132);
        exp[6] = new Point(132, 0);
        exp[7] = new Point(132, 66);
        exp[8] = new Point(132, 132);

        Point[] result = Fragmenter.imageNwPoints(image, fragsPerWidth);
        assertEquals(exp, result);
    }

}
