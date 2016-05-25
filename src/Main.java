
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Main extends JComponent implements MouseListener{
 
    static final int WIDTH = 1000, HEIGHT = 1000;
    static BufferedImage img;
    
    static Color[][] grid = new Color[50][50];
    
    static int pixWidth = WIDTH/grid[0].length;
    static int pixHeight = HEIGHT/grid.length;
    
    public static void main(String[] args){
        
        for (int y = 0; y < grid.length; y ++)
        {
            for (int x = 0; x < grid[y].length; x ++)
            {
                grid[y][x] = Color.BLACK;
            }
        }
        
        int fileNum = getNumImgs("images/")+1;
        
        Main main = new Main();
        
        JFrame frame = new JFrame("Colors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(main);
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setVisible(true);
        main.addMouseListener(main);
        
        Scanner in = new Scanner(System.in);
        System.out.print("save img_" + fileNum + "?\n>> ");
        if (in.next().startsWith("y")){
            saveImage(img, "images/img_" + fileNum + ".png");
            
            frame.setVisible(false); 
            frame.dispose();
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        try {
            for (int y = 0; y < grid.length; y ++)
            {
                for (int x = 0; x < grid[y].length; x ++)
                {
                    g.setColor(grid[y][x]);
                    g.fillRect(x*pixWidth, y*pixHeight, pixWidth, pixHeight);
                    Thread.sleep(1);
                }
            }
                    
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void saveImage(BufferedImage img, String filepath)
    {
        int[] rgbArray = new int[grid.length*grid[0].length];
        
        for (int y = 0; y < grid.length; y ++)
        {
            for (int x = 0; x < grid[y].length; x ++)
            {
                rgbArray[y*grid[y].length+x] = grid[y][x].getRGB();
            }
        }
        
        img = new BufferedImage( WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB );
        img.setRGB(0, 0, WIDTH, HEIGHT, rgbArray, 0, WIDTH);
        
        try {
            File outputfile = new File(filepath).getAbsoluteFile();
            ImageIO.write(img, "png", outputfile);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    private void spawnWorm(int x, int y )
    {
        double curColor = 255;

        while ((int)curColor > 0)
        {
            if (x >= grid[0].length || x < 0 || y >= grid.length || y < 0)
                break;
            
            grid[y][x] = new Color((int)curColor, (int)curColor, (int)curColor);
            
            
            int newX = x;
            int newY = y;
            
            int loop = 0;
            
            while (loop < 10 && grid[newY][newX] != Color.BLACK)
            {
                newX = x;
                newY = y;
                
                int randomNum = (int)(Math.random()*4)+1;
                switch(randomNum){
                    case 1:
                        if (newX < grid[0].length-1)
                            newX ++;
                        break;
                    case 2:
                        if (newX > 0)
                            newX --;
                        break;
                    case 3:
                        if (newY < grid.length-1);
                            newY ++;
                        break;
                    case 4:
                        if (newY > 0)
                            newY --;
                        break;
                }
                
                loop ++;
            }
            if (loop >= 10)
                break;
            x = newX;
            y = newY;
            curColor --;
        }
    }
        
        
    
    private static int getNumImgs(String filepath)
    {
        File[] folder = new File("images/").listFiles();
        int count = 0;
        for (File file: folder)
        {
            if (file.getName().endsWith(".png"))
                count ++;
        }
        return count;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        spawnWorm(e.getX()/pixWidth, e.getY()/pixHeight);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
