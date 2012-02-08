/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics.vjing;

import com.piousbox.graphics.Config;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author ae1
 */
public class VJingKeyListener implements KeyListener {
    OutputPanel panel;
    Config c;

    public VJingKeyListener(OutputPanel panel, Config c) {
        this.panel = panel;
        this.c = c;
    }

    public void keyTyped(KeyEvent ke) {}

    public void keyPressed(KeyEvent ke) {
//        System.out.println(k);
//        // 81 87 69
//        if (k.getKeyCode() == 81) { // q
//            panel.webcamKaleidoscope();
//        } else {
//            panel.resetImage();
//        }
//        if (k.getKeyCode() == 87) { // w
//
//            panel.webcamAscii();
//        } else {
//            panel.resetImage();
//        }
//        // 65 83 68
//        if (k.getKeyCode() == 83) { // s
//
//            panel.flash();
//        }
//
//        if (k.getKeyCode() == 65) { // a
//
//            panel.growForest();
//        }
        System.out.println(ke);

        switch (ke.getKeyChar()) {
            case 'w':
            case 'W':
                panel.filter1 = Filter1.PLAIN;
                break;
            case 'r':
            case 'R':
                panel.filter1 = Filter1.KALEIDOSCOPE;
                break;
            case 'a':
            case 'A':
//                panel.flash();
                break;
            case 't':
            case 'T':
//                panel.printTest();
                break;
            case 's':
            case 'S':
                panel.growForest();
//                if (panel.growForestT) {
//                    panel.growForestT = false;
//                } else {
//                    panel.growForestT = true;
//                }
                break;
            case 'q':
            case 'Q':
                break;
            default:
        }
    }

    public void keyReleased(KeyEvent ke) {}

}
