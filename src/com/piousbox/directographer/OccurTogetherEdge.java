/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.directographer;

/**
 *
 * @author ae1
 */
class OccurTogetherEdge extends Edge {

    public OccurTogetherEdge(Node n1, Node n2) {
        super(n1, n2);
        this.edgeType = EdgeType.OCCUR_TOGETHER;
    }

}
