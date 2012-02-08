/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.graphics;

import com.piousbox.graphics.vjing.WebcamKeyListener;
import com.piousbox.graphics.webcam.WebcamBase;
import java.io.File;
import java.util.Observable;
import javax.swing.JTextArea;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ae1
 */
public class ConfigTest {
    String testDir;
    Config c;

    public ConfigTest() {
    }

    @Before
    public void setUp() {
        c = new Config();
        testDir = c.getTestDir();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetTestDir() {
        String _testDir = c.getTestDir();
        File file = new File(_testDir);
        assertTrue("testDir must exist", file.exists());
    }

    @Test
    public void testGetFileIn() {
//        File file = new File(testDir+"");
        
        assertTrue("getFileIn must return an existing file ", c.getFileIn().exists());
    }

    @Test
    public void testMultisLI() {
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSlicesDir_String() {
        System.out.println("getSlicesDir");
        String libAddr = "";
        File expResult = null;
        File result = Config.getSlicesDir(libAddr);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetConsoleUI() {
        System.out.println("setConsoleUI");
        boolean in = false;
        Config instance = new Config();
        instance.setConsoleUI(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsConsoleUI() {
        System.out.println("isConsoleUI");
        Config instance = new Config();
        boolean expResult = false;
        boolean result = instance.isConsoleUI();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFragsPerWidth() {
        System.out.println("getFragsPerWidth");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getFragsPerWidth();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetMaskSize() {
        System.out.println("getMaskSize");
        int expResult = 0;
        int result = Config.getMaskSize();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetFragsPerWidth() {
        System.out.println("setFragsPerWidth");
        int in = 0;
        Config instance = new Config();
        instance.setFragsPerWidth(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetLibInAddr() {
        System.out.println("getLibInAddr");
        Config instance = new Config();
        String expResult = "";
        String result = instance.getLibInAddr();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetLibIn() {
        System.out.println("getLibIn");
        Config instance = new Config();
        File expResult = null;
        File result = instance.getLibIn();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetLibIn() {
        System.out.println("setLibIn");
        String in = "";
        Config instance = new Config();
        instance.setLibIn(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testNextFileOutAddr() {
        System.out.println("nextFileOutAddr");
        Config instance = new Config();
        String expResult = "";
        String result = instance.nextFileOutAddr();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetP() {
        System.out.println("setP");
        String p = "";
        Config instance = new Config();
        instance.setP(p);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetThresh() {
        System.out.println("setThresh");
        String in = "";
        Config instance = new Config();
        instance.setThresh(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetNBlobs() {
        System.out.println("setNBlobs");
        String in = "";
        Config instance = new Config();
        instance.setNBlobs(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetP() {
        System.out.println("getP");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getP();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetThresh() {
        System.out.println("getThresh");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getThresh();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNBlobs() {
        System.out.println("getNBlobs");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getNBlobs();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetFragmenterDir() {
        System.out.println("setFragmenterDir");
        String dirAddr = "";
        Config instance = new Config();
        instance.setFragmenterDir(dirAddr);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFragmenterDir() {
        System.out.println("getFragmenterDir");
        Config instance = new Config();
        String expResult = "";
        String result = instance.getFragmenterDir();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetFragmenterSliceDirName() {
        System.out.println("getFragmenterSliceDirName");
        String expResult = "";
        String result = Config.getFragmenterSliceDirName();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetFragmenterSliceDirName() {
        System.out.println("setFragmenterSliceDirName");
        String in = "";
        Config instance = new Config();
        instance.setFragmenterSliceDirName(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSlicesDir_0args() {
        System.out.println("getSlicesDir");
        Config instance = new Config();
        File expResult = null;
        File result = instance.getSlicesDir();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetO0FractalLibAddr() {
        System.out.println("setO0FractalLibAddr");
        String addr = "";
        Config instance = new Config();
        instance.setO0FractalLibAddr(addr);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetO0FractalLibAddr() {
        System.out.println("getO0FractalLibAddr");
        Config instance = new Config();
        String expResult = "";
        String result = instance.getO0FractalLibAddr();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetTestImageAddr() {
        System.out.println("getTestImageAddr");
        Config instance = new Config();
        String expResult = "";
        String result = instance.getTestImageAddr();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetTestDir() {
        System.out.println("setTestDir");
        String addr = "";
        Config instance = new Config();
        instance.setTestDir(addr);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSourceImageAddr() {
        System.out.println("getSourceImageAddr");
        Config instance = new Config();
        String expResult = "";
        String result = instance.getSourceImageAddr();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSourceImage() {
        System.out.println("setSourceImage");
        String in = "";
        Config instance = new Config();
        instance.setSourceImage(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testE() {
        System.out.println("e");
        String in = "";
        Config.e(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDebug() {
        System.out.println("getDebug");
        Config instance = new Config();
        boolean expResult = false;
        boolean result = instance.getDebug();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetLog() {
        System.out.println("setLog");
        JTextArea output = null;
        Config instance = new Config();
        instance.setLog(output);
        fail("The test case is a prototype.");
    }

    @Test
    public void testLog() {
        System.out.println("log");
        String in = "";
        Config instance = new Config();
        instance.log(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRandomPartitionCounter() {
        System.out.println("getRandomPartitionCounter");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getRandomPartitionCounter();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNRandomBits() {
        System.out.println("getNRandomBits");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getNRandomBits();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetNRandomBits() {
        System.out.println("setNRandomBits");
        int in = 0;
        Config instance = new Config();
        instance.setNRandomBits(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetShuffleBitSize() {
        System.out.println("getShuffleBitSize");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getShuffleBitSize();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetShuffleBitSize() {
        System.out.println("setShuffleBitSize");
        int in = 0;
        Config instance = new Config();
        instance.setShuffleBitSize(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSourceImageFragsPerWidth() {
        System.out.println("getSourceImageFragsPerWidth");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getSourceImageFragsPerWidth();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSourceImageFragsPerWidth() {
        System.out.println("setSourceImageFragsPerWidth");
        int in = 0;
        Config instance = new Config();
        instance.setSourceImageFragsPerWidth(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdate() {
        System.out.println("update");
        Observable o = null;
        Object arg = null;
        Config instance = new Config();
        instance.update(o, arg);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNRndPartitions() {
        System.out.println("getNRndPartitions");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getNRndPartitions();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetNRndPartitions() {
        System.out.println("setNRndPartitions");
        int in = 0;
        Config instance = new Config();
        instance.setNRndPartitions(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testInitFragmenterTest() {
        System.out.println("initFragmenterTest");
        Config instance = new Config();
        instance.initFragmenterTest();
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetStepSize() {
        System.out.println("getStepSize");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getStepSize();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetStepSize() {
        System.out.println("setStepSize");
        int in = 0;
        Config instance = new Config();
        instance.setStepSize(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetWheelResolution() {
        System.out.println("getWheelResolution");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getWheelResolution();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetWheelResolution() {
        System.out.println("setWheelResolution");
        int in = 0;
        Config instance = new Config();
        instance.setWheelResolution(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetWheelSlice() {
        System.out.println("getWheelSlice");
        Config instance = new Config();
        double expResult = 0.0;
        double result = instance.getWheelSlice();
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNSteps() {
        System.out.println("getNSteps");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getNSteps();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetNSteps() {
        System.out.println("setNSteps");
        int in = 0;
        Config instance = new Config();
        instance.setNSteps(in);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNRndPoints() {
        System.out.println("getNRndPoints");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getNRndPoints();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetImgOutAddr() {
        System.out.println("getImgOutAddr");
        Config instance = new Config();
        String expResult = "";
        String result = instance.getImgOutAddr();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetCircleSize() {
        System.out.println("getCircleSize");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getCircleSize();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDrawingDelay() {
        System.out.println("getDrawingDelay");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getDrawingDelay();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetPainter() {
        System.out.println("setPainter");
        WebcamKeyListener aThis = null;
        Config instance = new Config();
        instance.setPainter(aThis);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSleepNpaint() {
        System.out.println("sleepNpaint");
        Config instance = new Config();
        instance.sleepNpaint();
        fail("The test case is a prototype.");
    }

    @Test
    public void testRepaint() {
        System.out.println("repaint");
        Config instance = new Config();
        instance.repaint();
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetCanvasWidth() {
        System.out.println("getCanvasWidth");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getCanvasWidth();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetCanvasHeight() {
        System.out.println("getCanvasHeight");
        Config instance = new Config();
        int expResult = 0;
        int result = instance.getCanvasHeight();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetCanvasSize() {
        System.out.println("setCanvasSize");
        int w = 0;
        int h = 0;
        Config instance = new Config();
        instance.setCanvasSize(w, h);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetVideoSource() {
        System.out.println("setVideoSource");
        WebcamBase webcam = null;
        Config instance = new Config();
        instance.setVideoSource(webcam);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetInAddr() {
        System.out.println("getInAddr");
        Config instance = new Config();
        String expResult = "";
        String result = instance.getInAddr();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testOutAddr() {
        String ext = c.outAddr.substring(c.outAddr.length()-3, c.outAddr.length());
        assertEquals(ext, "png");
    }

}