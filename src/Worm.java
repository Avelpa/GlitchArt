
import java.awt.Point;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kobed6328
 */
public class Worm {
    
    private ArrayList<Point> points;
    private Point head;
    private Point newHead;
    private Point closestPoint;
    
    public Worm(int x, int y)
    {
        points = new ArrayList();
        head = new Point(x, y);
        points.add(head);
    }
    
    public Point move(ArrayList<Point> targets)
    {
        genClosestPoint(targets);
        int dirX = (int)Math.signum(closestPoint.x-head.x);
        int dirY = (int)Math.signum(closestPoint.y-head.y);
        
        if (dirX == 0 && dirY == 0)
            targets.remove(closestPoint);
        else {
            newHead = new Point(head.x + dirX, head.y+dirY);
            head = newHead;
            points.add(head);
        }
        
        return head;
    }
    
    private void genClosestPoint(ArrayList<Point> targets)
    {
        double minRadius = Double.MAX_VALUE;
        double r;
        
        for (Point p: targets)
        {
            r = Math.sqrt(Math.pow(Math.abs(p.x-head.x), 2) + Math.pow(Math.abs(p.y-head.y), 2));
            if (r < minRadius)
            {
                minRadius = r;
                closestPoint = p;
            }
        }
    }
    
    public ArrayList<Point> getPoints()
    {
        return points;
    }
}
