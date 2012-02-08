/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.directographer;

/**
 *
 * @author ae1
 */
class ChildOfEdge extends Edge {

    ChildOfEdge(Node newNode, Node parentNode) {
        super(newNode, parentNode);
        this.edgeType = EdgeType.CHLID_OF;
    }
}
