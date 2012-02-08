/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import com.piousbox.graphics.vjing.OutputPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 *
 * @author ae1
 */
public class VJControlFrame extends JFrame implements Runnable, ActionListener {

    Config c;
    Thread animation;
    OutputPanel panel;

    public VJControlFrame(Config config, OutputPanel panel) {
        super("ae1VJing - Control");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 250);
        setVisible(true);

        animation = new Thread(this);
        animation.start();

        c = config;
        this.addKeyListener(new VJingKeyListener(panel, c));
    }

    public void actionPerformed(ActionEvent ae) {}

    public void run() {}

}
