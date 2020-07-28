
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class Mapping extends Canvas {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 1200, HEIGHT = 700;
    
    private static LinkedList<Node> shortestPath;
    private static Node fromNode, toNode;
    private static Graph myGraph;
    
    private static boolean direction = false;
    private static boolean show = false;
    
    public static void main(String[] args) {
        
        
        String file = args[0].trim();
        String fromName = "", toName = "";
        
        for(int i=1; i<args.length; i++) {
            if(args[i].trim().equalsIgnoreCase("-show")) {
                show = true;
            } else if (args[i].trim().equalsIgnoreCase("-directions")) {
                direction = true;
                fromName = args[i+1];
                toName = args[i+2];
            }
        }
        
        try { 
            myGraph = new Graph();
            shortestPath = new LinkedList<Node>();

            getInput(file); 
        } catch(IOException e) {
            System.out.println("Your file, " + file + " could not be read; try again.");
            return;
        }
        
        if(direction) { 
            fromNode = myGraph.findNode(fromName);
            toNode = myGraph.findNode(toName);

            myGraph.dijkstra(fromNode);
            myGraph.getShortestPath(fromNode, toNode, shortestPath);
            printpath();
        } 
        if(show) { 
            for(Node n : myGraph.nodes()) {
                n.computeX(WIDTH-50.0, 50.0);
                n.computeY(HEIGHT-50.0, 50.0);
            } 

            createWindow();
        } 
    } 
    
   
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        for(Node n1 : myGraph.nodes()) {
            for(Node n2 : n1.adj().keySet()) {
                if(n1.compareTo(n2) < 0)
                    g.drawLine(n1.x(), n1.y(), n2.x(), n2.y());
            }
        }
        
        if(direction) {
            g.setColor(Color.CYAN);
            Node first = fromNode;
            for(Node next : shortestPath) {              
                g.drawLine(first.x(), first.y(), next.x(), next.y());
                g.fillOval(first.x(), first.y(), 3, 3);
                first = next;
            }
        }
    
    }
    
    private static void printpath() {
        if(shortestPath.size() > 0) {
            System.out.printf("The shortest path from %s to %s is:\n\n",fromNode.name(), toNode.name());
            
            for(Node n : shortestPath)
                System.out.printf("%s -> ", n.name());
            System.out.printf("DONE!\n\n");
            
        } else {
            System.out.printf("THERE EXISTS NO ROUTE BETWEEN %s AND %s!\n\n", 
                               fromNode.name(), toNode.name());
        }
    }
    
    
    private static void getInput(String inputFile) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(inputFile));
        String nextLine;
        String[] params = null;
        
        while((nextLine = in.readLine()) != null) {
            params = nextLine.split("\\s+", 4);
            
            if(params[0].equals("i")) {
                parseIntersection(params);
            } else if(params[0].equals("r")) {
                myGraph.sortNodes();
                parseRoad(params);
                break;
            } else { 
                in.close();
                throw new IOException();
            }
            
        } 
        
        while((nextLine = in.readLine()) != null) {
            params = nextLine.split("\\s+", 4);
            
            if(params[0].equals("r")) {
                parseRoad(params);
            } else { 
                in.close();
                throw new IOException();
            }
            
        } 
        
        in.close();
    }
        
  
    private static void parseIntersection(String[] params) {
        Double latitude = Double.parseDouble(params[2]);
        Double longitude = Double.parseDouble(params[3]) * -1.0;
        Node n = new Node(params[1], latitude, longitude);
        myGraph.addNode(n); 
    } 
  
    private static void parseRoad(String[] params) {
        myGraph.addEdge(params[2], params[3]);
    } 
    
   
   
    private static void createWindow() {
        Mapping map = new Mapping();
        
        JFrame a = new JFrame("Mapping window");
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.pack();
        a.add(map);
        a.setSize(WIDTH, HEIGHT);
        a.setVisible(true);
    } 
    
} 