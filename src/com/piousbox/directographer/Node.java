/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.directographer;

import java.io.File;

/**
 *
 * @author ae1
 */
abstract public class Node {
    int id;
    String name;
    String descr;

    Node(String string) {
        this.name = string;
    }

    @Override
    public String toString() {
        return name;
    }

}
