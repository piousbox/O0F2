/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox;

import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author ae1
 */
public class MatrixUtils {


    /**
     * Average value in 2d array of integers.
     * @param in
     * @return
     */
    public static int average(int[][] in) {
        int out = 0;
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                out += in[i][j];
            }
        }
        out /= in.length;
        out /= in[0].length;
        return out;
    }

    public static double similarity(int[] arr1, int[] arr2) {
        throw new UnsupportedOperationException("Not yet implemented");

//        if (arr1.length != arr2.length) {
//            throw new UnsupportedOperationException("Lenths of arrays must be equal. "+ arr1.length +" "+ arr2.length);
//        }
//
//        double delta = (double) Utils.computePixelDifference(arr1, arr2);
//
//        if (delta==0.0)
//            delta = 1.0;
//
//        return out;
    }

    /**
     * Init to rnd values between 0 and 1 in double precision.
     * @param nDims
     * @return dims[], with random values.
     */
    public static double[] initRnd(int nDims) {
        double[] dims = new double[nDims];
        for (int i = 0; i < nDims; i++) {
            dims[i] = Math.random();
        }
        return dims;
    }

    public static void initToZeroes(double[] mean) {
        for (int i = 0; i < mean.length; i++) {
            mean[i] = 0.0;
        }
    }

    /**
     * Matrix to zeroes.
     * @param wordMatrix
     */
    public static void initToZeroes(int[][] wordMatrix) {
        for (int i = 0; i < wordMatrix.length; i++) {
            for (int j = 0; j < wordMatrix[0].length; j++) {
                wordMatrix[i][j] = 0;
            }
        }
    }

    /**
     * Take a matrix and put it in R-readable CVS form.
     * @param matrix this matrix
     * @param cols with this many columns
     * @param rows and rows
     * @param addr save it to this file
     * @throws java.lang.Exception
     */
    public static void exportMatrixToR(int[][] matrix, String[] cols, String[] rows, String addr) throws Exception {
        // is this how you throw exceptions?
        if ((cols.length != matrix.length) || (rows.length != matrix[0].length)) {
            System.err.println("matrix dimensions mismatch");
            throw new Exception("matrix dimensions mismatch");
        }

        try {
            FileWriter fstream = new FileWriter(addr);
            BufferedWriter out = new BufferedWriter(fstream);

            // write header
            String check = "\"row.names\",";
            for (int i = 0; i < cols.length; i++) {
                check += "\"" + cols[i] + "\",";
            }
            check = check.substring(0, check.length() - 1);
            check += "\r\n";
            out.write(check);

            // write matrix
            for (int i = 0; i < matrix.length; i++) {
                check = "";
                check += "\"" + rows[i] + "\",";
                for (int j = 0; j < matrix[0].length; j++) {
                    check += matrix[i][j] + ",";
                }
                check = check.substring(0, check.length() - 1);
                check += "\r\n";
                out.write(check);
            }
            out.close();
        } catch (IOException ex) {
        }
    }

    /**
     * This one gives no labels: the output will be matrix without row or column names.
     * @param matrix
     * @param addr
     * @throws java.lang.Exception
     */
    public static void exportMatrixToR(int[][] matrix, String addr) throws Exception {
        try {
            FileWriter fstream = new FileWriter(addr);
            BufferedWriter out = new BufferedWriter(fstream);
            // write matrix
            for (int i = 0; i < matrix.length; i++) {
                String check = "";
                for (int j = 0; j < matrix[0].length; j++) {
                    check += matrix[i][j] + ",";
                }
                check = check.substring(0, check.length() - 1);
                check += "\r\n";
                out.write(check);
            }
            out.close();
        } catch (IOException ex) {
        }
    }

    /**
     * Initiates a double[] with all zeroes.
     * @param nDims
     * @return
     */
    public static double[] zeroes(int nDims) {
        double[] out = new double[nDims];
        for (int i = 0; i < nDims; i++) {
            out[i] = 0.0d;
        }

        return out;
    }

    static int average(int[] in) {
        int sum = 0;
        for (int i = 0; i < in.length; i++) {
            sum += in[i];
        }
        return sum / in.length;
    }

    /**
     * This searches an UNSORTED collection of points.
     * @param northWest
     * @param tempNorthWestPoints
     * @return
     */
    public static boolean isIn(Point point, Point[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == point) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIn(Point point, ArrayList<Point> arr) {
        return isIn(point, arr.toArray(new Point[arr.size()]));
    }

    public static double max(double[] t) {
        double maximum = t[0];   // start with the first value
        for (int i = 1; i < t.length; i++) {
            if (t[i] > maximum) {
                maximum = t[i];   // new maximum
            }
        }
        return maximum;
    }

    public static double min(double[] t) {
        double min = t[0];   // start with the first value
        for (int i = 1; i < t.length; i++) {
            if (t[i] < min) {
                min = t[i];   // new maximum
            }
        }
        return min;
    }

    /**
     * For sorting an array of points.
     */
    static public class PointComparator implements Comparator<Point> {

        public int compare(Point o1, Point o2) {
            if (o1.x == o2.x) {
                if (o1.y > o2.y) {
                    return 1;
                } else if (o1.y == o2.y) {
                    return 0;
                } else {
                    return -1;
                }
            } else if (o1.x > o2.x) {
                return 1;
            } else {
                return -1;
            }
        }

    }
}
