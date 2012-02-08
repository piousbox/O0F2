/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox.finance;

/**
 *
 * @author ae1
 */
public class SimpleGraph implements Graph {
    
    Config c = new Config();

    SimpleGraph() {
        SimpleStock goog = new SimpleStock();
        double prices[] = goog.getPrices();
        GraphUtils.drawGraph(prices, c.imgOutAddr);
    }

    public static void main(String[] args) {
        SimpleGraph sg = new SimpleGraph();
    }

}
