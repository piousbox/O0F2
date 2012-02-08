/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import com.piousbox.MatrixUtils;
import com.piousbox.graphics.Config;
import com.piousbox.graphics.Fragmenter;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * J Internal frame for all the images. Every image, src and fin and whatnot.
 * @author ae1
 */
public class ImageFrame extends JInternalFrame implements Observer, ActionListener, MouseListener, MouseMotionListener, ItemListener {

    BufferedImage sourceImage;
    ImagePanel imagePanel;
    Config config;

    public ImagePanel getImagePanel() {
        return imagePanel;
    }

    public ImageFrame(JDesktopPane jdpDesktop, Config config, BufferedImage img) {
        super("Image", true, true, true, true);

        sourceImage = img;
        this.config = config;

        /**
         * @TODO: resize img to fit the screen?
         */
        setSize(sourceImage.getWidth(), sourceImage.getHeight());
        setVisible(true);
        imagePanel = new ImagePanel(sourceImage, config);
        add(imagePanel, BorderLayout.CENTER);
        jdpDesktop.add(this);

        try {
            setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ImageFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public ImageFrame(JDesktopPane jdpDesktop, Config config) {
        this(jdpDesktop, config, ImageUtils.loadBufferedImage());
    }

    public BufferedImage getImage() {
        return sourceImage;
    }

    public void actionPerformed(ActionEvent ae) {
        //
    }

    public void mouseClicked(MouseEvent me) {
        //
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        //
    }

    public void mouseEntered(MouseEvent me) {
        //
    }

    public void mouseExited(MouseEvent me) {
        //
    }

    public void mouseDragged(MouseEvent me) {
        //
    }

    public void mouseMoved(MouseEvent me) {
        //
    }

    public void itemStateChanged(ItemEvent ie) {
        //
    }

    public void fitFragmenter() {
        ImageLibrary lib = new ImageLibrary(config);

        Frag[] frags = Fragmenter.imageToFrags(imagePanel.getImage(), config.getSourceImageFragsPerWidth(), config.getNRndPartitions());

//        BufferedImage[] partitions = Fragmenter.imageToPartitions(imagePanel.getImage(), config.getSourceImageFragsPerWidth());
//        RunnableFragmenter runnableFragmenter = new RunnableFragmenter(config, imagePanel, this, lib, partitions);
        RunnableFragmenter runnableFragmenter = new RunnableFragmenter(config, imagePanel, this, lib, frags);
    }

    public void update(Observable o, Object arg) {
        Frag frag = (Frag) arg;
        imagePanel.draw(frag.getSubImg(), frag.getNwPoint());
    }
}
