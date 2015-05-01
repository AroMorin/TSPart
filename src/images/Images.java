package images;

import java.awt.*;
//import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Random;
import static java.lang.Math.abs;
//import java.awt.image.RescaleOp;

public class Images {
    BufferedImage img;
    BufferedImage bw;
    BufferedImage st;
    int height;
    int width;
    
    public Images(){
        try{
        File f1 = new File("flower.jpg");
        img = ImageIO.read(f1);
        }
        catch (IOException e){};
        height = img.getHeight();
        width = img.getWidth();
    }
 /*
    //public void paint(Graphics) {
    //    gg.drawImage(bw, 0, 0, null);
    //}

 
   
    public Dimension getPreferredSize() {
        if (img == null) return new Dimension(100,100);
        else return new Dimension(width, height);
    }
 
    public void Greyscale(){
        //scans pixels and converts them to greyscale 
         for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){
                
               Color c = new Color(img.getRGB(j, i));
               int red = (int)(c.getRed() * 0.2126);
               int green = (int)(c.getGreen() * 0.7152);
               int blue = (int)(c.getBlue() *0.0722);
               Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
               img.setRGB(j,i,newColor.getRGB());
            }
         }
    }
    
    public void Washout(){
    RescaleOp brighten = new RescaleOp(1.2f, 15, null);
    brighten.filter(img, img);
    }
    //public BufferedImage BW(int width, int height){         
    //    return bw;
    //}
    */
    public Graphics2D DiColor(){
        bw = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g = bw.createGraphics();
        g.drawRenderedImage(img, null);
        //g.dispose();
        return g;
    }
    
    public boolean TakePixel(){
        Random rnd = new Random();
        return abs(rnd.nextGaussian())>1.5;
    }
    
    public void Stipple(Graphics2D g){
        st = bw;
        for(int i=0; i<height; i++){         
            for(int j=0; j<width; j++){
               //Color c = new Color(img.getRGB(j, i));
               //int red = (int)(c.getRed() * 0.2126);
               //int green = (int)(c.getGreen() * 0.7152);
               //int blue = (int)(c.getBlue() *0.0722);
               //Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
               //img.setRGB(j,i,newColor.getRGB());
               Color c = new Color (st.getRGB(j, i));
               Color white = new Color(255,255,255,255);
               if (c.getGreen()==0){
                   if (!TakePixel()) st.setRGB(j, i, white.getRGB());
               }
            }
         }
        g.dispose();
    }
    
    public Color getPixelColor(int x, int y){
        Color c = new Color (bw.getRGB(x, y));
        System.out.println("Blue: "+c.getBlue()+"Red: "+c.getRed()+"Green: "+c.getGreen()+"Alpha: "+c.getAlpha());
        return c;
    }

    public static void main(String[] args) throws IOException{       
        Images obj = new Images();
        obj.Stipple(obj.DiColor());
        JPanel cp = new JPanel(new GridLayout(2,1));
        cp.add(new JLabel(new ImageIcon(obj.bw)));
        cp.add(new JLabel(new ImageIcon(obj.st)));        
        JFrame f = new JFrame("Example");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new JScrollPane(cp));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
       }
            //Greyscale();
            //Washout();
            //f.addWindowListener(
        //    new WindowAdapter() {
        //        public void windowClosing(WindowEvent e) {System.exit(0);}
        //    }
        //);        
        //f.add( new Images());
        //Greyscale();
    }
