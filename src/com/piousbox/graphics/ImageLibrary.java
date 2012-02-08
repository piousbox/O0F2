/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piousbox.graphics;

import com.piousbox.IOUtils;
import com.piousbox.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author ae1
 */
public class ImageLibrary implements Iterator {

    Config config;
    ArrayList<File> images = new ArrayList<File>();
    int cursor = 0;
    String libAddr;

    public ImageLibrary(Config config) {
        File[] tmp = IOUtils.imagesInDir(config.getSlicesDir(), false);
        images.addAll(Arrays.asList(tmp));

        libAddr = config.getLibInAddr();
    }

    public ImageLibrary(String libAddr) {
        this.libAddr = libAddr;
        File slicesDir = Config.getSlicesDir(libAddr);
        File[] tmp = IOUtils.imagesInDir(slicesDir, false);
        images.addAll(Arrays.asList(tmp));
    }

    public boolean hasNext() {
        return (cursor < images.size()) ? true : false;
    }

    public int getNImages() {
        return images.size();
    }

    public File next() {
        return images.get(cursor++);
    }

    /**
     * @TODO check that this works.
     */
    public void remove() {
        images.remove(cursor);
    }

    public void setCursor(int i) {
        cursor = i;
    }

    public int getCursor() {
        return cursor;
    }

    public File thisSlice() {
        if (cursor == 0) {
            return images.get(cursor);
        } else {
            return images.get(cursor - 1);
        }
    }

    int[] getComparisonMask() {
        int[] mask = null;
        File thisFile = next();
        if (existsMask(thisFile)) {
            mask = loadMask(thisFile);
        } else {
            mask = saveMask(thisFile);
        }

        return mask;
    }

    boolean existsMask(File thisFile) {
        String thisName = Fragmenter.getMaskAddr(thisFile);
        File maskFile = new File(thisName);
        if (maskFile.exists() && maskFile.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    int[] loadMask(File thisFile) {
        String thisName = Fragmenter.getMaskAddr(thisFile);
        return (int[]) IOUtils.loadObject(thisName);
    }

    int[] saveMask(File thisFile) {
        int[] thisMask = Fragmenter.getComparisonMask(ImageUtils.loadBufferedImage(thisFile));
        String addr = Fragmenter.getMaskAddr(thisFile);
        IOUtils.saveObject(thisMask, addr);
//        config.log("saving mask "+ addr);
        return thisMask;
    }
}
