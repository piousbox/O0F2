/*
 * MatrixUtilsTest.java
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
import java.awt.Point;
import java.util.ArrayList;
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
public class MatrixUtilsTest {

    public MatrixUtilsTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of average method, of class MatrixUtils.
     */
    @Test
    public void testAverage() {
        System.out.println("average");
        int[][] in = new int[2][2];
        in[0][0] = 2;
        in[0][1] = 2;
        in[1][0] = 4;
        in[1][1] = 4;
        int expResult = 3;
        int result = MatrixUtils.average(in);
        assertEquals(expResult, result);
    }

    /**
     * Test of initRnd method, of class MatrixUtils.
     */
    @Test
    public void testInitRnd() {
        System.out.println("initRnd");
        int nDims = 2;
        double[] result = MatrixUtils.initRnd(nDims);
        assertTrue(result[0]>0);
        assertTrue(result[0]<1);
        assertTrue(result[1]>0);
        assertTrue(result[1]<1);
    }

    @Test
    public void testSimilarity_intArr_intArr() {
        int[] arr1 = {1, 2, 3, 4, 5};
        int[] arr2 = {5, 6, 7,8, 9};
        double result = MatrixUtils.similarity(arr1, arr2);
        double expected = 0.05;
        assertEquals(result, expected);

        int[] arr3 = {5, 2, 1};
        int[] arr4 = {6, 0, 2};
        result = MatrixUtils.similarity(arr3, arr4);
        expected = 0.25;
        assertEquals(result, expected);
    }

    /**
     * Test of initToZeroes method, of class MatrixUtils.
     */
    @Test
    public void testInitToZeroes_doubleArr() {
        System.out.println("initToZeroes");
        double[] mean = null;
        MatrixUtils.initToZeroes(mean);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initToZeroes method, of class MatrixUtils.
     */
    @Test
    public void testInitToZeroes_intArrArr() {
        System.out.println("initToZeroes");
        int[][] wordMatrix = null;
        MatrixUtils.initToZeroes(wordMatrix);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exportMatrixToR method, of class MatrixUtils.
     */
    @Test
    public void testExportMatrixToR_4args() throws Exception {
        System.out.println("exportMatrixToR");
        int[][] matrix = null;
        String[] cols = null;
        String[] rows = null;
        String addr = "";
        MatrixUtils.exportMatrixToR(matrix, cols, rows, addr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exportMatrixToR method, of class MatrixUtils.
     */
    @Test
    public void testExportMatrixToR_intArrArr_String() throws Exception {
        System.out.println("exportMatrixToR");
        int[][] matrix = null;
        String addr = "";
        MatrixUtils.exportMatrixToR(matrix, addr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of zeroes method, of class MatrixUtils.
     */
    @Test
    public void testZeroes() {
        System.out.println("zeroes");
        int nDims = 0;
        double[] expResult = null;
        double[] result = MatrixUtils.zeroes(nDims);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testAverage_intArrArr() {
        System.out.println("average");
        int[][] in = null;
        int expResult = 0;
        int result = MatrixUtils.average(in);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSimilarity() {
        System.out.println("similarity");
        int[] arr1 = null;
        int[] arr2 = null;
        double expResult = 0.0;
        double result = MatrixUtils.similarity(arr1, arr2);
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAverage_intArr() {
        System.out.println("average");
        int[] in = null;
        int expResult = 0;
        int result = MatrixUtils.average(in);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsIn_Point_PointArr() {
        System.out.println("isIn");
        Point point = null;
        Point[] arr = null;
        boolean expResult = false;
        boolean result = MatrixUtils.isIn(point, arr);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsIn_Point_ArrayList() {
        System.out.println("isIn");
        Point point = null;
        ArrayList<Point> arr = null;
        boolean expResult = false;
        boolean result = MatrixUtils.isIn(point, arr);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testMax() {
        System.out.println("max");
        double[] t = null;
        double expResult = 0.0;
        double result = MatrixUtils.max(t);
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

    @Test
    public void testMin() {
        System.out.println("min");
        double[] t = null;
        double expResult = 0.0;
        double result = MatrixUtils.min(t);
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

}