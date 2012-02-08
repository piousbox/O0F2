/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.ui;

import com.piousbox.graphics.Config;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author ae1
 */
public class O0ConfigFrame extends JFrame implements ActionListener {
    JButton closeButton, submitButton;
    Config config;
    JTextField p, thresh, nBlobs;

    public O0ConfigFrame(Config config) {
        super("Config Frame");

        this.config = config;
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(250, 250);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setSize(250, 250);

        submitButton = new JButton("Submit");
        closeButton = new JButton("Close");

        submitButton.addActionListener(this);
        closeButton.addActionListener(this);

        p = new JTextField(20);
        thresh = new JTextField(20);
        nBlobs = new JTextField(20);

        p.setText(""+ config.getP());
        thresh.setText(""+ config.getThresh());
        nBlobs.setText(""+ config.getNBlobs());

        p.addActionListener(this);
        thresh.addActionListener(this);
        nBlobs.addActionListener(this);

        panel.add(new Label("Size Of Square"));
        panel.add(p);

        panel.add(new Label("Thresh"));
        panel.add(thresh);

        panel.add(new Label("nBlobs"));
        panel.add(nBlobs);

        panel.add(closeButton);
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);
        pack();
        
    }

    public Config getConfig() {
        return config;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submitButton) {
            config.setP(p.getText());
            config.setThresh(thresh.getText());
            config.setNBlobs(nBlobs.getText());
        }

        if(e.getSource() == closeButton) {
            setVisible(false);
        }
    }

}
