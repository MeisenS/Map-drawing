import java.util.*;

public class Node implements Comparable<Node> {
    
    private static Double minLongitude = 180.0; 
    private static Double maxLongitude = 0.0;
    private static Double minLatitude = 180.0; 
    private static Double maxLatitude = 0.0;

    private Map<Node, Double> adj;
    
    private Double dist = Double.POSITIVE_INFINITY;
    private Node parent = null;
    private boolean visited = false;
    
    private String name;
    Double latitude;
    Double longitude;
    private int x, y;
    
   
    public Node(String name, Double latitude, Double longitude) {
        adj = new HashMap<Node, Double>();
        this.name = name; 
        this.latitude = latitude; 
        this.longitude = longitude;
        
        if(longitude != Double.NaN && latitude != Double.NaN) {
            if(longitude < minLongitude) minLongitude = longitude;
            if(longitude > maxLongitude) maxLongitude = longitude;
            if(latitude < minLatitude) minLatitude = latitude;
            if(latitude > maxLatitude) maxLatitude = latitude;
        } 
    }

   
    public Map<Node, Double> adj() { return adj; }
    public Double dist() { return dist; }
    public Node parent() { return parent; }
    public boolean visited() { return visited; }
    public String name() { return name; }
    public Double lat() { return latitude; }
    public Double lon() { return longitude; }
    public int x() { return x; }
    public int y() { return y; }

    public void setDist(Double d) { dist = d; }
    public void setParent(Node n) { parent = n; }
    public void setVisited(boolean v) { visited = v; }
    
    
    public int compareTo(Node n2) { 
        return name.compareTo(n2.name()); 
    }
    
   
  
    public void addAdj(Node newNode) {
        adj.put(newNode, computeDist(newNode));
    } 
    
    public void computeX(Double outMin, Double outMax) {
        x = (int) Math.round( map(longitude, minLongitude, maxLongitude, outMin, outMax) );
    }
    
   
    public void computeY(Double outMin, double outMax) { 
        y = (int) Math.round( map(latitude, minLatitude, maxLatitude, outMin, outMax) );
    } 
    
    /**
     * An implementation of the Haversine formula so that the geographic
     * distance between 2 nodes can be computed using their lattitudes
     * and longitudes.
     * @param n2 The node to compute the distance to
     * @return Double The distance to n2 in miles
     **/
    public Double computeDist(Node n2) {
        Double dLat = .5 * Math.toRadians(n2.lat() - latitude);
        Double dLon = .5 * Math.toRadians(n2.lon() - longitude);
        
        Double tmp = Math.cos(Math.toRadians(latitude)) * 
            Math.cos(Math.toRadians(n2.lat()));
        
        Double a = Math.pow(Math.sin(dLat),2) + Math.pow(Math.sin(dLon),2) * tmp;
        return 7917.82 * Math.asin(Math.sqrt(a));
    }
    
    /**
     * A custom implementation of the map function from Arduino - this takes
     * in a value and maps it from one range to another
     * @param n The value to be mapped
     * @param inMin The minimum value of n's starting range
     * @param inMax The maximum value of n's starting range
     * @param outMin The minimum value of n's ending range
     * @param outMax The maximum value of n's ending range
     * @return Double The new value of n
     **/
    public Double map(Double n, Double inMin, Double inMax, Double outMin, Double outMax) { 
        return (n - inMin)*(outMax - outMin) / (inMax - inMin) + outMin; 
    }
    
}