package com.piousbox.graphics;

import com.piousbox.MatrixUtils;
import com.piousbox.MatrixUtils.PointComparator;
import java.util.Arrays;
import java.awt.Point;
import java.awt.image.BufferedImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class MultisLITest {

    Config c;

    public MultisLITest() {
        c = new Config();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCentersOfCells method, of class MultisLI.
     */
    @Test
    public void testGetCentersOfCells() {
        System.out.println("getCentersOfCells");
        BufferedImage img = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);
        int w = img.getWidth();
        int h = img.getHeight();
        int nCells = 7;
        int d = h / 6; // diameter
        double pi = Math.PI;
        int x = (int) (d * Math.tan(pi / 3));
        int y = (int) (d / Math.cos(pi / 3));
        Point[] expResult = new Point[nCells];
        expResult[0] = new Point(w / 2, h / 2);
        expResult[1] = new Point(w / 2, h / 6);
        expResult[2] = new Point(w / 2, 5 * h / 6);
        expResult[3] = new Point(w / 2 - x, d * 2);
        expResult[4] = new Point(w / 2 - x, d * 4);
        expResult[5] = new Point(w / 2 + x, d * 2);
        expResult[6] = new Point(w / 2 + x, d * 4);
        Point[] result = MultisLI.getCentersOfCells(img, nCells);
        Arrays.sort(expResult, new MatrixUtils.PointComparator());
        Arrays.sort(result, new MatrixUtils.PointComparator());
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testComputeClipShape_Point() {
        fail("prototype");
    }

    /**
     * Test of main method, of class MultisLI.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
//        MultisLI.main(args);
         /**
          * @TODO: review the generated test code and remove the default call to fail.
          */
//        fail("The test case is a prototype.");
    }
}
