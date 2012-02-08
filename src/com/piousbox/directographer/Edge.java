/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.directographer;

/**
 *
 * @author ae1
 */
abstract class Edge {

    int count;
    Node nodeFrom, nodeTo;
    EdgeType edgeType;

    void increaseWeight() {
        System.out.println("increaseWeight of an edge");
    }
    
    public enum EdgeType {

        CHLID_OF, OCCUR_TOGETHER
    }

    public String toString() {
        // return nodeFrom +"-"+ typeOfEdge +"-"+ nodeTo;
        return "";
    }

    public String getNodesAsString() {
        return nodeFrom.toString() + "." + nodeTo.toString();
    }

    Edge() {
        count = 1;
    }

    public void incrementCount(int howMany) {
        count += howMany;
    }

    public Edge(Node newNode, Node parentNode) {
        nodeFrom = newNode;
        nodeTo = parentNode;
    }
}
