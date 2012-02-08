/**
 * Works 20100804 1240pm
 *
 */
package com.piousbox.graphics.webcam;

import com.piousbox.graphics.VideoSource;
import com.piousbox.ImageUtils;
import com.piousbox.graphics.O0Filter;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ae1
 */
public class WebcamBase extends VideoSource implements Runnable {

    Thread animation;
    int offset = 0;
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;

    public WebcamBase() {

        animation = new Thread(this);
        animation.start();

        try {
            is = new FileInputStream(new File("/dev/video0"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebcamBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public BufferedImage getImage() {
        return image;
    }

    public void run() {
        while (true) {
            try {

                int numread = is.read(bytes);
                offset += numread;

                InputStream in = new ByteArrayInputStream(bytes);
                in.reset();
                image = ImageIO.read(in);

            } catch (IOException ex) {
                Logger.getLogger(WebcamBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
