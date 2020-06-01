import java.util.ArrayList;

public class Node {
	
	private char name;
	private ArrayList<Edge> edges;
	private int dst;
	private Node parent;
	
	public Node(char newName) {
		name = newName;
		edges = new ArrayList<Edge>();
		dst = 1000000; // essentially infinity
		parent = null;
	}
	
	public char getName() {
		return name;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Edge getParentEdge() {
		Edge pe = null;
		for (int i=0; i<edges.size(); i++) 
			if (edges.get(i).getA() == parent || edges.get(i).getB() == parent)
				pe = edges.get(i);
		return pe;
	}
	
	public int getDst() {
		return dst;
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}
	
	public void setDst(int d) {
		dst = d;
	}
	
	public void setParent(Node p) {
		parent = p;
	}
	
	public boolean hasParent() {
		if (parent == null)
			return false;
		else
			return true;
	}
}
