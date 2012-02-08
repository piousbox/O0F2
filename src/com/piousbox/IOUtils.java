package com.piousbox;

import com.piousbox.graphics.Config;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author ae1
 */
public class IOUtils {

    /**
     * Brings up fileDialog to save.
     * NOT TESTED because it's fucking annoying.
     * @param frame is a JFrame, observer.
     * @return the addr to save to.
     */
    public static String SaveDialog(JFrame frame) {
        String addr = "";
        FileDialog dialog = new FileDialog(frame, "saveTo: ");
        dialog.setMode(FileDialog.SAVE);
        dialog.setVisible(true);
        if (!(dialog.getFile().equals(""))) {
            addr += dialog.getDirectory() + dialog.getFile();
        }
        return addr;
    }

    /**
     * Tested, works.
     * @param idxRing
     * @return a space-separated string of the int arr values.
     */
    public static String intArr2Str(int[] idxRing) {
        String out = "";
        for (int i = 0; i < idxRing.length; i++) {
            out += idxRing[i] + " ";
        }

        return out.substring(0, out.length() - 1);
    }

    /**
     * Serializes an object.
     * @param saveObj object to be saves
     * @param addr save the object to this addr
     */
    public static void saveObject(Object saveObj, String addr) {
        try {
            ObjectOutputStream objStream = new ObjectOutputStream(new FileOutputStream(addr));
            objStream.writeObject(saveObj);
            objStream.close();
        } catch (IOException ex) {
            Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("saved: " + addr);
    }

    /**
     * Loads a serialized object
     * @param addr from here
     * @return the object loaded.
     */
    public static Object loadObject(String addr) {
        ObjectInputStream objStream = null;
        Object obj = null;
        try {
            objStream = new ObjectInputStream(new FileInputStream(addr));
            obj = objStream.readObject();
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        } finally {
            try {
                objStream.close();
            } catch (IOException ex) {
            }
        }
//        System.out.println("loaded: " + addr);
        return obj;
    }

    /**
     * 
     * @param sortedLines
     * @param addr2
     * @param newline String seq. used to denote newline. Should be something like "\n" most of the time.
     */
    public static void StringArrToFile(String[] sortedLines, String addr2, String newline) {
        FileWriter fstream;
        BufferedWriter out = null;
        try {
            fstream = new FileWriter(addr2);
            out = new BufferedWriter(fstream);

            for (int i = 0; i < sortedLines.length; i++) {
                out.write(sortedLines[i] + newline);
            }
        } catch (IOException ex) {
            Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Read contents of a file into a string.
     * @param filename
     * @return string containing the contents of the file.
     * @throws java.io.IOException
     */
    public static String fileToString(String filename) throws IOException {
        String lineSep = System.getProperty("line.separator");
        touch(new File(filename));
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String nextLine = "";
        StringBuffer sb = new StringBuffer();
        while ((nextLine = br.readLine()) != null) {
            sb.append(nextLine);
            //
            // note:
            //   BufferedReader strips the EOL character
            //   so we add a new one!
            //
            sb.append(lineSep);
        }
        return sb.toString();
    }

    /**
     * Usage: EasyInput.inputStr("<prompt>"); or EasyInput.inputInt("<prompt>");
     * 
     * @author ae1
     */
    static int inputInt(String s) {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(s);
        int i = 0;
        try {
            i = Integer.parseInt(input.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * Input a string from file
     * @param s addr of the file.
     * @return the string of the text in this text file.
     */
    public static String inputStr(String s) {
        String aLine = "";
        BufferedReader input =
                new BufferedReader(new InputStreamReader(System.in));
        System.out.print(s);
        try {
            aLine = input.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aLine;
    }

    /**
     * Writes a string to file
     * @param stringOut this string
     * @param addr to this file
     * @throws java.io.IOException
     */
    public static void writeString2File(String stringOut, String addr) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(addr));
        out.write(stringOut);
        out.close();
    }

    /**
     * Loads a directory via jFileChooser.
     * @return
     */
    public static File loadDir() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogType(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        return file;
    }

    public static File[] imagesInDir(File dir, boolean traverse_subdirs) {
        assert dir.exists() : "The directory of image library must exist.";

        File[] allFiles = dir.listFiles();

        if (allFiles == null) {
            return null;
        }

        ArrayList<File> outList = new ArrayList<File>();

        for (int i = 0; i < allFiles.length; i++) {
            String ext = allFiles[i].getName().toLowerCase();
            if (ext.endsWith("jpg") || ext.endsWith("gif") || ext.endsWith("png")) {
                outList.add(allFiles[i]);
            }
        }
//        System.out.println(outList.size());
        File[] out = outList.toArray(new File[outList.size()]);
//        System.out.println(out.length);
        return out;
    }

    public static void main(String[] args) {
        File[] f = imagesInDir(new File("/var/www/test/20100614/"), false);
        System.out.println(f[0].getAbsoluteFile());
    }

    public static String fileNameWithoutExtention(File srcImg) {
        String out = srcImg.getName();
        out = out.substring(0, out.lastIndexOf("."));
        return out;
    }

    public static String fileNameExtention(File srcImg) {
        String out = srcImg.getName();
        out = out.substring(out.lastIndexOf(".") + 1);
        return out;
    }

    public static String readFile(String in) {
        String out = "";
        try {
            FileReader input = new FileReader(in);

            BufferedReader bufRead = new BufferedReader(input);

            String line = bufRead.readLine();
            while (line != null) {
                out += line + "\n";
                line = bufRead.readLine();
            }

            bufRead.close();

        } catch (IOException e) {
            // If another exception is generated, print a stack trace
            e.printStackTrace();
        }
        return out;
    }

    public static void touch(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static File loadFile(Config c) {
        FileDialog fd = new FileDialog(new Frame(), "Open...", FileDialog.LOAD);
        fd.setDirectory(c.workDir);
        fd.setVisible(true);
        String a = fd.getDirectory()
                + System.getProperty("file.separator") + fd.getFile();
        return new File(a);
    }

    public static File[] imagesInDir(String inDir, boolean b) {
        return imagesInDir(new File(inDir), b);
    }

    public static void copyFile(String in, String out) throws Exception {
//        System.out.println("copying "+ in +" to "+ out);
        copyFile(new File(in), new File(out));
    }

    public static void copyFile(File in, File out) throws Exception {
        FileInputStream fis = new FileInputStream(in);
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
