/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox;

import com.piousbox.graphics.Config;
import java.io.File;
import java.util.Arrays;
import javax.swing.JFrame;
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
public class IOUtilsTest {
    Config config;
    String testDir;

    public IOUtilsTest() {
    }

    @Before
    public void setUp() {
        config = new Config();
        testDir = config.getTestDir();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of intArr2Str method, of class IOUtils.
     */
    @Test
    public void testIntArr2Str() {
        System.out.println("intArr2Str");
        int[] idxRing = {1, 2, 3};
        String expResult = "1 2 3";
        String result = IOUtils.intArr2Str(idxRing);
        assertEquals(expResult, result);
    }

    /**
     * Test of saveObject method, of class IOUtils.
     */
    @Test
    public void testSaveLoadObject() {
        System.out.println("saveObject");
        String[] saveObj = {"blah", "blah1", "blah2"};
        String addr = testDir+"20100618/obj1.oats";
        IOUtils.saveObject(saveObj, addr);

        String[] result = (String[]) IOUtils.loadObject(addr);
        assertEquals(saveObj, result);
    }

    /**
     * Test of StringArrToFile method, of class IOUtils.
     */
    @Test
    public void testStringArrToFile() {
        System.out.println("StringArrToFile");
        String[] sortedLines = {"line1", "line2", "line3"};
        String addr2 = testDir+"20100618/stringArrToFile.txt";
        String newline = "\n";
        IOUtils.StringArrToFile(sortedLines, addr2, newline);

        String result = IOUtils.readFile(addr2);
        System.out.println(result);
        String expected = "line1\nline2\nline3\n";

        assertEquals(expected, result);
    }

    /**
     * Test of fileToString method, of class IOUtils.
     */
    @Test
    public void testFileToString() throws Exception {
        System.out.println("fileToString");
        String filename = testDir+"20100618/test3.txt";
        String expResult = "abfk1\n";
        String result = IOUtils.fileToString(filename);
        assertEquals(expResult, result);
    }

    /**
     * Test of writeString2File method, of class IOUtils.
     */
    @Test
    public void testWriteString2File() throws Exception {
        System.out.println("writeString2File");
        String expected = "blah blah blah\n";
        String addr = testDir+"20100618/test1.txt";
        IOUtils.writeString2File(expected, addr);

        String actual = IOUtils.readFile(addr);
        assertEquals(expected, actual);
    }

    /**
     * Test of imagesInDir method, of class IOUtils.
     */
    @Test
    public void testImagesInDir() {
        System.out.println("imagesInDir");
        String dirAddr = this.testDir + "20101015/";
        File dir = new File(dirAddr);
        boolean traverse_subdirs = false;

        File e1 = new File(dirAddr+"test1.jpg");
        File[] expected = {e1};

        File[] result = IOUtils.imagesInDir(dir, traverse_subdirs);

        assertEquals(expected.length, result.length);

        Arrays.sort(expected);
        Arrays.sort(result);
        
//        assertTrue(Arrays.equals(expected, result));
        assertEquals(expected[0].getAbsoluteFile(), result[0].getAbsoluteFile());
    }

    @Test
    public void testFileNameWithoutExtention_File() {
        System.out.println("testFileNameWithoutExtention_File");
        File file = new File("/temp1.jpgs");
        String expected = "temp1";
        String result = IOUtils.fileNameWithoutExtention(file);
        assertEquals(expected, result);
    }

    @Test
    public void testFileNameExtention_File() {
        System.out.println("testFileNameExtention_File");
        File file = new File("/temp1.jpgs");
        String expected = "jpgs";
        String result = IOUtils.fileNameExtention(file);
        assertEquals(expected, result);
    }

    @Test
    public void testSaveObject() {
        System.out.println("test save Object");
        assertEquals("the function is testSaveLoadObject", 1, 1);
    }

    @Test
    public void testLoadObject() {
        System.out.println("test Load Object");
        assertEquals("the function is testSaveLoadObject", 1, 1);
    }

    @Test
    public void testInputInt() {
        System.out.println("test Input Int");
        assertEquals("These won't be tested b/c it's Input/Output", 1, 1);
    }

    @Test
    public void testInputStr() {
        System.out.println("test Input String");
        assertEquals("These won't be tested b/c it's Input/Output", 1, 1);
    }

    @Test
    public void testLoadDir() {
        System.out.println("test Load Dir");
        assertEquals("This involves a jDialog...", 1, 1);
    }

    @Test
    public void testImagesInDir_File_boolean() {
        System.out.println("test Images In Dir File Boolean");
        System.out.println("imagesInDir");
        File dir = new File(this.testDir + "20101015/");
        boolean traverse_subdirs = false;
        File[] expResult = {new File(testDir + "20101015/test1.jpg")};
        File[] result = IOUtils.imagesInDir(dir, traverse_subdirs);
        assertEquals(expResult, result);
    }

    @Test
    public void testMain() {
        System.out.println("test main method");
        assertEquals("main method won't be tested", 1, 1);
    }

    @Test
    public void testFileNameWithoutExtention() {
        System.out.println("fileNameWithoutExtention");
        File srcImg = new File(testDir+"20110116/frame0001.jpg");
        String expResult = "frame0001";
        String result = IOUtils.fileNameWithoutExtention(srcImg);
        assertEquals(expResult, result);
    }

    @Test
    public void testFileNameExtention() {
        System.out.println("fileNameExtention");
        File srcImg = new File(testDir+"20110116/frame0001.jpg");
        String expResult = "jpg";
        String result = IOUtils.fileNameExtention(srcImg);
        assertEquals(expResult, result);
    }

    @Test
    public void testReadFile() {
        System.out.println("readFile");
        String in = testDir+"20100618/test1.txt";
        String expResult = "blah blah blah\n";
        String result = IOUtils.readFile(in);
        assertEquals(expResult, result);
    }

    @Test
    public void testTouch() {
        System.out.println("touch");
        File file = new File(testDir + "20101015/touch");
//        System.out.println(file.toString());
        file.delete();
        IOUtils.touch(file);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testLoadFile() {
        System.out.println("loadFile");
        assertEquals("this is a dialog...", 1, 1);
    }

    @Test
    public void testImagesInDir_String_boolean() {
        System.out.println("imagesInDir");

        String dirAddr = this.testDir + "20101015/";
        boolean traverse_subdirs = false;
        File e1 = new File(dirAddr+"test1.jpg");
        File[] expected = {e1};
        File[] result = IOUtils.imagesInDir(dirAddr, traverse_subdirs);
        assertEquals(expected.length, result.length);
        Arrays.sort(expected);
        Arrays.sort(result);
        assertEquals(expected[0].getAbsoluteFile(), result[0].getAbsoluteFile());
    }

    @Test
    public void testCopyFile_String_String() throws Exception {
        System.out.println("copyFile");
        String in = testDir+"20100618/test1.txt";
        String out = testDir+"20100618/test2.txt";
        IOUtils.copyFile(in, out);
        String a = IOUtils.readFile(out);
        String b = IOUtils.readFile(in);
        assertEquals(a, b);
    }

    @Test
    public void testCopyFile_File_File() throws Exception {
        System.out.println("copyFile");
        File in = new File(testDir+"20100618/test1.txt");
        File out = new File(testDir+"20100618/test2.txt");
        IOUtils.copyFile(in, out);
        String a = IOUtils.readFile(out.getCanonicalPath());
        String b = IOUtils.readFile(in.getCanonicalPath());
        assertEquals(a, b);
    }

    @Test
    public void testSaveDialog() {
        System.out.println("SaveDialog");
        assertEquals("won't be tested.", 1, 1);
    }

}