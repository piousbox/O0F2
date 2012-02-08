/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author ae1
 */
class VJOutputFrame extends JWindow {

    BufferedImage image;
    int w, h;
    Random rnd = new Random();
    Config c;
    OutputPanel panel = null;
    Graphics2D imageGraphics;

    public VJOutputFrame(Config config) {
        c = config;
//        super(title);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
//        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        boolean debug1 = true;
        if (debug1) {
            w = 640;
            h = 480;
        } else {
            w = 1680;
            h = 1080;
        }

        setBounds(1680, 0, w, h);

        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        imageGraphics = image.createGraphics();


        panel = new OutputPanel();
        this.getContentPane().add(panel);

//        JPanel contentPane = new JPanel(new BorderLayout());
//
//        this.getContentPane().add(contentPane);
    }

    @Override
    public void update(Graphics g) {
    }

    public JPanel getPanel() {
        return panel;
    }

    class OutputPanel extends JPanel implements Runnable {

        public static final int FLASH_WHITE_3 = 1;
        private int which = 0;
        int ms = 50;
        int randomSize = 100;
        Thread animation;
        boolean flag1 = false;

        OutputPanel() {
            animation = new Thread(this);
            animation.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
//            Graphics2D g2d = (Graphics2D) g;
            g.drawImage(image, 0, 0, this);


//            if (flag1) {
//                g2d.setColor(new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
//                g2d.fillRect(0, 0, w, h);
//                flag1 = false;
//            }



//            switch (which) {
//                case FLASH_WHITE_3:
//                    g2d.setColor(new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
//                    g2d.fillRect(0, 0, w, h);
//                    System.out.println("flashBW3 ");
//                    try {
//                        Thread.sleep(ms);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(VJOutputFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    which = 0;
//                    break;
//                case 0:
//                default:
////                    g2d.setColor(Color.black);
////                    g2d.fillRect(0, 0, w, h);
////                    System.out.println("paint black");
//            }





//            for (int i = 0; i < 200; i++) {
//                int x = rnd.nextInt(w - randomSize);
//                int y = rnd.nextInt(h - randomSize);
//                g2d.setColor(new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
//                g2d.fillOval(x, y, randomSize, randomSize);
//            }
        }

        @Override
        public void update(Graphics g) {
            paint(g);
        }

        public void run() {
            while (true) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(VJOutputFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                repaint();
            }
        }

        @SuppressWarnings("static-access")
        void flashBW3() {
            for (int i = 0; i < 3; i++) {
                imageGraphics.setColor(Color.white);
                imageGraphics.fillRect(0, 0, w, h);

                rerepaint();
                sleep(100);

                imageGraphics.setColor(Color.black);
                imageGraphics.fillRect(0, 0, w, h);

                rerepaint();
                sleep(100);
            }
        }

        void treeGrow() {
            int nTrees = 5;

            // for each tree:
//            for (int i = 0; i < nTrees; i++) {
//                new AvatarTree(imageGraphics, w, h, rnd, this, i, nTrees);
//            }
        }

        private void rerepaint() {
            Graphics g = getGraphics();
            if (g != null) {
                paintComponent(g);
            } else {
                repaint();
            }
        }

        private void sleep(int i) {
            try {
                animation.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(VJOutputFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
