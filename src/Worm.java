
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Worm {
    
    private LinkedList<Point> points;
    private Point closestPoint;
    private int maxSize;
    private boolean shrinking;
    
    public Worm(int x, int y, int maxSize)
    {
        points = new LinkedList();
        points.add(0, new Point(x, y));
        this.maxSize = maxSize;
        shrinking = false;
    }
    
    public void move(ArrayList<Point> targets, Color[][] grid)
    {
        genClosestPoint(targets);
        int dirX = (int)Math.signum(closestPoint.x-points.peek().x);
        int dirY = (int)Math.signum(closestPoint.y-points.peek().y);
        
        if (dirX == 0 && dirY == 0){
            targets.remove(closestPoint);
            grid[points.peek().y][points.peek().x] = Color.BLACK;
        }
        else {
            int newX = points.peek().x + dirX;
            int newY = points.peek().y + dirY;
            
            if (points.size() < maxSize){
                points.add(0, new Point(newX, newY));
            } else {
                grid[points.getLast().y][points.getLast().x] = Color.BLACK;
                points.getLast().setLocation(newX, newY);
                points.add(0, points.removeLast());
            }
        }
        grid[points.peek().y][points.peek().x] = Color.WHITE;
    }
    
    public void shrink(Color[][] grid)
    {
        if (points.size() > 1){
            grid[points.getLast().y][points.getLast().x] = Color.BLACK;
            points.removeLast();
        }
        if (points.size() == 1)
            shrinking = false;
    }
    
    private void genClosestPoint(ArrayList<Point> targets)
    {
        double minRadius = Double.MAX_VALUE;
        double r;
        
        for (Point p: targets)
        {
            r = Math.sqrt(Math.pow(Math.abs(p.x-points.peek().x), 2) + Math.pow(Math.abs(p.y-points.peek().y), 2));
            if (r < minRadius)
            {
                minRadius = r;
                closestPoint = p;
            }
        }
        
        if (minRadius == 1)
            shrinking = true;
            
    }
    
    public LinkedList<Point> getPoints()
    {
        return points;
    }
    public boolean shrinking()
    {
        return shrinking;
    }
}
