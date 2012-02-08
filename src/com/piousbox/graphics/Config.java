/*
 * 20091201 8:07pm Start work on entry (fragmeister2, FFT).
 * 
 */
package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.MatrixUtils;
import com.piousbox.graphics.vjing.WebcamKeyListener;
import com.piousbox.graphics.webcam.WebcamBase;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

/**
 * 
 * @author ae1
 */
public class Config implements Observer {

    String dataDir = "/home/ae1/data/";
    static private final String newline = "\n";
    static private final String nl = newline;
    /**
     * Grow forest
     * @param libAddr
     * @return
     */
    public int drawingTime = 250;
    public int leftRightPadding = 30;
    public int bottomPadding = 30;
    public int rowSpacing = 20;
    public int nRows = 1;
    public int nTreesPerRow = 6;

    public static File getSlicesDir(String libAddr) {
        File out = null;
        if (libAddr.endsWith("/")) {
            out = new File(libAddr + "slices/");
        } else {
            out = new File(libAddr + "/slices/");
        }
        return out;
    }
    //
    // clusterimage (I think)
    //
    public int lumThresh = 100; // how bright is the edge?
    public int wheelSize = 50; // pixel size of the searching spotlight (the 8-part wheel)
    public int sides = 6; // how many sides in a QUARTER of the wheel? give even number
    public int spotSize = 8; // should be less than some reasonable amoun, else be overlap
    public int runOk = 8250; // how many lines to draw?
    public String addr1 = "/home/ae1/data/Leite.LOVE/scaled_99.jpg";
    public String addr2 = "/home/ae1/data/2/7.png";
    public String dirOut = "/home/ae1/data/Leite.LOVE/fcukt_jerkker/";
    public String workDir = "/home/ae1/archive/2010/data/";
//    public String videoInAddr = "/dev/video0";
    public String videoInAddr = "/home/ae1/Desktop/jekker_2.avi";
    public BufferedImage imgTmp;
    // beginning frag size
    public static int fragW = 100;
    public static int fragH = 100;
    public static int fragSize = 16; // so far default
    /**
     * how many cells in a comparison mask. should be fragSize * fragSize
     */
    public static int nCells = 256; // = nWCells ^ 2;
    /**
     * How many cells per frag's comparison mask's width. Should be about 16, I think.
     */
    public static int nWCells = 16;
    int infinity = 500000;
    /**
     * HEREHERE
     * fpw
     */
    public static int maskSize = 16;
    private int fragsPerWidth = 8;
    private int sourceImageFragsPerWidth = 32;
    private boolean consoleUI = true;
    JFileChooser fc = new JFileChooser();
    public String root = "/home/ae1/data/";
    public int nRandomWalkers = 6;
    public int randomWalkerSize = 15;
    public int randomWalkSpeed = 45;
    public int nSwarmAgents = 15;
    public int swarmSpeed = 75;
    public int swarmProximityThresh = 1;
    public int swarmSpeedThresh = 25;
    public int swarmVolatility = 70;

    /**
     * Sets whether this application is currently running with GUI or in console.
     * enables System.out.println() as opposed to writing to a ScrollPane.
     * @param in
     */
    public void setConsoleUI(boolean in) {
        consoleUI = in;
    }

    public boolean isConsoleUI() {
        return consoleUI;
    }

    /**
     * clean below vp_20100617
     */
    /************************************************************************/
    public Config() {
        this.fileOutAddrNum = 0;
        this.libOutAddr = testDir + "20100617/";
        this.libInAddr = testDir + "20100614/";
        this.testImageAddr = testDir + "burner.jpg";
        this.imgOutAddr = workDir + "male_face/out_1.png";

        /*
        BufferedImage img = ImageUtils.LoadBufferedImage(addr1);
        w = img.getWidth();
        h = img.getHeight();
        imgTmp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        //        imgTmp = img.getSubimage(0, 0, w, h);
         * */
    }
    public static String testDir = "/home/ae1/archive/2010/data/test/";
    private String testImageAddr = null;
    private String libOutAddr = null;
    private String fileOutAddrPrefix = "";
    private String fileOutAddrSuffix = ".png";
    private int fileOutAddrNum;
    /**
     * Below, for fractal.
     */
    private String fragmenterDir;

    public int getFragsPerWidth() {
        return fragsPerWidth;
    }

    public static int getMaskSize() {
        return maskSize;
    }

    public void setFragsPerWidth(int in) {
        fragsPerWidth = in;
    }
    private static String fragmenterSliceDirName = "slices/";
    /**
     * below, for o0-filter, ClusteredImage.
     */
    private int p = 80; // size of convolution mask
    private int thresh = 50;
    private int nBlobs = 20;
    private String libInAddr = null;

    /**
     * Where all the images are stored.
     * @return
     */
    public String getLibInAddr() {
        return libInAddr;
    }

    public File getLibIn() {
        return new File(getLibInAddr());
    }

    public void setLibIn(String in) {
        libInAddr = in;
    }

    public String nextFileOutAddr() {
        return libOutAddr + fileOutAddrPrefix + fileOutAddrNum++ + fileOutAddrSuffix;
    }

    public void setP(String p) {
        this.p = new Integer(p);
    }

    public void setThresh(String in) {
        this.thresh = new Integer(in);
    }

    public void setNBlobs(String in) {
        this.nBlobs = new Integer(in);
    }

    public int getP() {
        return p;
    }

    public int getThresh() {
        return thresh;
    }

    public int getNBlobs() {
        return nBlobs;
    }

    public void setFragmenterDir(String dirAddr) {
        this.fragmenterDir = dirAddr;
    }

    public String getFragmenterDir() {
        return fragmenterDir;
    }

    public static String getFragmenterSliceDirName() {
        return fragmenterSliceDirName;
    }

    public void setFragmenterSliceDirName(String in) {
        fragmenterSliceDirName = in;
    }

    public File getSlicesDir() {
        return new File(this.getLibIn() + "/slices");
    }

    public void setO0FractalLibAddr(String addr) {
        this.fragmenterDir = addr;
    }

    public String getO0FractalLibAddr() {
        return fragmenterDir;
    }

    public String getTestImageAddr() {
        return testImageAddr;
    }

    public void setTestDir(String addr) {
        testDir = addr;
    }

    public String getTestDir() {
        return testDir;
    }
    private String sourceImageAddr = "/var/www/test/20100618/blah.jpg";

    public String getSourceImageAddr() {
        return sourceImageAddr;
    }

    public void setSourceImage(String in) {
        sourceImageAddr = in;
    }

    /**
     * Debug print statement shortcut.
     * @param in
     */
    public static void e(String in) {
        System.out.println(in);
    }

    public boolean getDebug() {
        return true;
    }
    JTextArea output;

    public void setLog(JTextArea output) {
        this.output = output;
    }

    /**
     * Logger should work for both GUI and console apps.
     * @param in
     */
    public void log(String in) {
        if (consoleUI == false) {
            output.append(in + "\n");
            output.setCaretPosition(output.getDocument().getLength());
        } else {
            System.out.println(in);
        }
    }

    /**
     * @TODO: this is trash.
     * @return
     */
    public int getRandomPartitionCounter() {
        return 1;
    }
    private int nRandomBits = 100;

    public int getNRandomBits() {
        return nRandomBits;
    }

    public void setNRandomBits(int in) {
        nRandomBits = in;
    }
    private int shuffleBitSize = 50;

    public int getShuffleBitSize() {
        return shuffleBitSize;
    }

    public void setShuffleBitSize(int in) {
        shuffleBitSize = in;
    }

    public int getSourceImageFragsPerWidth() {
        return sourceImageFragsPerWidth;
    }

    public void setSourceImageFragsPerWidth(int in) {
        sourceImageFragsPerWidth = in;
    }

    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("not implemented");
    }
    private int nRndPartitions = 6;

    public int getNRndPartitions() {
        return nRndPartitions;
    }

    public void setNRndPartitions(int in) {
        nRndPartitions = in;
    }

    public void initFragmenterTest() {
        // production
        setSourceImage("/home/ae1/Desktop/2.jpg");
//        setLibIn("/var/www/data/images_many/");

        // test
//        setSourceImage("/home/ae1/Desktop/1.jpg");
//        setLibIn("/var/www/test/20100708/");
    }
    /**
     * Isolines
     */
    private int stepSize = 6;

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int in) {
        stepSize = in;
    }
    private int wheelResolution = 8;

    /**
     * how finegrained the searchwheel is. Coarser may mean more fun!
     * @return
     */
    public int getWheelResolution() {
        return wheelResolution;
    }

    public void setWheelResolution(int in) {
        wheelResolution = in;
    }

    /**
     * degrees of rotation of each wheel slice...
     * @return
     */
    public double getWheelSlice() {
        return 360.0 / (double) wheelResolution;
    }
    private int nSteps = 100;

    /**
     * How many times to repeat the process of drawing a line.
     * @return
     */
    public int getNSteps() {
        return nSteps;
    }

    public void setNSteps(int in) {
        nSteps = in;
    }
    private int nRndPoints = 200;

    public int getNRndPoints() {
        return nRndPoints;
    }
    private String imgOutAddr = null;

    public String getImgOutAddr() {
        return imgOutAddr;
    }
    private int circleSize = 20;
    private int ms = 50;
    private int randomSize = 100;

    /**
     * This is pretty much random.
     * @return int random size for a random circle
     */
    public int getCircleSize() {
        return circleSize;
    }
    public final static Random rnd = new Random();
    private int drawingDelay = 0;

    /**
     * e.g. how fast a tree in a forest grows. 0 means no delay, maximum fps.
     * @return
     */
    public int getDrawingDelay() {
        return drawingDelay;
    }
    WebcamKeyListener painter;
    boolean debug = true;

    public void setPainter(WebcamKeyListener aThis) {
        painter = aThis;
    }

    /**
     * @deprecated use c.repaint();
     */
    public void sleepNpaint() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        painter.repaint();

    }

    public void repaint() {
        painter.repaint();
    }

    /*
     * canvas size
     */
    public int w = 640;
    public int h = 500;

    public int getCanvasWidth() {
        return w;
    }

    public int getCanvasHeight() {
        return h;
    }

    public void setCanvasSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /*
     * base tree
     */
    public int baseTreeDelta = 40; // how crooked the branches are.
    public int nForksCenter = 20;
    public int nForksDistr = 20;
    public int branchingFreq = 2; // 1 in 2.
    public int branchNLengthCenter = 5;
    public int branchNLengthDist = 4;
    public int trunkSizeCenter = 30;
    public int trunkSizeDist = 10;
    public int brightness = 100; // > 25
    public int nTrunkHeightDistr = 18;
    public int nTrunkHeightCenter = 32;
    VideoSource videoSource;

    public void setVideoSource(WebcamBase webcam) {
        videoSource = webcam;
    }
//    public String imgLib = "/home/ae1/data/VJing/1.jpg";
    public String imgLib = "/home/ae1/archive/2010/data/test/20101015";
    public int nGoldfish = 2;
    /**
     * Fake Stereo
     */
    public String inAddr = "/home/ae1/trash/cccp.jpg";
    public String outAddr = "/home/ae1/trash/cccp1.png"; // only png for now.

    /**
     * This should be used everywhere.
     * @return
     */
    public File getFileIn() {
        if ((new File(inAddr)).exists()) {
            return new File(inAddr);
        } else {
            return IOUtils.loadFile(this);
        }
    }
    String inDir = dataDir + "VJing/";
    static final String outImgFormat = ".png"; // this should always be png.
    int fakeStereoOffsetInit = 20;
    /**
     * how to split the RGB values. default should be 255, 128, 0.
     * between 0-255. It's best if they split the channels evenly.
     */
    int[] fakeStereoMask = {255, 0, 0};
    public int fakeStereoNFrames = 60;

    /*
     * Need a dictionary of labels, to be modifiable.
     */
    /*************************************************************************/
    /**
     * How many cells in the image.
     * 1 + 6 + 12 + 18
     * I guess it's multiples of 6.
     */
//    public int multisN = 1 + 6;
    public int multisN = 13;
    /**
     * cells enlarge by how many percent outward.
     * 50%
     */
    public double multisPercentOut = .5;

    String getInAddr() {
        return inAddr;
    }
    /**
     * @TODO: account for different shapes of the mask.
     */
    /**
     * @TODO can also do cell count and size and percent of width., with or
     * without making it completely circular (so that the width is equal to
     * height, i.e. make the input image a square).
     */
    //////////////// Male Face //////////////
    private int maleFaceW = 600;

    /**
     * size of canvas out on maleFace algorithm.
     */
    public int getMaleFaceWidthOut() {
        return maleFaceW;
    }

    /**
     * size of canvas out on maleFace algorithm.
     */
    public void setMaleFaceWidthOut(int in) {
        maleFaceW = in;
    }
    private int nRectangles = 16;

    /**
     * How many rectangles in the width?
     */
    public int getNRectangles() {
        return nRectangles;
    }
}
