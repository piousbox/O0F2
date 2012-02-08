/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import com.piousbox.graphics.vjing.Outra.OutWindow.OutPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

class RandomWalk implements Runnable {

        Thread t;
        Graphics2D g2d;
        OutPanel panel;
        Config c = new Config();
        Random rnd = new Random();

        RandomWalk(Graphics2D g2d, OutPanel panel) {
            t = new Thread(this);
            t.stop();

            this.g2d = g2d;
            this.panel = panel;
        }
        boolean randomWalkRun = false;

        public void stop() {
            t.stop();
            randomWalkRun = false;
        }

        private void start() {

            randomWalkRun = true;
            if (t == null) {
                t = new Thread(this);
            }
            if (!t.isAlive()) {
                t.resume();
            }
        }

        public void run() {
            while (randomWalkRun) {
                for (int i = 0; i < c.nRandomWalkers; i++) {
//                    int x = c.w / 2;
//                    int y = c.h / 2;
                    int x = rnd.nextInt(c.w);
                    int y = rnd.nextInt(c.h);
                    int x2 = x;
                    int y2 = y;
                    int x3 = x2;
                    int y4 = y2;
                    while (x < c.w && x > 0 && y < c.h && y > 0) {
                        try {
//                            g2d = panel.clear2(); // clear filter2
                            x2 += rnd.nextInt(c.randomWalkSpeed) * ((rnd.nextBoolean() == true) ? -1 : 1);
                            y2 += rnd.nextInt(c.randomWalkSpeed) * ((rnd.nextBoolean() == true) ? -1 : 1);
                            g2d.setColor(new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                            g2d.setStroke(new BasicStroke(5));
                            g2d.drawLine(x, y, x2, y2);
                            x = x2;
                            y = y2;
                            panel.repaint();
                            Thread.sleep(20);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
//                    panel.filter2 = Filter2.RESET;
//                    t.stop();
                }
            }
        }
    }