package demos;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.lang.Math;
import javax.imageio.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Graphics;

public class Painter extends Frame implements ActionListener, MouseListener, MouseMotionListener, ItemListener
{
    Point dot[] = new Point[1000];
    Point start = new Point(0, 0);
    Point end = new Point(0, 0);
    int dots = 0;
    private BufferedImage bufferedImage, tileImage;
    private Image image;
    private int imageWidth = 300;
    private int imageHeight = 300;
    private int offsetX = 50;
    private int offsetY = 60;
    private Color color = new Color(0, 0, 0);
    Menu menu1, menu2, menu3;
    MenuBar menubar;
    MenuItem openMenuItem, saveMenuItem, colorMenuItem, newMenuItem,
        exitMenuItem;
    CheckboxMenuItem linesMenuItem, ellipsesMenuItem,
        rectanglesMenuItem, roundedMenuItem, freehandMenuItem,
        solidMenuItem, gradientMenuItem, textureMenuItem,
        transparentMenuItem, textMenuItem, thickMenuItem,
        plainMenuItem, shadowMenuItem;
    FileDialog dialog;
    private String drawText = "";
    boolean mouseUp = false;
    boolean dragging = false;
    boolean draw = false;
    boolean line = false;
    boolean ellipse = false;
    boolean rectangle = false;
    boolean rounded = false;
    boolean text = false;
    boolean solid = false;
    boolean shade = false;
    boolean transparent = false;
    boolean texture = false;
    boolean red = false;
    boolean green = false;
    boolean blue = false;
    boolean thick = false;
    boolean adjusted = false;
    boolean shadow = false;
    OkCancelDialog textDialog;

    public static void main(String[] args)
    {
        new Painter();
    }

    public Painter()
    {
        setLayout(null);

        addMouseListener(this);
        addMouseMotionListener(this);

        menubar = new MenuBar();

        menu1 = new Menu("File");
        menu2 = new Menu("Draw");
        menu3 = new Menu("Effects");

        newMenuItem = new MenuItem("New");
        menu1.add(newMenuItem);
        newMenuItem.addActionListener(this);

        openMenuItem = new MenuItem("Open...");
        menu1.add(openMenuItem);
        openMenuItem.addActionListener(this);

        saveMenuItem = new MenuItem("Save As...");
        menu1.add(saveMenuItem);
        saveMenuItem.addActionListener(this);

        linesMenuItem = new CheckboxMenuItem("Draw lines");
        menu2.add(linesMenuItem);
        linesMenuItem.addItemListener(this);

        ellipsesMenuItem = new CheckboxMenuItem("Draw ellipses");
        menu2.add(ellipsesMenuItem);
        ellipsesMenuItem.addItemListener(this);

        rectanglesMenuItem = new CheckboxMenuItem("Draw rectangles");
        menu2.add(rectanglesMenuItem);
        rectanglesMenuItem.addItemListener(this);

        roundedMenuItem = new CheckboxMenuItem(
            "Draw rounded rectangles");
        menu2.add(roundedMenuItem);
        roundedMenuItem.addItemListener(this);

        freehandMenuItem = new CheckboxMenuItem("Draw freehand");
        menu2.add(freehandMenuItem);
        freehandMenuItem.addItemListener(this);

        plainMenuItem = new CheckboxMenuItem("Plain");
        menu3.add(plainMenuItem);
        plainMenuItem.addItemListener(this);

        solidMenuItem = new CheckboxMenuItem("Solid fill");
        menu3.add(solidMenuItem);
        solidMenuItem.addItemListener(this);

        gradientMenuItem = new CheckboxMenuItem("Gradient fill");
        menu3.add(gradientMenuItem);
        gradientMenuItem.addItemListener(this);

        textureMenuItem = new CheckboxMenuItem("Texture fill");
        menu3.add(textureMenuItem);
        textureMenuItem.addItemListener(this);

        transparentMenuItem = new CheckboxMenuItem("Transparent");
        menu3.add(transparentMenuItem);
        transparentMenuItem.addItemListener(this);

        textMenuItem = new CheckboxMenuItem("Draw text");
        menu2.add(textMenuItem);
        textMenuItem.addItemListener(this);

        thickMenuItem = new CheckboxMenuItem("Draw thick lines");
        menu3.add(thickMenuItem);
        thickMenuItem.addItemListener(this);

        shadowMenuItem = new CheckboxMenuItem("Drop shadow");
        menu3.add(shadowMenuItem);
        shadowMenuItem.addItemListener(this);

        colorMenuItem = new MenuItem("Select color...");
        menu3.add(colorMenuItem);
        colorMenuItem.addActionListener(this);

        exitMenuItem = new MenuItem("Exit");
        menu1.add(exitMenuItem);
        exitMenuItem.addActionListener(this);

        menubar.add(menu1);
        menubar.add(menu2);
        menubar.add(menu3);

        setMenuBar(menubar);
        dialog = new FileDialog(this, "File Dialog");

        bufferedImage = new BufferedImage (imageWidth, imageHeight,
           BufferedImage.TYPE_INT_BGR );

        setSize(400, 400);

        setTitle("Painter");
        setVisible(true);

        image = createImage(imageWidth, imageHeight);
        textDialog = new OkCancelDialog(this, "Enter your text", true);

        try{
           File inputFile = new File("tile.jpg");
           tileImage = ImageIO.read(inputFile);
        } catch (java.io.IOException ioe){
            System.out.println("Need tile.jpg.");
            System.exit(0);
        }

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(
                WindowEvent e){
                    System.exit(0);
                }
            }
        );
    }

    public void mousePressed(MouseEvent e)
    {
        mouseUp = false;
        start = new Point(e.getX(), e.getY());
        adjusted=true;
        repaint();
    }

    public void mouseReleased(MouseEvent e)
    {
        if(rounded || line || ellipse || rectangle || draw || text){
            end = new Point(e.getX(), e.getY());
            mouseUp = true;
            dragging = false;
            adjusted=false;
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e)
    {
        dragging = true;
        if(new Rectangle(offsetX, offsetY,
            imageWidth, imageHeight).contains(e.getX(), e.getY())){
            if(draw){
                dot[dots] = new Point(e.getX(), e.getY());
                dots++;
            }
        }
        if(new Rectangle(offsetX, offsetY,
            imageWidth, imageHeight).contains(start.x, start.y)){
            end = new Point(e.getX(), e.getY());
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}

    public void paint (Graphics g)
    {
        Graphics2D gImage;
        Paint paint;
        Composite composite;

        if(!dragging && !adjusted){
            if(image == null){
                image = createImage(imageWidth, imageHeight);
            }
            gImage = (Graphics2D) image.getGraphics();
        }
        else{
            gImage = (Graphics2D) g;
            g.drawImage(image, offsetX, offsetY, this);
            g.drawRect(offsetX, offsetY, imageWidth, imageHeight);
            gImage.clip(new Rectangle2D.Double(offsetX, offsetY,
                imageWidth, imageHeight));
        }

        composite = gImage.getComposite();

        if(color != null){
            gImage.setColor(color);
        } else {
            gImage.setColor(new Color(0, 0, 0));
        }

        if(thick){
            gImage.setStroke(new BasicStroke(10));
        } else{
            gImage.setStroke(new BasicStroke(1));
        }

        if (mouseUp || dragging) {
            Point tempStart, tempEnd;

            tempStart = new Point(Math.min(end.x, start.x),
                Math.min(end.y, start.y));
            tempEnd = new Point(Math.max(end.x, start.x),
                Math.max(end.y, start.y));

            tempStart = new Point(Math.max(tempStart.x, offsetX),
                Math.max(tempStart.y, offsetY));
            tempEnd = new Point(Math.max(tempEnd.x, offsetX),
                Math.max(tempEnd.y, offsetY));

            tempStart = new Point(Math.min(tempStart.x,
                bufferedImage.getWidth() + offsetX),
                Math.min(tempStart.y, bufferedImage.getHeight() +
                offsetY));
            tempEnd = new Point(Math.min(tempEnd.x,
                bufferedImage.getWidth() + offsetX),
                Math.min(tempEnd.y, bufferedImage.getHeight() +
                offsetY));

            if(!adjusted){
                tempEnd.x -= offsetX;
                tempEnd.y -= offsetY;
                tempStart.x -= offsetX;
                tempStart.y -= offsetY;
                end.x -= offsetX;
                end.y -= offsetY;
                start.x -= offsetX;
                start.y -= offsetY;
                adjusted=true;
            }

            int width = tempEnd.x - tempStart.x;
            int height = tempEnd.y - tempStart.y;

            if(line){
                Line2D.Double drawLine = new Line2D.Double(start.x,
                start.y, end.x, end.y);

                if(shadow){
                    paint = gImage.getPaint();
                    composite = gImage.getComposite();

                    gImage.setPaint(Color.black);
                    gImage.setComposite(AlphaComposite.getInstance
                        (AlphaComposite.SRC_OVER, 0.3f));

                    Line2D.Double line2 =
                        new Line2D.Double(start.x + 9, start.y + 9,
                        end.x + 9, end.y + 9);

                    gImage.draw(line2);

                    gImage.setPaint(paint);
                    gImage.setComposite(composite);
                }

                gImage.draw(drawLine);
            }

            if(ellipse && width != 0 && height != 0){

                Ellipse2D.Double ellipse =
                    new Ellipse2D.Double(tempStart.x, tempStart.y,
                    width, height);

                if(shadow){
                    paint = gImage.getPaint();
                    composite = gImage.getComposite();

                    gImage.setPaint(Color.black);
                    gImage.setComposite(AlphaComposite.getInstance
                        (AlphaComposite.SRC_OVER, 0.3f));

                    Ellipse2D.Double ellipse2 =
                        new Ellipse2D.Double(tempStart.x + 9,
                        tempStart.y + 9, width, height);

                    if(solid || shade || transparent || texture){
                        gImage.fill(ellipse2);
                    }
                    else{
                        gImage.draw(ellipse2);
                    }

                    gImage.setPaint(paint);
                    gImage.setComposite(composite);
                }

                if(solid){
                    gImage.setPaint(color);
                }

                if(shade){
                    gImage.setPaint(
                    new GradientPaint(
                        tempStart.x, tempStart.y, color,
                        tempEnd.x, tempEnd.y, Color.black));
                }

                if(texture){
                    Rectangle2D.Double anchor =
                    new Rectangle2D.Double(0,0,tileImage.getWidth(),
                    tileImage.getHeight());

                    TexturePaint texturePaint =
                        new TexturePaint(tileImage,anchor);

                    gImage.setPaint(texturePaint);
                }

                if(transparent){
                    gImage.setComposite(AlphaComposite.getInstance
                    (AlphaComposite.SRC_OVER,0.3f));
                }

                if(solid || shade || transparent || texture){
                    gImage.fill(ellipse);
                }
                else{
                    gImage.draw(ellipse);
                }

                if(transparent){
                    gImage.setComposite(composite);
                }
            }

            if(rectangle && width != 0 && height != 0){

                Rectangle2D.Double rectangle =
                    new Rectangle2D.Double(tempStart.x, tempStart.y,
                    width, height);

                if(shadow){
                    paint = gImage.getPaint();
                    composite = gImage.getComposite();

                    gImage.setPaint(Color.black);
                    gImage.setComposite(AlphaComposite.getInstance
                        (AlphaComposite.SRC_OVER, 0.3f));

                    Rectangle2D.Double rectangle2 =
                        new Rectangle2D.Double(tempStart.x + 9,
                        tempStart.y + 9, width, height);

                    if(solid || shade || transparent || texture){
                        gImage.fill(rectangle2);
                    }
                    else{
                        gImage.draw(rectangle2);
                    }

                    gImage.setPaint(paint);
                    gImage.setComposite(composite);
                }

                if(solid){
                    gImage.setPaint(color);
                }

                if(shade){
                    gImage.setPaint(
                        new GradientPaint(
                        tempStart.x, tempStart.y, color,
                        tempEnd.x, tempEnd.y, Color.black));
                     }

                if(transparent){
                    gImage.setComposite(AlphaComposite.getInstance
                    (AlphaComposite.SRC_OVER,0.3f));
                }

                if(texture){
                    Rectangle2D.Double anchor =
                        new Rectangle2D.Double(0,0,
                        tileImage.getWidth(), tileImage.getHeight());
                    TexturePaint texturePaint =
                        new TexturePaint(tileImage,anchor);

                    gImage.setPaint(texturePaint);
                }

                if(solid || shade || texture || transparent){
                    gImage.fill(rectangle);
                }
                else{
                    gImage.draw(rectangle);
                }

                if(transparent){
                    gImage.setComposite(composite);
                }
            }

            if(rounded && width != 0 && height != 0){

                RoundRectangle2D.Double round =
                    new RoundRectangle2D.Double(tempStart.x,
                    tempStart.y, width, height, 10, 10);

                if(shadow){
                    paint = gImage.getPaint();
                    composite = gImage.getComposite();

                    gImage.setPaint(Color.black);
                    gImage.setComposite(AlphaComposite.getInstance
                        (AlphaComposite.SRC_OVER, 0.3f));

                    RoundRectangle2D.Double round2 =
                        new RoundRectangle2D.Double(tempStart.x + 9,
                        tempStart.y + 9, width, height, 10, 10);

                    if(solid || shade || transparent || texture){
                        gImage.fill(round2);
                    }
                    else{
                        gImage.draw(round2);
                    }

                    gImage.setPaint(paint);
                    gImage.setComposite(composite);
                }

                if(solid){
                    gImage.setPaint(color);
                }

                if(shade){
                    gImage.setPaint(
                        new GradientPaint(
                            tempStart.x, tempStart.y, color,
                            tempEnd.x, tempEnd.y, Color.black));
                }

                if(transparent){
                    gImage.setComposite(AlphaComposite.getInstance
                    (AlphaComposite.SRC_OVER,0.3f));
                }

                if(texture){
                    Rectangle2D.Double anchor =
                        new Rectangle2D.Double(0,0,
                        tileImage.getWidth(), tileImage.getHeight());

                    TexturePaint texturePaint =
                        new TexturePaint(tileImage,anchor);

                    gImage.setPaint(texturePaint);
                }

                if(solid || shade || texture || transparent){
                    gImage.fill(round);
                }
                else{
                    gImage.draw(round);
                }

                if(transparent){
                    gImage.setComposite(composite);
                }
            }

            if(draw){
                Line2D.Double drawLine;

                if(shadow){
                    paint = gImage.getPaint();
                    composite = gImage.getComposite();

                    gImage.setPaint(Color.black);
                    gImage.setComposite(AlphaComposite.getInstance
                        (AlphaComposite.SRC_OVER, 0.3f));

                    for(int loop_index = 0; loop_index < dots - 1;
                        loop_index++){
                        if(dragging){
                            drawLine = new Line2D.Double(
                                dot[loop_index].x + 9,
                                dot[loop_index].y + 9,
                                dot[loop_index + 1].x + 9,
                                dot[loop_index + 1].y + 9);
                        }else{
                            drawLine = new Line2D.Double(
                                dot[loop_index].x - offsetX + 9,
                                dot[loop_index].y - offsetY + 9,
                                dot[loop_index + 1].x - offsetX + 9,
                                dot[loop_index + 1].y - offsetY + 9);
                        }
                        gImage.draw(drawLine);
                    }

                    gImage.setPaint(paint);
                    gImage.setComposite(composite);
                }

                for(int loop_index = 0; loop_index < dots - 1;
                    loop_index++){
                    if(dragging){
                        drawLine = new Line2D.Double(dot[loop_index].x,
                            dot[loop_index].y, dot[loop_index + 1].x,
                            dot[loop_index + 1].y);
                    }else{
                        drawLine = new Line2D.Double(dot[loop_index].x
                            - offsetX, dot[loop_index].y - offsetY,
                            dot[loop_index + 1].x - offsetX,
                            dot[loop_index + 1].y - offsetY);
                    }
                    gImage.draw(drawLine);
                }
                if(!dragging){
                    dots = 0;
                }
            }
        }

        if(text){
            if(drawText != null && end != null && end.x !=0 &&
                end.y !=0){

                if(shadow){
                    paint = gImage.getPaint();
                    composite = gImage.getComposite();

                    gImage.setPaint(Color.black);
                    gImage.setComposite(AlphaComposite.getInstance
                        (AlphaComposite.SRC_OVER, 0.3f));

                    gImage.drawString(drawText, end.x + 9, end.y + 9);

                    gImage.setPaint(paint);
                    gImage.setComposite(composite);
                }

                gImage.drawString(drawText, end.x, end.y);
            }
        }

        if(!dragging){
            g.drawImage(image, offsetX, offsetY, this);
        }
        g.setColor(Color.black);
        gImage.setStroke(new BasicStroke(1));
        g.drawRect(offsetX, offsetY, imageWidth, imageHeight);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == openMenuItem){

            try{
                dialog.setMode(FileDialog.LOAD);

                dialog.setVisible(true);

                if(dialog.getFile() != ""){
                    File inputFile = new File(dialog.getDirectory() +
                        dialog.getFile());
                    bufferedImage = ImageIO.read(inputFile);
                    if(bufferedImage != null){
                        image = createImage(bufferedImage.getWidth(),
                        bufferedImage.getHeight());
                        Graphics2D g2d = (Graphics2D)
                            image.getGraphics();
                        g2d.drawImage(bufferedImage, null, 0, 0);
                        imageWidth = bufferedImage.getWidth();
                        imageHeight = bufferedImage.getHeight();
                        setSize(imageWidth + 100, imageHeight + 90);
                        repaint();
                    }
                }
            }catch(Exception exp){
                System.out.println(exp.getMessage());
            }
        }

        if(e.getSource() == saveMenuItem){

            dialog.setMode(FileDialog.SAVE);

            dialog.setVisible(true);

            try{
                if(dialog.getFile() != ""){
                    String outfile = dialog.getFile();
                    File outputFile = new File(dialog.getDirectory() +
                        outfile);
                    bufferedImage.createGraphics().drawImage(image,
                        0, 0, this);
                    ImageIO.write(bufferedImage,
                        outfile.substring(outfile.length() - 3,
                        outfile.length()), outputFile);
                }
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }

        if(e.getSource() == colorMenuItem){
            color = JColorChooser.showDialog(this, "Select your color",
                Color.black);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == newMenuItem){
            bufferedImage = new BufferedImage (300, 300,
                BufferedImage.TYPE_INT_BGR );
            image = createImage(300, 300);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
            repaint();
        }

        if(e.getSource() == exitMenuItem){
            System.exit(0);
        }
    }

    public void itemStateChanged(ItemEvent e)
    {
        if(e.getSource() == linesMenuItem){
            setFlagsFalse();
            line = true;
            linesMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == ellipsesMenuItem){
            setFlagsFalse();
            ellipse = true;
            ellipsesMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == rectanglesMenuItem){
            setFlagsFalse();
            rectangle = true;
            rectanglesMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == roundedMenuItem){
            setFlagsFalse();
            rounded = true;
            roundedMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == freehandMenuItem){
            setFlagsFalse();
            draw = true;
            freehandMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == solidMenuItem){
            solid = !solid;
            if(solid){
                texture = false;
                shade = false;
            }
            solidMenuItem.setState(solid);
            gradientMenuItem.setState(shade);
            textureMenuItem.setState(texture);
            plainMenuItem.setState(false);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == gradientMenuItem){
            shade = !shade;
            if(shade){
                solid = false;
                texture = false;
            }
            solidMenuItem.setState(solid);
            gradientMenuItem.setState(shade);
            textureMenuItem.setState(texture);
            plainMenuItem.setState(false);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == textureMenuItem){
            texture = !texture;
            if(texture){
                shade = false;
                solid = false;
            }
            solidMenuItem.setState(solid);
            gradientMenuItem.setState(shade);
            textureMenuItem.setState(texture);
            plainMenuItem.setState(false);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == transparentMenuItem){
            transparent = !transparent;
            transparentMenuItem.setState(transparent);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == textMenuItem){
            textDialog.setVisible(true);
            drawText = textDialog.data;
            setFlagsFalse();
            text = true;
            textMenuItem.setState(true);
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == thickMenuItem){
            thick = thickMenuItem.getState();
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == plainMenuItem){
            solidMenuItem.setState(false);
            gradientMenuItem.setState(false);
            textureMenuItem.setState(false);
            transparentMenuItem.setState(false);
            plainMenuItem.setState(true);
            shade = false;
            solid = false;
            transparent = false;
            texture = false;
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }

        if(e.getSource() == shadowMenuItem){
            shadow = shadowMenuItem.getState();
            start.x = -20;
            start.y = -20;
            end.x = -20;
            end.y = -20;
        }
    }

    void setFlagsFalse()
    {
        rounded = false;
        line = false;
        ellipse = false;
        rectangle = false;
        draw = false;
        text = false;
        linesMenuItem.setState(false);
        ellipsesMenuItem.setState(false);
        rectanglesMenuItem.setState(false);
        roundedMenuItem.setState(false);
        freehandMenuItem.setState(false);
        textMenuItem.setState(false);
    }
}

class OkCancelDialog extends Dialog implements ActionListener
{
    Button ok, cancel;
    TextField text;
    public String data;

    OkCancelDialog(Frame hostFrame, String title, boolean dModal)
    {
        super(hostFrame, title, dModal);
        setSize(280, 100);
        setLayout(new FlowLayout());
        text = new TextField(30);
        add(text);
        ok = new Button("OK");
        add(ok);
        ok.addActionListener((ActionListener)this);
        cancel = new Button("Cancel");
        add(cancel);
        cancel.addActionListener(this);
        data = new String("");
    }

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == ok){
            data = text.getText();
        } else {
            data = "";
        }
        setVisible(false);
    }
}

