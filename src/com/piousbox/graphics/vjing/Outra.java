/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics.vjing;

import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.*;
import javax.imageio.ImageIO;
import com.piousbox.ImageUtils;
import com.piousbox.graphics.Config;
import com.piousbox.graphics.kaleidoscope.Triangle;
import com.piousbox.graphics.vjing.Outra.OutWindow.OutPanel;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * This class is a complete small VJing application. Presently, look at Filter1 and Filter2 enums for the list of available effects.
 * @author ae1
 */
public class Outra {

    public enum Filter1 {

        PLAIN, ASCII, KALEIDOSCOPE, STILLFRAME, FLOCK, SCHOOL, BLANK
    }

    public enum Filter2 {

        RESET, RANDOM_WALK, FLASH, FISH, NONE, RANDOM_WALK_STOP, SWARM
    }
    Config c = new Config();
    CtlFrame frame;
    OutWindow window;
    boolean webcamT = false;
    int offset = 0;
    BufferedImage imageSrc = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    byte[] bytes = new byte[640 * 480 * 3];
    InputStream is = null;
    public Random rnd = new Random();

    public Outra() {
        frame = new CtlFrame();
        window = new OutWindow(frame);
        frame.setPanel(window.getPanel());
        frame.attachKeyListener();
//        frame.getContentPane().addKeyListener(new VJKeyListener(window.getPanel()));
    }

    class OutWindow extends java.awt.Window {

        OutPanel panel;

        OutPanel getPanel() {
            return panel;
        }

        OutWindow(CtlFrame frame) {
            super(frame);
            setVisible(true);
//            setBounds(1680, 0, c.w, c.h);
            setBounds(500, 0, c.w, c.h);
            panel = new OutPanel();
            add(panel);
        }

        class OutPanel extends JPanel implements Runnable {

            Thread animation;
            BufferedImage image = new BufferedImage(c.w, c.h, BufferedImage.TYPE_INT_ARGB);
            BufferedImage image2 = new BufferedImage(c.w, c.h, BufferedImage.TYPE_INT_ARGB);
            private ArrayList<RandomWalker> randomWalks = new ArrayList<RandomWalker>(10);
            Swarm swarm;

            OutPanel() {
                try {
                    is = new FileInputStream(new File(c.videoInAddr));
                } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
                    c.log("Exception. webcam isn't plugged in?");
                    filter1 = Filter1.BLANK;
                }

                animation = new Thread(this);
                animation.start();

            }
            boolean run = true;
//            boolean swarmRun = false;
            Filter1 filter1 = Filter1.BLANK;
            Filter2 filter2 = Filter2.RESET;
            Graphics2D g22 = image2.createGraphics();

            public void run() {
                while (run) {
                    setBackground(Color.black);
                    try {

                        switch (filter1) {
                            case BLANK:
                                image = new BufferedImage(c.w, c.h, BufferedImage.TYPE_INT_ARGB);
                                break;
                            case PLAIN:
                                image = imageSrc;
                                break;
                            case KALEIDOSCOPE:
                                webcamInput();
                                image = kaleidoscope(imageSrc);
                                break;
                            case STILLFRAME:
                            default:
                            //
                        }

                        switch (filter2) {
                            case RESET:
                                g22 = clear2();
                                break;
                            case FLASH:
                                for (int i = 0; i < 3; i++) {
                                    g22.setColor(Color.black);
                                    g22.fillRect(0, 0, c.w, c.h);
                                    repaint();
                                    Thread.sleep(100);
                                    g22.setColor(Color.white);
                                    g22.fillRect(0, 0, c.w, c.h);
                                    repaint();
                                    Thread.sleep(50);
                                }
                                filter2 = Filter2.RESET;
                                break;
                            case FISH:
                                new Aquarium(g22, this);
                                break;
                            case RANDOM_WALK:
                                if (panel.randomWalks.size() > 0) {
                                    for (RandomWalker walk : randomWalks) {
                                        walk.stop();

                                    }
                                    panel.randomWalks.clear();
                                } else {
                                    panel.randomWalks.add(new RandomWalker(panel.g22, panel));
                                }

                                filter2 = Filter2.RESET;

//                                for (int i = 0; i < c.nRandomWalkers; i++) {
//                                    randomWalks.add(new RandomWalker(g22, this));
//                                }

//                                filter2 = Filter2.RESET;
                                break;
                            case RANDOM_WALK_STOP:
                                for (RandomWalker walk : randomWalks) {
                                    walk.stop();
                                }
                                filter2 = Filter2.RESET;
                                break;
                            case SWARM:
                                if (swarm == null) {
                                    swarm = new Swarm(g22, this);
                                } else {
                                    swarm.stop();
                                    swarm = null;
                                }

                                filter2 = Filter2.NONE;
                                break;
                            default:
                            case NONE:
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    repaint();
                }
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(image, 0, 0, null);
                g2d.drawImage(image2, 0, 0, null);
            }

            int rndX = c.w / 2;
            boolean rndXMovingLeft = true;

            private BufferedImage kaleidoscope(BufferedImage in) {
                in = ImageUtils.scale(in, c.w / 2);

                int rndXDelta = rnd.nextInt(8);
                if (rndXMovingLeft) {
                    rndX -= rndXDelta;
                } else {
                    rndX += rndXDelta;
                }
                if (rndX <= 0) {
                    rndX = 1;
                    rndXMovingLeft = false;
                }
                if (rndX > in.getWidth() - 200) { // the 200 is there b/c I'm counting from the top-left corner, not from the middle for the constructed triangle.
                    rndX = in.getWidth() - 200;
                    rndXMovingLeft = true;
                }

                in = Triangle.constructTriangle(in, rndX);

                BufferedImage out = new BufferedImage(c.w, c.h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = (Graphics2D) out.getGraphics();

                g2d.translate(c.w / 2, c.h / 2);

                for (int i = 0; i < 6; i++) {
                    g2d.drawImage(in, 0, 0, this);
                    g2d.rotate(Math.PI / 3);
                }

                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-in.getWidth(), 0);
                AffineTransformOp op = new AffineTransformOp(tx,
                        AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                BufferedImage flipImage = op.filter(in, null);

                for (int i = 0; i < 6; i++) {

                    g2d.drawImage(flipImage, -flipImage.getWidth(), 0, flipImage.getWidth(), flipImage.getHeight(), this);
                    g2d.rotate(Math.PI / 3);
                }


                return out;
            }

            private Graphics2D clear2() {
                image2 = new BufferedImage(c.w, c.h, BufferedImage.TYPE_INT_ARGB);
                return image2.createGraphics();
            }

            private BufferedImage webcamInput() {
                BufferedImage imageSrc = null;
                InputStream in = null;
                try {
                    int numread = is.read(bytes);
                    offset += numread;
                    in = new ByteArrayInputStream(bytes);
                    in.reset();
                    imageSrc = ImageIO.read(in);

                } catch (IOException ex) {
                    Logger.getLogger(Outra.class.getName()).log(Level.SEVERE, null, ex);
                }
                return imageSrc;
            }
        }
    }

    class Swarm implements Runnable {

        Thread ttt;
        OutPanel panel;
        Graphics2D g2d;
        Agent[] agents = new Agent[c.nSwarmAgents];
        Point center = new Point(c.w / 2, c.h / 2);
        boolean runT = true;

        Swarm(Graphics2D g2d, OutPanel panel) {
            this.panel = panel;
            this.g2d = g2d;

            for (int i = 0; i < c.nSwarmAgents; i++) {
                agents[i] = new Agent();
            }
            ttt = new Thread(this);
            ttt.start();

        }

        private void updateCenter() {
            int tempX = 0;
            int tempY = 0;
            for (int i = 0; i < agents.length; i++) {
                tempX += agents[i].p2.x;
                tempY += agents[i].p2.y;
            }
            tempX /= 0.0 + agents.length;
            tempY /= 0.0 + agents.length;

            tempX += rnd.nextInt(c.swarmVolatility * 2) - c.swarmVolatility;
            tempY += rnd.nextInt(c.swarmVolatility * 2) - c.swarmVolatility;

            center.x = tempX;
            center.y = tempY;
        }

        class Agent {

            Point p0, p1, p2;
            Color color;

            Agent() {
                p2 = new Point(rnd.nextInt(c.w), rnd.nextInt(c.h));
                p1 = new Point(rnd.nextInt(c.w), rnd.nextInt(c.h));
                p0 = new Point(rnd.nextInt(c.w), rnd.nextInt(c.h));
            }

            void move(Point center, Agent[] agents, int thisAgentIdx) {
                p0 = new Point(p1.x, p1.y);
                p1 = new Point(p2.x, p2.y);

                int a = p2.x - center.x;
                int b = p2.y - center.y;
                if (a == 0) {
                    a = 1;
                }
                if (b == 0) {
                    b = 1;
                }
                int s = c.swarmSpeed;

                double tanTh = b / a;
                double th = Math.atan(tanTh);
                double sinTh = Math.sin(th);

                int d = (int) Math.sqrt(s * s - (s * sinTh) * (s * sinTh));
                int e = (int) Math.abs(s * sinTh);

                if (a > 0) {
                    d = -d;
                }
                if (b > 0) {
                    e = -e;
                }

                p2.x += d;
                p2.y += e;

                if (p2.x < 0) {
                    p2.x = c.w + p2.x;
                }
                if (p2.x > c.w) {
                    p2.x = c.w - p2.x;
                }
                if (p2.y < 0) {
                    p2.y = c.h + p2.y;
                }
                if (p2.y > c.h) {
                    p2.y = c.h - p2.y;
                }


            }

            void draw(Graphics2D g2d) {
                color = new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                g2d.setColor(color);
                g2d.fillOval(p2.x - 10, p2.y - 10, 20, 20);
                g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                g2d.drawLine(p0.x, p0.y, p1.x, p1.y);
            }
        }

        void start() {
            ;
        }

        void stop() {
            ttt.stop();
            ttt = null;
            panel.clear2();
        }

        public void run() {
            while (runT) {
                try {
                    for (int i = 0; i < c.nSwarmAgents; i++) {
                        agents[i].move(center, agents, i);
                        agents[i].draw(g2d);
                    }
                    updateCenter();
                    panel.repaint();
                    Thread.sleep(120);
                    g2d = panel.clear2();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class RandomWalker implements Runnable {

        Graphics2D g2d;
        OutPanel panel;
        Thread aa;
        boolean runn = true;

        private RandomWalker(Graphics2D g22, OutPanel panel) {
            this.g2d = g22;

            this.panel = panel;

            aa = new Thread(this);
            aa.start();
        }

        public void run() {
            int x0 = rnd.nextInt(c.w);
            int y0 = rnd.nextInt(c.h);
            int x1 = x0;
            int y1 = y0;
            int x2 = x1;
            int y2 = y1;
            int x3 = x2;
            int y3 = y2;

            while (runn) {
                try {

                    x3 += rnd.nextInt(c.randomWalkSpeed) * ((rnd.nextBoolean() == true) ? -1 : 1);
                    y3 += rnd.nextInt(c.randomWalkSpeed) * ((rnd.nextBoolean() == true) ? -1 : 1);
                    if (x3 >= c.w) {
                        x3 -= c.w;
                    }
                    if (x3 <= 0) {
                        x3 = c.w + x3;
                    }
                    if (y3 >= c.h) {
                        y3 -= c.h;
                    }
                    if (y3 <= 0) {
                        y3 = c.h + y3;
                    }
                    g2d.setStroke(new BasicStroke(15, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND));
                    g2d.setColor(new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
                    g2d.drawLine(x0, y0, x1, y1);
                    g2d.drawLine(x1, y1, x2, y2);
                    g2d.drawLine(x2, y2, x3, y3);
                    g2d.drawLine(x2, y2, x3, y3);

                    panel.repaint();
                    Thread.sleep(20);

                    g2d = panel.clear2();

                    x0 = x1;
                    y0 = y1;

                    x1 = x2;
                    y1 = y2;

                    x2 = x3;
                    y2 = y3;


                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

//                    runn = false;
//                    panel.filter2 = Filter2.RESET;
//                    aa.stop();
            }


        }

        private void stop() {
            runn = false;
            aa.stop();
        }
    }

    class Aquarium implements Runnable {

        Graphics2D g22;
        OutPanel panel;
        Thread aa;

        private Aquarium(Graphics2D g22, OutPanel panel) {
            this.g22 = g22;
            this.panel = panel;

            aa = new Thread(this);
            aa.start();
        }
        boolean runn = true;

        public void run() {
            while (runn) {
                for (int i = 0; i < c.nGoldfish; i++) {
                    try {
                        new Fish(g22, panel);
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Outra.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                panel.filter2 = Filter2.RESET;
//                panel.filter2 = Filter2.FLASH;
                runn = false;
            }
        }

        class Fish implements Runnable {

            Thread t;
            Graphics2D g22;
            OutPanel panel;

            private Fish(Graphics2D g22, OutPanel panel) {
                t = new Thread(this);
                t.start();

                this.g22 = g22;
                this.panel = panel;
            }
            boolean ta = true;

            public void run() {
                while (ta) {
                    String tempAddr = (rnd.nextBoolean() == true) ? "goldfish.png" : "goldfish_flip.png";
                    try {
                        int x = rnd.nextInt(c.w) - 100;
                        int y = rnd.nextInt(c.h) - 78;
                        g22.drawImage(ImageUtils.loadBufferedImage(c.imgLib + tempAddr), x, y, null);
                        panel.repaint();
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    ta = false;
                }
            }
        }
    }

    class CtlFrame extends JFrame implements ActionListener {

        JButton submit;
        JTextField label;
        JTextArea mainTextArea;
        OutPanel panel;

        CtlFrame() {
            this.setLayout(new BorderLayout());

            setSize(200, 200);
            setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel swarmPanel = new JPanel();
//            JPanel mainPanel = new JPanel();

            submit = new JButton("nSwarmAgents");
            label = new JTextField("nSwarmAgents");
            label.setPreferredSize(new Dimension(100, 20));

            mainTextArea = new JTextArea("main\nmain\nmain");
            mainTextArea.setRows(3);
            mainTextArea.setSize(200, 200);
            mainTextArea.setBackground(Color.red);

            swarmPanel.add(submit);
            swarmPanel.add(label);

            submit.addActionListener(this);

            this.getContentPane().add(swarmPanel, BorderLayout.CENTER);
            this.getContentPane().add(mainTextArea, BorderLayout.NORTH);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {
                c.nSwarmAgents = Integer.parseInt(label.getText());
            }
        }

        public void keyTyped(KeyEvent ke) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void keyPressed(KeyEvent ke) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void keyReleased(KeyEvent ke) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void setPanel(OutPanel panel) {
            this.panel = panel;
        }

        private void attachKeyListener() {
            mainTextArea.addKeyListener(new VJKeyListener(panel));
        }
    }

    class VJKeyListener implements KeyListener {

        OutPanel panel;

        private VJKeyListener(OutPanel panel) {
            this.panel = panel;
        }

        public void keyTyped(KeyEvent ke) {
        }

        public void keyPressed(KeyEvent ke) {
//            System.out.println(ke);

            switch (ke.getKeyChar()) {
                /////////////////////// these are streams
                case 'q':
                case 'Q':
                    panel.filter1 = Filter1.PLAIN;
                    break;
                case 'w':
                case 'W':
                    panel.filter1 = Filter1.ASCII;
                    break;
                case 'e':
                case 'E':
                    panel.filter1 = Filter1.KALEIDOSCOPE;
                    break;
                case 'r':
                case 'R':
                    panel.filter1 = Filter1.FLOCK;
                    break;
                case 't':
                case 'T':
                    panel.filter1 = Filter1.SCHOOL;
                    break;
                /////////////////////// these are more like still frames
                case 'a':
                case 'A':
                    panel.filter2 = Filter2.RESET;
                    break;
                case 's':
                case 'S':
                    panel.filter2 = Filter2.FLASH;
                    break;
                case 'd':
                case 'D':
                    panel.filter2 = Filter2.FISH;
                    break;
                case 'f':
                case 'F':
                    panel.filter2 = Filter2.RANDOM_WALK;
//                    panel.randomWalks.add(new RandomWalker(panel.g22, panel));
                    break;
                case 'g':
                case 'G':
//                    panel.filter2 = Filter2.RANDOM_WALK_STOP;
                    break;
                case 'h':
                case 'H':
                    panel.filter2 = Filter2.SWARM;
                    break;
                default:
            }
        }

        public void keyReleased(KeyEvent ke) {
        }
    }

    public static void main(String[] args) {
        new Outra();



    }
}
