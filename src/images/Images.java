package images;
//References
//https://community.oracle.com/thread/1269440

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.Random;
import static java.lang.Math.abs;

public class Images {
    BufferedImage img;
    BufferedImage bw;
    BufferedImage st;
    int height;
    int width;
    int[][] graph;
    
    public Images(){
        try{
        File f1 = new File("flower.jpg");
        img = ImageIO.read(f1);
        }
        catch (IOException e){};
        height = img.getHeight();
        width = img.getWidth();
        graph = new int[width][height];
    }

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
               Color c = new Color (st.getRGB(j, i));
               Color white = new Color(255,255,255,255);
               if (c.getGreen()==0){
                   if (!TakePixel()) st.setRGB(j, i, white.getRGB());
               }
            }
         }
        g.dispose();
    }
    
    public void initializeMatrix(int[][] matrix){
        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                matrix[x][y]=0;
            }
        }
    }
    
    public void setMatrix(int[][] graph){
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                Color c2 = new Color (st.getRGB(j, i));
                if (c2.getGreen()==0) graph[i][j]=1;
            }
        }
    }
    
    public Color getPixelColor(int x, int y){
        Color c = new Color (bw.getRGB(x, y));
        System.out.println("Blue: "+c.getBlue()+"Red: "+c.getRed()+"Green: "+c.getGreen()+"Alpha: "+c.getAlpha());
        return c;
    }
    
    public void printMatrix(int[][] matrix){
        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                System.out.print(matrix[x][y]+" ");
            }
            System.out.println();
        }
    }
    
    public int getTourSize (int[][] matrix){
        int numPts =0;
        for (int x=0; x<width; x++){
            for (int y=0; y<height; y++){
                if (matrix[x][y]==1) numPts++;
            }
        }
        return numPts;
    }

    public static void main(String[] args) throws IOException{       
        Images obj = new Images();
        obj.Stipple(obj.DiColor());
        obj.initializeMatrix(obj.graph);
        obj.setMatrix(obj.graph);
        MST mst = new MST (obj.width, obj.height, obj.graph);
        mst.setWeights(obj.graph);
        obj.printMatrix(mst.weights);
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
}
