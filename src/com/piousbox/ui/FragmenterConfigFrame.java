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
public class FragmenterConfigFrame extends JFrame implements ActionListener {

    JButton closeButton, submitButton;
    Config config;
    JTextField libAddr;

    public FragmenterConfigFrame(Config config) {
        super("Config Frame");

        this.config = config;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(250, 250);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.setSize(250, 250);

        submitButton = new JButton("Submit");
        closeButton = new JButton("Close");

        submitButton.addActionListener(this);
        closeButton.addActionListener(this);

        libAddr = new JTextField(20);

        libAddr.setText("" + config.getLibInAddr());

        libAddr.addActionListener(this);

        panel.add(new Label("Lib In Addr (absolute)"));
        panel.add(libAddr);

        panel.add(closeButton);
        panel.add(submitButton);

        add(panel, BorderLayout.CENTER);
        pack();

    }

    public Config getConfig() {
        return config;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            config.setLibIn(libAddr.getText());
        }

        if (e.getSource() == closeButton) {
            setVisible(false);
        }
    }
}
