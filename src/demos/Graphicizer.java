package demos;

import java.io.*;
import java.awt.*;
import java.lang.Math;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*;

public class Graphicizer extends Frame implements ActionListener
{
    BufferedImage bufferedImage, bufferedImageBackup;
    Image image;
    Menu menu;
    MenuBar menubar;
    MenuItem menuitem1, menuitem2, menuitem3, menuitem4;
    Button button1, button2, button3, button4, button5;
    FileDialog dialog;

    public static void main(String[] args)
    {
        new Graphicizer();
    }

    public Graphicizer() {
        setSize(400, 360);

        setTitle("The Graphicizer");

        setVisible(true);

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(
                WindowEvent e){
                    System.exit(0);
                }
            }
        );

        menubar = new MenuBar();

        menu = new Menu("File");

        menuitem1 = new MenuItem("Open...");
        menu.add(menuitem1);
        menuitem1.addActionListener(this);

        menuitem2 = new MenuItem("Save As...");
        menu.add(menuitem2);
        menuitem2.addActionListener(this);

        menuitem3 = new MenuItem("Undo");
        menu.add(menuitem3);
        menuitem3.addActionListener(this);

        menuitem4 = new MenuItem("Exit");
        menu.add(menuitem4);
        menuitem4.addActionListener(this);

        menubar.add(menu);

        setMenuBar(menubar); 

        dialog = new FileDialog(this, "File Dialog");
    }

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == menuitem1){

            dialog.setMode(FileDialog.LOAD);

            dialog.setVisible(true);

            try{
                if(!dialog.getFile().equals("")){
                    File input = new File(dialog.getDirectory() + 
                        dialog.getFile()); 
                    bufferedImage = ImageIO.read(input); 

                    setSize(getInsets().left + getInsets().right + 
                        Math.max(400, bufferedImage.getWidth() + 60), 
                        getInsets().top + getInsets().bottom + 
                        Math.max(340, bufferedImage.getHeight() + 60));

                    button1.setBounds(30, getHeight() - 30, 60, 20);
                    button2.setBounds(100, getHeight() - 30, 60, 20);
                    button3.setBounds(170, getHeight() - 30, 60, 20);
                    button4.setBounds(240, getHeight() - 30, 60, 20);
                    button5.setBounds(310, getHeight() - 30, 60, 20);
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }

            repaint();

        } 

        if(event.getSource() == menuitem2){
             
            dialog.setMode(FileDialog.SAVE);

            dialog.setVisible(true);

            try{
                if(!dialog.getFile().equals("")){

                    String outfile = dialog.getFile();
                    File outputFile = new File(dialog.getDirectory() 
                        + outfile); 

                    ImageIO.write(bufferedImage, 
                        outfile.substring(outfile.length() - 3,  
                        outfile.length()), outputFile); 
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        } 

        if(event.getSource() == menuitem3){

            if (bufferedImageBackup != null){

                bufferedImage = bufferedImageBackup;

                setSize(getInsets().left + getInsets().right + Math.max
                    (400, bufferedImage.getWidth() + 60), 
                    getInsets().top + getInsets().bottom + 
                    Math.max(340, bufferedImage.getHeight() + 60));

                button1.setBounds(30, getHeight() - 30, 60, 20);
                button2.setBounds(100, getHeight() - 30, 60, 20);
                button3.setBounds(170, getHeight() - 30, 60, 20);
                button4.setBounds(240, getHeight() - 30, 60, 20);
                button5.setBounds(310, getHeight() - 30, 60, 20);

                repaint();
            }
        } 

        if(event.getSource() == menuitem4){
            System.exit(0);
        }

        if(event.getSource() == button1){

            bufferedImageBackup = bufferedImage;

            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            
            int pixels[] = new int[width * height];

            PixelGrabber pg = new PixelGrabber(bufferedImage, 0, 0, 
                width, height, pixels, 0, width);

            try {
                pg.grabPixels();
            }
            catch(InterruptedException e){
                System.out.println(e.getMessage());
            }

            for (int x = 0; x <= 1; x++){
                for (int y = 0; y < height - 1; y++){
                    pixels[x + y * width] = 0x88888888 ;
                }
            }

            for (int x = width - 2; x <= width - 1; x++){
                for (int y = 0; y < height - 1; y++){
                    pixels[x + y * width] = 0x88888888 ;
                }
            }

            for (int x = 0; x <= width - 1; x++){
                for (int y = 0; y <= 1; y++){
                    pixels[x + y * width] = 0x88888888 ;
                }
            }

            for (int x = 0; x <= width -1; x++){
                for (int y = height - 2; y <= height - 1; y++){
                    pixels[x + y * width] = 0x88888888 ;
                }
            }

            for (int x = 2; x < width - 1; x++){
                for (int y = 2; y < height - 1; y++){
    
                    int red = ((pixels[(x + 1) + y * width + 1] & 0xFF) 
                    - (pixels[x + y * width] & 0xFF)) + 128;

                    int green = (((pixels[(x + 1) + y * width + 1] 
                        & 0xFF00) / 0x100) % 0x100 - 
                        ((pixels[x + y * width] 
                        & 0xFF00) / 0x100) % 0x100) + 128;

                    int blue = (((pixels[(x + 1) + y * width + 1] & 
                        0xFF0000) / 0x10000) % 0x100 - 
                        ((pixels[x + y * width] & 
                        0xFF0000) / 0x10000) % 0x100) + 128;
            
                    int avg = (red + green + blue) / 3;

                    pixels[x + y * width] = (0xff000000 | avg << 16 | 
                        avg << 8 | avg);
                }
            }

            image = createImage(new MemoryImageSource(width, height, 
                pixels, 0 , width));

            bufferedImage = new BufferedImage (width, height,           
               BufferedImage.TYPE_INT_BGR ); 

            bufferedImage.createGraphics().drawImage(image, 0, 0, 
                this); 

            repaint();
        }

        if(event.getSource() == button2){
            bufferedImageBackup = bufferedImage;

            Kernel kernel =
                new Kernel(3, 3, new float[] {
                    0.0f, -1.0f,  0.0f,
                    -1.0f,  5.0f, -1.0f,
                    0.0f, -1.0f,  0.0f
                });   

            ConvolveOp convolveOp =
                new ConvolveOp(
                    kernel, ConvolveOp.EDGE_NO_OP, null);

            BufferedImage temp = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(),  
                BufferedImage.TYPE_INT_ARGB);

            convolveOp.filter(bufferedImage, temp);

            bufferedImage = temp;

            repaint();
        }

        if(event.getSource() == button3){

            bufferedImageBackup = bufferedImage;

            Kernel kernel = new Kernel(1, 1, new float[] {2});

            ConvolveOp convolveOp 
                = new ConvolveOp(kernel);

            BufferedImage temp = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(),       
                BufferedImage.TYPE_INT_ARGB);

            convolveOp.filter(bufferedImage, temp);

            bufferedImage = temp;

            repaint();
        }

        if(event.getSource() == button4){

            bufferedImageBackup = bufferedImage;

            Kernel kernel = new Kernel(3, 3, new float[] 
                {.25f, 0,  .25f,
                 0,   0,  0,
                 .25f, 0,  .25f});

            ConvolveOp convolveOp 
                = new ConvolveOp(kernel);

            BufferedImage temp = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(), 
                BufferedImage.TYPE_INT_ARGB);

            convolveOp.filter(bufferedImage, temp);

            bufferedImage = temp;

            repaint();
        }

        if(event.getSource() == button5){
            bufferedImageBackup = bufferedImage;

            image = bufferedImage.getScaledInstance
                (bufferedImage.getWidth()/2, 
                bufferedImage.getHeight()/2, 0);

            bufferedImage = new BufferedImage (
                bufferedImage.getWidth()/2, 
                bufferedImage.getHeight()/2,           
                BufferedImage.TYPE_INT_BGR ); 

            bufferedImage.createGraphics().drawImage(image, 0, 0, 
                this ); 

            setSize(getInsets().left + getInsets().right + 
                Math.max(400, bufferedImage.getWidth() + 60), 
                getInsets().top + getInsets().bottom + 
                Math.max(340, bufferedImage.getHeight() + 60));

            button1.setBounds(30, getHeight() - 30, 60, 20);
            button2.setBounds(100, getHeight() - 30, 60, 20);
            button3.setBounds(170, getHeight() - 30, 60, 20);
            button4.setBounds(240, getHeight() - 30, 60, 20);
            button5.setBounds(310, getHeight() - 30, 60, 20);

            repaint();
        }
    }

    @Override
    public void paint(Graphics g) 
    {
        if(bufferedImage != null){
            g.drawImage(bufferedImage, 
                getSize().width / 2 - bufferedImage.getWidth() / 2,
                getInsets().top + 20, this);
        }
    }
}
