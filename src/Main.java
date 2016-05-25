
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
public class Main extends JComponent implements MouseListener, KeyListener{
 
    static final int WIDTH = 1000, HEIGHT = 1000;
    static JFrame frame;
    
    Color[][] grid = new Color[100][100];
    BufferedImage img;
    
    int pixWidth = WIDTH/grid[0].length;
    int pixHeight = HEIGHT/grid.length;
    
    boolean done = false;
    ArrayList<Point> drawOrder = new ArrayList();
    int lastDrawnPoint = -1;
    
    final int FPS = 60;
    
    Color backGroundColor = Color.BLACK;
    
    public static void main(String[] args){
        
        Main main = new Main();
        
        frame = new JFrame("Colors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(main);
        frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.pack();
        frame.setVisible(true);
        main.addMouseListener(main);
        frame.addKeyListener(main);
        
        main.run();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(backGroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        for (int i = 0; i <= lastDrawnPoint; i ++)
        {
            Point gp = drawOrder.get(i);
            g.setColor(grid[gp.y][gp.x]);
            g.fillRect(gp.x*pixWidth, gp.y*pixHeight, pixWidth, pixHeight);
        }
    }
    
    private void saveImage()
    {
        int[] rgbArray = new int[grid.length*grid[0].length];
        
        for (int y = 0; y < grid.length; y ++)
        {
            for (int x = 0; x < grid[y].length; x ++)
            {
                rgbArray[y*grid[y].length+x] = grid[y][x].getRGB();
            }
        }
        
        img = new BufferedImage( grid[0].length, grid.length, BufferedImage.TYPE_INT_RGB );
        img.setRGB(0, 0, grid[0].length, grid.length, rgbArray, 0, grid[0].length);
        
        
        
        try {
            int fileNum = getNumImgs("images/")+1;
            String filepath = "images/img_" + fileNum + ".png";
            File outputfile = new File(filepath).getAbsoluteFile();
            ImageIO.write(img, "png", outputfile);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    private void run()
    {
        for (int y = 0; y < grid.length; y ++)
        {
            for (int x = 0; x < grid[y].length; x ++)
            {
                grid[y][x] = Color.BLACK;
            }
        }
        while(!done)
        {
            
            
            backGroundColor = backGroundColor.darker();
            
            if (lastDrawnPoint < drawOrder.size()-1)
            {
                lastDrawnPoint ++;
            }
            repaint();
            
            try {
                Thread.sleep(1000/FPS);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
        }
            
        frame.setVisible(false); 
        frame.dispose();
    }
    
    private void spawnWorm(int x, int y )
    {
        double curColor = 255;

        while ((int)curColor > 0)
        {
            grid[y][x] = new Color((int)curColor, (int)curColor, (int)curColor);
            drawOrder.add(new Point(x, y));
            
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
                        if (newY < grid.length-1)
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
        int clickX = e.getX()/pixWidth;
        int clickY = e.getY()/pixHeight;
        if (grid[clickY][clickX] == Color.BLACK)
            spawnWorm(e.getX()/pixWidth, e.getY()/pixHeight);
        else
            backGroundColor = Color.RED;
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar()== 's')
        {
            saveImage();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
