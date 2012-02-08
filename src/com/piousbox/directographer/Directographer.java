package com.piousbox.directographer;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.activation.MimetypesFileTypeMap;

/**
 *
 * @author ae1
 */
public class Directographer implements ActionListener, ItemListener {
    //Where the GUI is created:

    JFrame frame = null;
    JMenuBar menuBar;
    JMenu menu, changeLayoutSubmenu;
    JMenuItem openDirMenuItem,
            treeLayoutMenuItem, circularLayoutMenuItem, springLayoutMenuItem;
    JRadioButtonMenuItem childOfEdgesMenuItem_trash;
    JCheckBoxMenuItem childOfEdgesMenuItem;
    SparseGraph<TagNode, Edge> g;
    Layout<Node, Edge> layout = null;
    VisualizationViewer<Node, Edge> vv = null;
    /**
     * config
     */
    boolean add_child_of_edges;
    File rootFolder;
    int level;

    public Directographer() {
        g = new SparseGraph<TagNode, Edge>();

        /*
         * config
         */
        rootFolder = new File("/home/ae1/archive/2011");
        level = 4; // how many levels down?
        add_child_of_edges = true;

//        TagNode[] origin = new TagNode[0];
//        origin[0] = new TagNode(folder.getName());
        processFolder(rootFolder, null, level);

        layout = new edu.uci.ics.jung.algorithms.layout.SpringLayout2(g);
        vv = visualize();

        frame = new JFrame("Simple Graph View");
        frame.setJMenuBar(this.constructMenuBar());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

    }

    final JMenuBar constructMenuBar() {
        menuBar = new JMenuBar();

        menu = new JMenu("Main Menu");
//        menu.setMnemonic(KeyEvent.VK_A);
//        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        openDirMenuItem = new JMenuItem("Open...", KeyEvent.VK_T);
        openDirMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        openDirMenuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        openDirMenuItem.addActionListener(this);
//        menu.add(openDirMenuItem);


        ButtonGroup group = new ButtonGroup();
        childOfEdgesMenuItem_trash = new JRadioButtonMenuItem("A radio button menu item");
        childOfEdgesMenuItem_trash.setSelected(true);
        childOfEdgesMenuItem_trash.setMnemonic(KeyEvent.VK_R);
        group.add(childOfEdgesMenuItem_trash);
//        menu.addSeparator();
//        menu.add(rbMenuItem);

        childOfEdgesMenuItem_trash = new JRadioButtonMenuItem("Do ChildOf Relation?");
        group.add(childOfEdgesMenuItem_trash);
//        menu.add(rbMenuItem);

        /**
         * child of edges menu item
         */
        menu.addSeparator();
        childOfEdgesMenuItem = new JCheckBoxMenuItem("ChildOf Edges?");
        childOfEdgesMenuItem.setMnemonic(KeyEvent.VK_C);
        childOfEdgesMenuItem.addItemListener(this);
        menu.add(childOfEdgesMenuItem);




        /**
         * Change Layout
         */
        changeLayoutSubmenu = new JMenu("Change Layout");
//        changeLayoutSubmenu.setMnemonic(KeyEvent.VK_S);

        treeLayoutMenuItem = new JMenuItem("Tree Layout");
        treeLayoutMenuItem.addActionListener(this);
        changeLayoutSubmenu.add(treeLayoutMenuItem);

        circularLayoutMenuItem = new JMenuItem("Circular Layout");
        circularLayoutMenuItem.addActionListener(this);
        changeLayoutSubmenu.add(circularLayoutMenuItem);

        springLayoutMenuItem = new JMenuItem("Spring Layout 2");
        springLayoutMenuItem.addActionListener(this);
        changeLayoutSubmenu.add(springLayoutMenuItem);
        menu.add(changeLayoutSubmenu);




        openDirMenuItem = new JMenuItem("An item in the submenu");
        openDirMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));

        //Build second menu in the menu bar.
        menu = new JMenu("Another Menu");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
//        menuBar.add(menu);

        return menuBar;
    }

    public static void main(String[] args) {
        new Directographer();
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("action changes: " + e.getSource());

        if (e.getSource() == this.circularLayoutMenuItem) {
            layout = new edu.uci.ics.jung.algorithms.layout.CircleLayout(g);
            vv = visualize();
            reinitFrame(vv);
        }

        if (e.getSource() == this.springLayoutMenuItem) {
            layout = new edu.uci.ics.jung.algorithms.layout.SpringLayout2(g);
            vv = visualize();
            reinitFrame(vv);
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == this.childOfEdgesMenuItem) {
            if (this.childOfEdgesMenuItem.isSelected()) {
                this.add_child_of_edges = true;
            } else {
                this.add_child_of_edges = false;
            }

            reinitNodesAndEdges();
        }
    }

    private void processFolder(File folder, TagNode[] parentNodes, int level) {
        /*
         * get tags
         */
        String[] tags = computeTags(folder);
        TagNode[] theseNodes = new TagNode[tags.length];

        if (parentNodes == null) {
            parentNodes = new TagNode[0];
        }

        /*
         * compute these nodes
         */
        for (int i = 0; i < tags.length; i++) {
            TagNode tempNode = this.findNode(tags[i]);
            if (tempNode != null) {
                theseNodes[i] = tempNode;
            } else {
                theseNodes[i] = new TagNode(tags[i]);
                g.addVertex(theseNodes[i]);
            }
        }

        /*
         * adding chlid_of edges.
         */
        if (add_child_of_edges) {
            for (int ii = 0; ii < parentNodes.length; ii++) {
                TagNode parentNode = parentNodes[ii];
                for (int i = 0; i < tags.length; i++) {
                    TagNode childNode = this.findNode(tags[i]);
                    ChildOfEdge edge = new ChildOfEdge(childNode, parentNode);
                    g.addEdge(edge, childNode, parentNode, EdgeType.DIRECTED);
                }
            }
        }

        /*
         * adding occur_together edges
         */
        if (tags.length > 1) {
            Edge existingEdge = null;
            for (int i = 0; i < tags.length - 1; i++) {
                existingEdge = this.edgeExists(tags[i], tags[i + 1], Edge.EdgeType.CHLID_OF);
            }

            if (existingEdge == null) {
                for (int i = 0; i < tags.length - 1; i++) {
                    TagNode node1 = findNode(tags[i]);
                    TagNode node2 = findNode(tags[i + 1]);
                    Edge newEdge = new OccurTogetherEdge(node1, node2);
                    g.addEdge(newEdge, node1, node2, EdgeType.UNDIRECTED);
                }
            } else {
                // increment weight of existing edge.
                existingEdge.increaseWeight();
            }
        }

        /*
         * At this point I'm done processing a folder.
         * Next I will process children of this folder.
         */
        if (folder.isDirectory()) {
            if (level > 0) {
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                    processFolder(listOfFiles[i], theseNodes, --level);
                }
            }
        }
        if (folder.isFile()) {
            processFile(folder, theseNodes);
        }


    }

    void processFile(File file, TagNode[] parentNodes) {
//        FileNode node = null;
//        node = new FileNode(file.getName());
////        try {
////            node = new FileNode(file.getCanonicalPath());
////        } catch (IOException ex) {
////            Logger.getLogger(Directographer.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        String contentType = new MimetypesFileTypeMap().getContentType(file);
//
//        node.setFileType(contentType);
//
//        for (int i = 0; i < parentNodes.length; i++) {
//            g.addEdge(new ChildOfEdge(node, parentNodes[i]), node, parentNodes[i], EdgeType.DIRECTED);
//        }
    }

    /**
     * computes tags of this foldeR: splits it along dots ("\\.")
     * Tested, works.
     * This is redundant, it's a one-liner: String[] result = name.split("\\.");
     * @deprecated this is a one-liner: String[] result = name.split("\\.");
     * @param folder
     * @return
     */
     String[] computeTags(File folder) {
        String name = folder.getName();
        String[] result = name.split("\\.");
        return result;
    }

    /**
     * Tested, works.
     * @param prospectiveNodeName
     * @return
     */
    TagNode findNode(String prospectiveNodeName) {
        for (Iterator<TagNode> iter = g.getVertices().iterator(); iter.hasNext();) {
            TagNode existingNode = iter.next();
            if (existingNode.toString().equals(prospectiveNodeName)) {
                return existingNode;
            }
        }
        return null;
    }

    public SparseGraph<TagNode, Edge> getGraph() {
        return g;
    }

    public void setGraph(SparseGraph<TagNode, Edge> g) {
        this.g = g;
    }

    private VisualizationViewer<Node, Edge> visualize() {
        Dimension d = new Dimension(1600, 900);
        layout.setSize(d);

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);

        VisualizationViewer<Node, Edge> vv =
                new VisualizationViewer<Node, Edge>(layout);
        vv.setPreferredSize(d); //Sets the viewing area size
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.setGraphMouse(gm);
        vv.addKeyListener(gm.getModeKeyListener());

        return vv;
    }

    Edge edgeExists(String s1, String s2, Edge.EdgeType edgeType) {
        for (Iterator<Edge> iter = g.getEdges().iterator(); iter.hasNext();) {
            String edgeRepresentation = s1 + "." + s2;
            Edge existingEdge = iter.next();
            if (existingEdge.edgeType == edgeType) {
                if (existingEdge.getNodesAsString().equals(edgeRepresentation)) {
                    return existingEdge;
                }
            }
        }
        return null;
    }

    private void reinitFrame(VisualizationViewer<Node, Edge> vv) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

    private void reinitNodesAndEdges() {
        g = new SparseGraph<TagNode, Edge>();
        processFolder(rootFolder, null, level);
        visualize();
    }
}
