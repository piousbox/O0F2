package com.piousbox.ui.console;

import com.piousbox.ImageUtils;
import com.piousbox.graphics.Config;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Isolines {

    /**
     * @Deprecated
     * @param pointIn
     * @param bimIn
     * @param config
     * @return
     */
    private static Point nextInterestingPoint(Point pointIn, BufferedImage bimIn, Config config) {
        int step = config.getStepSize();
        if (pointIn.x <= step || pointIn.x >= bimIn.getWidth() - step
                || pointIn.y <= step || pointIn.y >= bimIn.getHeight() - step) {
//            config.log(("I've reached an edge of the canvas"));
            return pointIn;
        }
        Point out = null;
        double maxContrast = 0.0;

        for (int i = 0; i < config.getWheelResolution(); i++) {
            double angle = config.getWheelSlice() * i;
            int x = (int) (pointIn.x + Math.sin(angle) * step);
            int y = (int) (pointIn.y + Math.cos(angle) * step);
            Point temp = new Point(x, y);

            /**
             * Compute the contrast at this point.
             */
            double tempContrast = 0.0;
            try {
                for (int pointOutX = temp.x - step; pointOutX < temp.x + step; pointOutX++) {
                    for (int pointOutY = temp.y - step; pointOutY < temp.y + step; pointOutY++) {
                        double tempDiff = ImageUtils.diff(bimIn.getRGB(pointOutX, pointOutY),
                                bimIn.getRGB(temp.x, temp.y));
                        tempContrast += tempDiff;
                    }
                }
                if (tempContrast > maxContrast) {
                    out = temp;
                    maxContrast = tempContrast;
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }

        }


        return out;
    }

    private static Point nextInterestingPoint(Point p1, Point p2, BufferedImage bimIn, Config config) {
        int step = config.getStepSize();
        if (p2.x <= step || p2.x >= bimIn.getWidth() - step
                || p2.y <= step || p2.y >= bimIn.getHeight() - step) {
//            config.log(("I've reached an edge of the canvas"));
            return p2;
        }
        Point out = null;
        double maxContrast = 0.0;

        for (int i = 0; i < config.getNRndPoints(); i++) {
            double angle = config.getWheelSlice() * i;
            int x = (int) (p2.x + Math.sin(angle) * step);
            int y = (int) (p2.y + Math.cos(angle) * step);
            Point temp = new Point(x, y);

            /**
             * Compute the contrast at this point.
             */
            double tempContrast = 0.0;
            try {
                for (int pointOutX = temp.x - step; pointOutX < temp.x + step; pointOutX++) {
                    for (int pointOutY = temp.y - step; pointOutY < temp.y + step; pointOutY++) {
                        double tempDiff = ImageUtils.diff(bimIn.getRGB(pointOutX, pointOutY),
                                bimIn.getRGB(temp.x, temp.y));
                        tempContrast += tempDiff;
                    }
                }
                if (tempContrast > maxContrast && dissimilar(temp, p1, step)) {
                    out = temp;
                    maxContrast = tempContrast;
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }

        }


        return out;
    }

    private static boolean dissimilar(Point p0, Point p1, int step) {
        int xDiff = Math.abs(p0.x - p1.x);
        int yDiff = Math.abs(p0.x - p1.y);
        if (xDiff > step*0.66 && yDiff > step*0.66) {
            return true;
        }
        return false;
    }

    public Isolines() {
        Config config = new Config();
        config.setConsoleUI(true);
        int step = config.getStepSize();
        Random rnd = new Random();
        BufferedImage bimIn = ImageUtils.loadBufferedImage(config.getTestImageAddr());
        BufferedImage bimOut = new BufferedImage(bimIn.getWidth(), bimIn.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bimOut.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, bimIn.getWidth(), bimIn.getHeight());
        
        /**
         * for everything.
         */
        try {
            for (int e = 0; e < 10000; e++) {
                g.setColor(new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
                /**
                 * Take a random point on the image.
                 */
                Point p1 = new Point(rnd.nextInt(bimIn.getWidth() - step * 2) + step,
                        rnd.nextInt(bimIn.getHeight() - step * 2) + step);
                Point p2 = Isolines.nextInterestingPoint(p1, bimIn, config);

                for (int i = 0; i < config.getNSteps(); i++) {
                    Point p3 = Isolines.nextInterestingPoint(p1, p2, bimIn, config);
                    p1 = p2;
                    p2 = p3;
                    try {
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);

                    } catch (Exception ex) {
                        ;
                    }
                }
//                ImageUtils.saveImage(bimOut, config.getImgOutAddr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * for the face.
         */
        try {
            for (int e = 0; e < 10000; e++) {
                /**
                 * Take a random point on the image.
                 */
                Point p1 = new Point(rnd.nextInt(186) + 120, rnd.nextInt(200) + 200);
                Point p2 = Isolines.nextInterestingPoint(p1, bimIn, config);

                for (int i = 0; i < config.getNSteps(); i++) {
                    Point p3 = Isolines.nextInterestingPoint(p1, p2, bimIn, config);
                    p1 = p2;
                    p2 = p3;
                    try {
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);

                    } catch (Exception ex) {
                        ;
                    }
                }
//                ImageUtils.saveImage(bimOut, config.getImgOutAddr());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.dispose();
        ImageUtils.saveImage(bimOut, config.getImgOutAddr());

    }

    public static void main(String[] args) {
        new Isolines();
    }
}
