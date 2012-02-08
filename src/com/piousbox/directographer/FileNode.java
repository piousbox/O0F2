

package com.piousbox.directographer;

/**
 *
 * @author ae1
 */
class FileNode extends Node {

    String contentType;

    FileNode(String str) {
        super(str);
    }

    void setFileType(String type) {
        this.contentType = type;
    }

}
