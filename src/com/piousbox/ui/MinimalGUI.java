package com.piousbox.ui;

import com.piousbox.graphics.ImageFrame;
import com.piousbox.graphics.*;
import com.piousbox.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

/**
 * MinimalGUI GUI. Most controls are inside JMenuBar.
 * @author ae1
 */
public class MinimalGUI extends JFrame implements ActionListener, ItemListener {

    JFileChooser fc;
    JPanel contentPanel;
    JMenuBar menuBar;
    JMenu mainMenu, filtersMenu;
    JMenuItem loadSourceImageMenuItem, loadSourceLibraryMenuItem, exitMenuItem;
    /**
     * Saving any output
     */
    JMenuItem saveImageMenuItem;
    JMenuItem o0FilterMenuItem, o0FilterConfigMenuItem, o0_edge_menuItem;
    JMenuItem fitFragmenterMenuItem;
    JMenuItem randomShuffleMenuItem, randomShuffleConfigMenuItem;
    /**
     * This one, draw the vertical and horizontal lines on the src img, to
     * divide it into pieces each of which will be compared to the entire library.
     */
    JMenuItem fragmenterPartitionSourceImageMenuItem;
    BufferedImage sourceImage;
    O0ConfigFrame o0ConfigFrame;
    FragmenterConfigFrame fragmenterConfigFrame;
    JDesktopPane jdpDesktop; // JInternalFrame's are inside it.
    ArrayList<JInternalFrame> ImageOutJIFArr = new ArrayList<JInternalFrame>(10);
    private JMenuItem fragmentDirMenuItem;
    private JMenuItem configureFragmenterMenuItem;
    O0Filter clusteredImage; // o0-filter
    /**
     * log output.
     */
    static private final String newline = "\n";
    static private final String nl = newline;
    JTextArea output;
    JScrollPane scrollPane;
    Config config;
    Fragmenter fragmenter = null;
    ImageFrame sourceImageFrame;
    /**
     * In case I'll miss something later, this is the master switch to turn off all debugging.
     */
    private boolean debug;

    public MinimalGUI() {
        super("Minimal GUI for Faulty Filters");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        config = new Config();
        fragmenter = new Fragmenter(config);

        fc = new JFileChooser();
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

//        int inset = 300;
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setBounds(inset, inset, screenSize.width - inset * 2,
//                screenSize.height - inset * 2);

        setContentPane(createContentPane());
        setJMenuBar(createMenuBar());

//        createFrame(); // Create first window

        setSize(500, 500);
        setVisible(true);

        config.setLog(output);

        debug = config.getDebug();

        if (debug) {
            config.initFragmenterTest();
            initTest();
        }
    }

    public Container createContentPane() {
        jdpDesktop = new JDesktopPane();
        jdpDesktop.putClientProperty("JDesktopPane.dragMode", "outline");

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        contentPane.add(jdpDesktop, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.SOUTH);

        return contentPane;
    }

    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex + 1);
    }

    public JMenuBar createMenuBar() {
        menuBar = new JMenuBar();

        mainMenu = new JMenu("Main Menu");
        mainMenu.setMnemonic(KeyEvent.VK_A);

        filtersMenu = new JMenu("Filters");

        loadSourceImageMenuItem = new JMenuItem("Load Source Image");
        loadSourceLibraryMenuItem = new JMenuItem("Load Source Library");
        saveImageMenuItem = new JMenuItem("Save Image");
        exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_T);

        o0FilterMenuItem = new JMenuItem("o0-filter");
        o0FilterConfigMenuItem = new JMenuItem("o0-filter config");
        fitFragmenterMenuItem = new JMenuItem("Fit Fragmenter to Image");
        fragmentDirMenuItem = new JMenuItem("Fragment Directory");
        configureFragmenterMenuItem = new JMenuItem("Configure Fragmenter");
        fragmenterPartitionSourceImageMenuItem = new JMenuItem("Partition Source Image");
//        o0_edge_menuItem = new JMenuItem("O0F3 (edge)");

        loadSourceImageMenuItem.addActionListener(this);
        loadSourceLibraryMenuItem.addActionListener(this);
        saveImageMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        o0FilterMenuItem.addActionListener(this);
        o0FilterConfigMenuItem.addActionListener(this);
//        o0_edge_menuItem.addActionListener(this);



        fitFragmenterMenuItem.addActionListener(this);
        fragmentDirMenuItem.addActionListener(this);
        configureFragmenterMenuItem.addActionListener(this);
        fragmenterPartitionSourceImageMenuItem.addActionListener(this);


        /**
         *
         */
        this.randomShuffleMenuItem = new JMenuItem("Random Shuffle");
        randomShuffleMenuItem.addActionListener(this);

        this.randomShuffleConfigMenuItem = new JMenuItem("Random Shuffle Config");
        randomShuffleConfigMenuItem.addActionListener(this);

        mainMenu.add(loadSourceImageMenuItem);
        mainMenu.add(loadSourceLibraryMenuItem);
        mainMenu.addSeparator();
        mainMenu.add(saveImageMenuItem);
        mainMenu.addSeparator();
        mainMenu.add(exitMenuItem);

        filtersMenu.add(o0FilterMenuItem);
        filtersMenu.add(o0FilterConfigMenuItem);
        filtersMenu.addSeparator();
        filtersMenu.add(fitFragmenterMenuItem);
        filtersMenu.add(fragmenterPartitionSourceImageMenuItem);
        filtersMenu.add(fragmentDirMenuItem);
        filtersMenu.add(configureFragmenterMenuItem);
        filtersMenu.addSeparator();
        filtersMenu.add(this.randomShuffleMenuItem);
        filtersMenu.add(this.randomShuffleConfigMenuItem);
        filtersMenu.addSeparator();
//        filtersMenu.add(o0_edge_menuItem);
        mainMenu.addSeparator();

        menuBar.add(mainMenu);
        menuBar.add(filtersMenu);
        return menuBar;
    }

    public static void main(String[] args) {
        MinimalGUI frame = new MinimalGUI();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        /**
         * Load source image.
         */
        if (e.getSource() == loadSourceImageMenuItem) {
            sourceImage = loadImage();
        }

        /**
         * save image
         */
        if (e.getSource() == saveImageMenuItem) {
            ImageFrame selectedInternamFrame = (ImageFrame) this.jdpDesktop.getSelectedFrame();
            ImageUtils.saveImage(selectedInternamFrame.getImage(), this);
        }

        /**
         * Apply o0-filter
         */
        if (e.getSource() == o0FilterMenuItem) {
            config.log("Applying o0 filter");
            clusteredImage = new O0Filter(sourceImage, config);
            new ImageFrame(jdpDesktop, config, clusteredImage.getImageOut());
        }

        /**
         * Change configuration for o0-filter
         */
        if (e.getSource() == o0FilterConfigMenuItem) {
            if (o0ConfigFrame == null) {
                o0ConfigFrame = new O0ConfigFrame(config);
            }

            o0ConfigFrame.setVisible(true);
            config = o0ConfigFrame.getConfig();
        }

        if (e.getSource() == this.configureFragmenterMenuItem) {
            checkExistsLibInAddr();
            fragmenterConfigFrame.setVisible(true);
        }

        /**
         * Process o0_fractal library
         */
        if (e.getSource() == this.fragmentDirMenuItem) {
            checkExistsLibInAddr();
//            config.log("Fragmenting directory "+ config.getLibInAddr());
            fragmenter.fragmentFolder(new File(config.getLibInAddr()));
        }

        /**
         * partition sourceImage for fragmenter.
         */
        if (e.getSource() == this.fragmenterPartitionSourceImageMenuItem) {
//            log("Left click for --- horizontal,\nRight click for ||| vertical.");

            if (sourceImage == null) {
                sourceImage = loadImage();
            }

            getSelectedJIF().getImagePanel().toggleIsDrawLimitedLines();
        }

        /**
         * fitFragmenterMenuItem
         */
        if (e.getSource() == this.fitFragmenterMenuItem) {
            checkExistsLibInAddr();

            getSelectedJIF().fitFragmenter();
        }

        if (e.getSource() == randomShuffleMenuItem) {
            BufferedImage img = getSelectedJIF().getImage();
            BufferedImage out = fragmenter.shuffleRandomBits(img);
            getSelectedJIF().getImagePanel().draw(out, new Point(0, 0));
        }

    }

    /**
     * Returns the selected internal frame.
     * @return
     */
    private ImageFrame getSelectedJIF() {
        ImageFrame jif = (ImageFrame) this.jdpDesktop.getSelectedFrame();
        return jif;
    }

    public void itemStateChanged(ItemEvent ie) {
        //
    }

    /**
     * Loads the image to be manipulated.
     */
    private BufferedImage loadImage() {
        sourceImageFrame = new ImageFrame(jdpDesktop, config);
        BufferedImage img = sourceImageFrame.getImage();
        return img;
    }

    /**
     * For framgenter, partitioner and fitter.
     */
    private void checkExistsLibInAddr() {
        if (fragmenterConfigFrame == null) {
            fragmenterConfigFrame = new FragmenterConfigFrame(config);
        }
    }

    private void initTest() {
        sourceImage = ImageUtils.loadBufferedImage(config.getSourceImageAddr());
        sourceImageFrame = new ImageFrame(jdpDesktop, config, sourceImage);

//        getSelectedJIF().getImagePanel().toggleIsDrawLimitedLines();

        checkExistsLibInAddr();

//        fragmenter.clearSlices();
        fragmenter.fragmentFolder(new File(config.getLibInAddr()));

//        getSelectedJIF().fitFragmenter();
    }
}



