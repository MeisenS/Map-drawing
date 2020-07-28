
public class Edge{
	String name;
	String v;
	String w;
	double weight;

	public Edge(String n, String x, String y, double wt){
		name = n;
		v = x;
		w = y;
		weight = wt;
	}

	public int compareTo(Edge o) {
		if(weight == o.weight){
			return 0;
		}
		else if (weight > o.weight){
			return 1;
		}
		else{
			return -1;
		}
	}


	
}