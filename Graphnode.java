
public class Graphnode {

	String idnum;
	Graphnode next;
	double weight;
	int con;
	String parent;
	String road;
	
	public Graphnode(String k) {
		idnum=k;
		con=0;
	}
	
	public void insert(Graphnode n,String a,Double lb,String parent,String road) {
		if(n.next == null){
			n.next = new Graphnode(a);
		    n.next.weight = lb;
		    n.next.parent = parent;
		    n.next.road = road;
		    con++;
		}else{
			insert(n.next, a, lb, parent, road);
		}
	}
	
	public boolean lookup(Graphnode n, String a) {
		if(n == null){
			return false;
		}else if (n.idnum.equals(a)){
			return true;
		}else{
			return lookup(n.next, a);
		}
	}
	
	public double lookupWeight(Graphnode n, String a){
	    if(n == null){
	    	return -100;
	    }else if (n.idnum.equals(a)){
	    	return n.weight;
	    }
	    else{
	    	return lookupWeight(n.next, a);
	    }
	}
	
	public void print(){
	    if (next == null){
	    	return;
	    }else {
	    	System.out.print(next.idnum +", ");
	    	next.print();
	    }
	    
	}
	
	public Graphnode[] getcon(Graphnode n, Graphnode[] a, int position){
	    if (n.next == null){
	    	return a;
	    }else{
	    	a[position] = n.next;
	    	return getcon(n.next, a, position+1);
	    }
	  }
	
	public Graphnode[] getConnections(){
	    Graphnode[] temp = new Graphnode[con];
	    getcon(this,temp, 0);
	    return temp;
	}
	
	public boolean equals (String s){
	    return (s.equals(idnum));
	}
	
	public int compareTo(Graphnode o){
	    if (this.weight == o.weight){
	      return 0;
	    }
	    else if (this.weight > o.weight){
	      return 1;
	    }
	    else{
	      return -1;
	    }
	  }
	
}
