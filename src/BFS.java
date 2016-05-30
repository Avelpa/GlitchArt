
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
public class BFS {
    
    
    static int[][] map = {
        {0, 0, 0},
        {-1, 0, 0},
        {2, 0, 0}
    };
    static boolean[][] searched = new boolean[100][100];
    public static void main(String[] args)
    {
        
        Queue<Node> points = new LinkedList();
        points.add(new Node(0, 0, null));
        
        Node curPoint;
        
        Node finalNode = null;
        while (!points.isEmpty())
        {
            curPoint = points.remove();
            if (map(curPoint) == 2){
                finalNode = curPoint;
                break;
            }
            if (map(curPoint) == -1)
                continue;
            
            if (curPoint.x > 0)
            {
                // left
                if (!searched[curPoint.y][curPoint.x-1]){
                    points.add(new Node(curPoint.x-1, curPoint.y, curPoint));
                    searched[curPoint.y][curPoint.x-1] = true;
                }
//                // top left
//                if (curPoint.y > 0 && !searched[curPoint.y-1][curPoint.x-1]){
//                    points.add(new Node(curPoint.x-1, curPoint.y-1, curPoint));
//                    searched[curPoint.y-1][curPoint.x-1] = true;
//                }
//                // bottom left
//                if (curPoint.y < map.length-1 && !searched[curPoint.y+1][curPoint.x-1]){
//                    points.add(new Node(curPoint.x-1, curPoint.y+1, curPoint));
//                    searched[curPoint.y+1][curPoint.x-1] = true;
//                }
            }
            if (curPoint.x < map[0].length-1)
            {
                // right
                if (!searched[curPoint.y][curPoint.x+1])
                {
                    points.add(new Node(curPoint.x+1, curPoint.y,curPoint));
                    searched[curPoint.y][curPoint.x+1] = true;
                }
//                // top right
//                if (curPoint.y > 0 && !searched[curPoint.y-1][curPoint.x+1]){
//                    points.add(new Node(curPoint.x+1, curPoint.y-1, curPoint));
//                    searched[curPoint.y-1][curPoint.x+1] = true;
//                }
//                // bottom right
//                if (curPoint.y < map.length-1 && !searched[curPoint.y+1][curPoint.x+1]){
//                    points.add(new Node(curPoint.x+1, curPoint.y+1, curPoint));
//                    searched[curPoint.y+1][curPoint.x+1] = true;
//                }
            }
            // top
            if (curPoint.y > 0 && !searched[curPoint.y-1][curPoint.x])
            {
                points.add(new Node(curPoint.x, curPoint.y-1, curPoint));
                searched[curPoint.y-1][curPoint.x] = true;
            }
            // bottom
            if (curPoint.y < map.length-1 && !searched[curPoint.y+1][curPoint.x]){
                points.add(new Node(curPoint.x, curPoint.y+1, curPoint));
                searched[curPoint.y+1][curPoint.x] = true;
            }
        }
        
        Stack<Node> path = new Stack();
        
        Node curNode = finalNode;
        while (curNode != null){
            path.push(curNode);
            curNode = curNode.parent;
        }
     
        while (!path.isEmpty())
        {
            System.out.println(path.peek().x + " " + path.pop().y);
        }
    }
    
    public static int map(Node p)
    {
        return map[p.y][p.x];
    }
    
    static class Node{
        
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
