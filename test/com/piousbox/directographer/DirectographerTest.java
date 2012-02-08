/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.directographer;

import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ae1
 */
public class DirectographerTest {
    Directographer instance;
    String testDir;

    SparseGraph<TagNode, Edge> g;
    TagNode n1, n2, n3;
    ChildOfEdge e1;
    OccurTogetherEdge e2;


    public DirectographerTest() {
        instance = new Directographer();
        testDir = "/home/ae1/archive/2011/CANON/";

        g = new SparseGraph<TagNode, Edge>();

        n1 = new TagNode("n1");
        n2 = new TagNode("n2");
        n3 = new TagNode("n3");
        e1 = new ChildOfEdge(n1, n2);
        e2 = new OccurTogetherEdge(n1, n2);
        g.addVertex(n1);
        g.addVertex(n2);
        g.addVertex(n3);
        g.addEdge(e1, n1, n2, EdgeType.DIRECTED);
        g.addEdge(e2, n1, n2, EdgeType.UNDIRECTED);

        instance.setGraph(g);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConstructFrame() {
        System.out.println("constructFrame");
        Directographer instance = new Directographer();
        JFrame expResult = null;
        JFrame result = instance.constructFrame();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Directographer.main(args);
        fail("The test case is a prototype.");
    }

    @Test
    public void testActionPerformed() {
        System.out.println("actionPerformed");
        ActionEvent e = null;
        Directographer instance = new Directographer();
        instance.actionPerformed(e);
        fail("The test case is a prototype.");
    }

    @Test
    public void testItemStateChanged() {
        System.out.println("itemStateChanged");
        ItemEvent e = null;
        Directographer instance = new Directographer();
        instance.itemStateChanged(e);
        fail("The test case is a prototype.");
    }
    
    /**
     * Finished 2011-01-22
     */
    @Test
    public void testComputeTags() {
        System.out.println("computeTags");
        File file1 = new File(testDir+"20110108.superdj");
        File file2 = new File(testDir+"20110122");
        File file3 = new File(testDir+"20110122.sunset.floripa.src");

        String[] exp1 = new String[2];
        exp1[0] = "20110108";
        exp1[1] = "superdj";

        String[] exp2 = new String[1];
        exp2[0] = "20110122";

        String[] exp3 = new String[4];
        exp3[0] = "20110122";
        exp3[1] = "sunset";
        exp3[2] = "floripa";
        exp3[3] = "src";

        
        String[] result1 = instance.computeTags(file1);
        String[] result2 = instance.computeTags(file2);
        String[] result3 = instance.computeTags(file3);

        assertEquals(exp1, result1);
        assertEquals(exp2, result2);
        assertEquals(exp3, result3);
    }

    @Test
    public void testFindNode() {
        System.out.println("findNode");
        String name1 = "n1";
        String name2 = "n2";
        Node exp1 = n1;
        Node exp2 = n2;
        Node result1 = instance.findNode("n1");
        Node result2 = instance.findNode("n2");
        assertEquals(exp1, result1);
        assertEquals(exp2, result2);
    }

    @Test
    public void testGetGraph() {
        System.out.println("getGraph");
        Directographer instance = new Directographer();
        SparseGraph expResult = null;
        SparseGraph result = instance.getGraph();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetGraph() {
        System.out.println("setGraph");
        SparseGraph<TagNode, Edge> g = null;
        Directographer instance = new Directographer();
        instance.setGraph(g);
        fail("The test case is a prototype.");
    }

    @Test
    public void testEdgeExists() {
        System.out.println("edgeExists");
        String s1 = "n1";
        String s2 = "n2";
        String s3 = "n3";
        Edge.EdgeType edgeType = Edge.EdgeType.CHLID_OF;
        boolean result = instance.edgeExists(s1, s2, edgeType);
        boolean result1 = instance.edgeExists(s1, s3, edgeType);
        assertEquals(true, result);
        assertEquals(false, result1);
    }

}