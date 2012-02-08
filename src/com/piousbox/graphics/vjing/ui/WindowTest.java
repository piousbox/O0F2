/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics.vjing.ui;

import com.piousbox.graphics.Config;
import com.piousbox.graphics.vjing.VJControlFrame;
import com.piousbox.graphics.vjing.VJOutputWindow;
import com.piousbox.graphics.webcam.WebcamBase;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import javax.swing.JFrame;
import javax.swing.JWindow;

/**
 *
 * @author ae1
 */
public class WindowTest {

    Config c;
    VJOutputWindow out;
    VJControlFrame ctrl;
    WebcamBase webcam;

    public WindowTest() {
        c = new Config();
        webcam = new WebcamBase();
        c.setVideoSource(webcam);
        out = new VJOutputWindow(c);
        ctrl = new VJControlFrame(c, out.getPanel());
    }

    public static void main(String[] args) {
        new WindowTest();
    }

}
