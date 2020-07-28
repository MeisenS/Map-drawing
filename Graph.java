
import java.awt.BorderLayout;
import java.io.File;
import java.util.*;

import javax.swing.JFrame;

public class Graph {
    private ArrayList<Node> nodes;
    private int vertexNum;
    private int edgeNum;
    boolean directed;
    private boolean adj[][];
    double distance;
    public double weight[][];
  
  
    PriorityQueue<Node> adji;
    ArrayList<Node> ps;
    ArrayList<Edge> smt;
    HashMap<String, Node> nodeMap;
    HashMap<String, Edge> edgeMap; 
    HashMap<String, Graphnode> adjNodeMap;
    Graphnode[] adjNode;
    
    public Graph() { nodes = new ArrayList<Node>(); }
    
    public ArrayList<Node> nodes() { return nodes; }
    
    public void addNode(Node n) { nodes.add(n); }
    
    
    public void sortNodes() { Collections.sort(nodes); }
    
   
    public void addEdge(String node1Name, String node2Name) {
        Node node1 = findNode(node1Name);
        Node node2 = findNode(node2Name);
        node1.addAdj(node2);
        node2.addAdj(node1);
    } 

    public Node findNode(String name) {
        int i = Collections.binarySearch(nodes, new Node(name, Double.NaN, Double.NaN) );
        assert (i >= 0) : "NODE CANNOT BE GOTTEN";
        return nodes.get(i);
    } 
  
    
    
    public void setupAdjNode(int num){
     adjNode = new Graphnode[num];
     int i = 0;
     for(String k : nodeMap.keySet()){
      
      if(k != null){
    	  adjNode[i] = new Graphnode(k);
    	  i++;
      	}
     		}
    }
    
    public void insertList(String a, String b, double lb, String rdName){
   Graphnode temp = adjNodeMap.get(a);
   
   if(temp == null){
    adjNodeMap.put(a, new Graphnode(a));
    temp = adjNodeMap.get(a);
   }
      
   temp.insert(temp,b,lb, temp.idnum, rdName);
   temp = adjNodeMap.get(b);
      
   if(temp == null){
    adjNodeMap.put(b, new Graphnode(b));
    temp = adjNodeMap.get(b);
   }
      
   
   temp.insert(temp,a,lb, temp.idnum, rdName);
  }
    
    
    public static Graph createFromFile(String fileName){
   Graph graph = new Graph();
   File file = new File(fileName);
   Scanner scanner;
   try{
    scanner=new Scanner(file);
        
   }catch(Exception e){
    System.out.println("File Not Found");
    return null;
   }
   scanner.useDelimiter("\\t|\\n");
   int i = 0;
   while(scanner.hasNext()){
    if (scanner.next().equals("r")){
     break;
    }
    else{
     String s= scanner.next();
     double LatiY = scanner.nextFloat();
     double LongiX = scanner.nextFloat();
     Node n = new Node(s,LatiY,LongiX);
     graph.nodeMap.put(s, n);
    }
    i++;
   }
      
     
      String a = scanner.next();
      String b = scanner.next();
      String c = scanner.next();
      Node first = graph.nodeMap.get(b);
      Node second = graph.nodeMap.get(c);
      double lb = getWeight(first, second);
      graph.insertList(b, c, lb, a);
      graph.edgeMap.put(a, new Edge(a, b, c, lb));
      graph.edgeNum++;
      
      while (scanner.hasNext()){
       String s1 = scanner.next(); //reads in and ignores the 'r'
       String s2 = scanner.next();
       String s3 = scanner.next();
       String s4 = scanner.next();
       Node n1 = graph.nodeMap.get(s3);
       Node n2 = graph.nodeMap.get(s4);
         double lbs = getWeight(n1, n2);
         graph.insertList(s3, s4, lbs, s2);
         graph.edgeMap.put(s2, new Edge(s2, s3, s4, lbs));
         graph.edgeNum++;
      }
      return graph;
    }
    
    public static double getWeight(Node nodea, Node nodeb){
     double weight1 = 0;
     double x = Math.abs(nodeb.latitude) - Math.abs(nodea.latitude);
     double y = Math.abs(nodeb.longitude) - Math.abs(nodea.longitude);
     double math = x*x + y*y;
     weight1 = Math.sqrt(math);
     return weight1;
    }
    
  public void priorityEdgeInsert(PriorityQueue<Edge> priorityqueue, Edge edge){
      Graphnode graphnode = adjNodeMap.get(edge.w);
      String rootName = graphnode.idnum;
      while (true){
        if(graphnode.next == null){
          break;
        }
        else{
          priorityqueue.add(new Edge(graphnode.next.road, rootName, graphnode.next.idnum, graphnode.next.weight));
          graphnode = graphnode.next;
        }
      }


      graphnode = adjNodeMap.get(edge.v);
      rootName = graphnode.idnum;
      while (true){
        if(graphnode.next == null){
          break;
        }
        else{
          priorityqueue.add(new Edge(graphnode.next.road, rootName, graphnode.next.idnum, graphnode.next.weight));
          graphnode = graphnode.next;
        }
      }
    }
    
   
    public void dijkstra(Node source) {
        
        Comparator<Node> distComparator = new Comparator<Node>() {
            public int compare(Node node1, Node node2) {
                return Double.compare(node1.dist(), node2.dist());
            }
        };

        PriorityQueue<Node> unvisited = new PriorityQueue<Node>(distComparator);
        source.setDist(0.0);
        unvisited.add(source);
        
        Node newnode;
        while( (newnode = unvisited.poll()) != null) {
            if(newnode.visited()) continue;
            newnode.setVisited(true);

            for(Map.Entry<Node, Double> neighbor : newnode.adj().entrySet()) {
                Double newDist = newnode.dist() + neighbor.getValue();
                Node k = neighbor.getKey();
                if( (newDist < k.dist()) && !k.visited() ) {
                    k.setDist(newDist);
                    k.setParent(newnode);
                    unvisited.add(k);
                }
            }
        }
        return;
    } 

   
    public void getShortestPath(Node fromNode, Node toNode, LinkedList<Node> path) {
        if(toNode.parent() != null) {
            path.addFirst(toNode);
            getShortestPath(fromNode, toNode.parent(), path);
        } else if (toNode.compareTo(fromNode) == 0) {
            path.addFirst(toNode);
        }
    }
    
    
}