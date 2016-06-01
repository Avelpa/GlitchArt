
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
    
    Stack<Node> path = new Stack();
    Queue<Node> nodes = new LinkedList();
    
    public Worm(int x, int y, int maxSize)
    {
        points = new LinkedList();
        points.add(0, new Point(x, y));
        this.maxSize = maxSize;
        shrinking = false;
    }
    
    public void move(ArrayList<Point> targets, Color[][] grid)
    {
        // old stuff without pathfinding
//        genClosestPoint(targets);
//        int dirX = (int)Math.signum(closestPoint.x-points.peek().x);
//        int dirY = (int)Math.signum(closestPoint.y-points.peek().y);
//        
//        if (dirX == 0 && dirY == 0){
//            targets.remove(closestPoint);
//            grid[points.peek().y][points.peek().x] = Color.BLACK;
//        }
//        else {
//            int newX = points.peek().x + dirX;
//            int newY = points.peek().y + dirY;
//            
//            if (points.size() < maxSize){
//                points.add(0, new Point(newX, newY));
//            } else {
//                grid[points.getLast().y][points.getLast().x] = Color.BLACK;
//                points.getLast().setLocation(newX, newY);
//                points.add(0, points.removeLast());
//            }
//        }
//        grid[points.peek().y][points.peek().x] = Color.WHITE;
        
        generatePath(grid);
        genClosestPoint(targets);
        if (!path.isEmpty()){
            if (points.size() < maxSize){
                points.add(0, new Point(path.peek().x, path.peek().y));
            } else {
                grid[points.getLast().y][points.getLast().x] = Color.BLACK;
                points.getLast().setLocation(path.peek().x, path.peek().y);
                points.add(0, points.removeLast());
            }
        }
        if (points.peek().x == closestPoint.x && points.peek().y == closestPoint.y){
            targets.remove(closestPoint);
            grid[closestPoint.y][closestPoint.x] = Color.BLACK;
        }
        grid[points.peek().y][points.peek().x] = Color.WHITE;
    }
    
    private void generatePath(Color[][] grid){
        boolean[][] searched = new boolean[grid.length][grid[0].length];
        
        nodes.clear();
        nodes.add(new Node(points.peek().x, points.peek().y, null));
        
        Node curPoint;
        Node finalNode = null;
        while (!nodes.isEmpty())
        {
            curPoint = nodes.remove();
            if (grid[curPoint.y][curPoint.x] == Color.GREEN){
                finalNode = curPoint;
//                nodes.add(curPoint);
                break;
            }
            if (grid[curPoint.y][curPoint.x] == Color.DARK_GRAY)
                continue;
            
            if (curPoint.x > 0)
            {
                // left
                if (!searched[curPoint.y][curPoint.x-1]){
                    nodes.add(new Node(curPoint.x-1, curPoint.y, curPoint));
                    searched[curPoint.y][curPoint.x-1] = true;
                }
//                // top left
//                if (curPoint.y > 0 && !searched[curPoint.y-1][curPoint.x-1]){
//                    nodes.add(new Node(curPoint.x-1, curPoint.y-1, curPoint));
//                    searched[curPoint.y-1][curPoint.x-1] = true;
//                }
//                // bottom left
//                if (curPoint.y < map.length-1 && !searched[curPoint.y+1][curPoint.x-1]){
//                    nodes.add(new Node(curPoint.x-1, curPoint.y+1, curPoint));
//                    searched[curPoint.y+1][curPoint.x-1] = true;
//                }
            }
            if (curPoint.x < grid[0].length-1)
            {
                // right
                if (!searched[curPoint.y][curPoint.x+1])
                {
                    nodes.add(new Node(curPoint.x+1, curPoint.y,curPoint));
                    searched[curPoint.y][curPoint.x+1] = true;
                }
//                // top right
//                if (curPoint.y > 0 && !searched[curPoint.y-1][curPoint.x+1]){
//                    nodes.add(new Node(curPoint.x+1, curPoint.y-1, curPoint));
//                    searched[curPoint.y-1][curPoint.x+1] = true;
//                }
//                // bottom right
//                if (curPoint.y < map.length-1 && !searched[curPoint.y+1][curPoint.x+1]){
//                    nodes.add(new Node(curPoint.x+1, curPoint.y+1, curPoint));
//                    searched[curPoint.y+1][curPoint.x+1] = true;
//                }
            }
            // top
            if (curPoint.y > 0 && !searched[curPoint.y-1][curPoint.x])
            {
                nodes.add(new Node(curPoint.x, curPoint.y-1, curPoint));
                searched[curPoint.y-1][curPoint.x] = true;
            }
            // bottom
            if (curPoint.y < grid.length-1 && !searched[curPoint.y+1][curPoint.x]){
                nodes.add(new Node(curPoint.x, curPoint.y+1, curPoint));
                searched[curPoint.y+1][curPoint.x] = true;
            }
        }
        path = new Stack();
        
        Node curNode = finalNode;
        while (curNode != null && curNode.parent != null){
            path.push(curNode);
            curNode = curNode.parent;
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
    
    private class Node{
        
        int x, y;
        Node parent;
        
        public Node(int x, int y, Node parent)
        {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
}
