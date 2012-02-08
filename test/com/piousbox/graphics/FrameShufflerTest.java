/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import java.io.File;
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
public class FrameShufflerTest {

    FrameShuffler instance;
    Frame[] result;
    String inPrefix;
    String inSuffix;
    String outPrefix;
    String outSuffix;
    int nFrames = 3;

    public FrameShufflerTest() {
        instance = new FrameShuffler();
    }

    @Before
    public void setUp() {
        inPrefix = Config.testDir + "20101216.shuffle_video/";
        inSuffix = ".jpg";
        outPrefix = Config.testDir + "20101216.shuffle_video/out/";
        outSuffix = ".jpg";

        result = FrameShuffler.collectFrames(inPrefix, inSuffix, nFrames);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testComputePadding() {
        System.out.println("computePadding");

        int result = FrameShuffler.computePadding(0);
        assertTrue(result >= 1);
        assertEquals(1, result);

        result = FrameShuffler.computePadding(12);
        assertEquals(2, result);

        result = FrameShuffler.computePadding(10);
        assertEquals(2, result);

        result = FrameShuffler.computePadding(1143);
        assertEquals(4, result);

        result = FrameShuffler.computePadding(12000000);
        assertEquals(8, result);
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
    }

    @Test
    public void testShuffle() {
        System.out.println("shuffle");
        int n = 5;
        Frame[] frames = new Frame[n];
        frames[0] = new Frame("1");
        frames[1] = new Frame("2");
        frames[2] = new Frame("3");
        frames[3] = new Frame("4");
        frames[4] = new Frame("5");

        int splitPoint = 2;
        Frame[] result = FrameShuffler.shuffle(frames, splitPoint);
        assertEquals(5, result.length);
        assertEquals("3", result[0].getIn());
        assertEquals("4", result[1].getIn());
        assertEquals("5", result[2].getIn());
        assertEquals("1", result[3].getIn());
        assertEquals("2", result[4].getIn());

        splitPoint = 4;
        result = FrameShuffler.shuffle(frames, splitPoint);
        assertEquals(5, result.length);
        assertEquals("5", result[0].getIn());
        assertEquals("1", result[1].getIn());
        assertEquals("2", result[2].getIn());
        assertEquals("3", result[3].getIn());
        assertEquals("4", result[4].getIn());
    }

    @Test
    public void testComputePaddingZeroes() {
        System.out.println("computePaddingZeroes");
        int nFrames = 10;
        int thisFrame = 1;
        String expResult = "0";
        String result = FrameShuffler.computePaddingZeroes(nFrames, thisFrame);
        assertEquals(expResult, result);

        result = FrameShuffler.computePaddingZeroes(100, 1);
        assertEquals("00", result);
    }

    @Test
    public void testCollectFrames() {
        System.out.println("collectFrames");

        assertEquals(inPrefix + "1" + inSuffix, result[0].getIn());
        assertEquals(inPrefix + "2" + inSuffix, result[1].getIn());
        assertEquals(inPrefix + "3" + inSuffix, result[2].getIn());
    }

    @Test
    public void testSaveFrames() {
        System.out.println("saveFrames");
        File f = new File(outPrefix);
        f.delete();
        f.mkdir();

        System.out.println(outPrefix);
        instance.saveFrames(result, outPrefix, outSuffix);
        File file = new File(outPrefix + "1" + outSuffix);
        assertTrue(file.exists());
    }
}
