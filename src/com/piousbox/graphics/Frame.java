
package com.piousbox.graphics;

/**
 *
 * @author piousbox
 */
public class Frame {

    /**
     * The filename of the input image
     */
    public String in;

    /**
     * the filename of the output image
     */
    public String out;

    Frame(String in) {
        this.in = in;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

}
