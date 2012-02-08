/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import com.piousbox.graphics.VideoSource;
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
public class VJOutputWindow extends JWindow implements Runnable {

    BufferedImage image;
    int w, h;
    Random rnd = new Random();
    Config c;
    OutputPanel panel = null;
    VideoSource videoSource;

    Thread animation;

    public VJOutputWindow(Config config) {
        c = config;

        setVisible(true);
//        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        setBounds(1680, 0, c.w, c.h);

        panel = new OutputPanel(c);

        this.getContentPane().add(panel);

        animation = new Thread(this);
        animation.start();
    }

    public OutputPanel getPanel() {
        return panel;
    }

    public void run() {
        ;
    }
}
