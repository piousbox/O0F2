/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import com.piousbox.graphics.webcam.WebcamBase;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestUI extends JFrame implements KeyListener, Runnable {

    Config c = new Config();
    WebcamBase webcam;
    Thread animation;
    InnerPanel panel = new InnerPanel();

    public TestUI() {
        setSize(200, 200);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().add(panel);

        webcam = new WebcamBase();

        animation = new Thread(this);
        animation.start();
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        c.log("" + ke);
    }

    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public void run() {
        repaint();
        panel.repaint();
    }

    class InnerPanel extends JPanel implements Runnable {

        Thread animation;

        public InnerPanel() {
            animation = new Thread(this);
            animation.start();
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(webcam.getImage(), 0, 0, null);
//            g.drawString("Hello, world!", 50, 50);
        }

        @Override
        public void update(Graphics g) {
            g.drawImage(webcam.getImage(), 0, 0, null);
        }

        public void run() {
            repaint();
            c.log("run");
        }
    }

    public static void main(String[] args) {
        new TestUI();
    }
}

