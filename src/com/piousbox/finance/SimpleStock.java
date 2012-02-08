/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.finance;

/**
 *
 * @author ae1
 */
public class SimpleStock implements Stock {

    static double[] prices = {538.23, 522.35, 525.0, 527.0, 527.0, 530.0, 527.0, 508.0, 480.0};

    public double[] getPrices() {
        return prices;
    }
}
