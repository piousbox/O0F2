/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.piousbox;



import java.util.ArrayList;

/**
 *
 * @author ae1
 */
public class TextUtils {

    public static String ArrL2Str(ArrayList<String> a) {
        String out = "";
        
        for (int i=0; i<a.size(); i++) {
            out += a.get(i) +" ";
        }
        
        return out;
    }
    
    /**
     * Take an array of strings and return the string containing all the elements, space-separated
     * @param restWordsArr
     * @return
     */
    public static String ArrayToString(String[] restWordsArr) {
        String check="";
        for (int i=0; i<restWordsArr.length; i++) {
            check += restWordsArr[i] + " ";
        }
        return check;
    }
}
