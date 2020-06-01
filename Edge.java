
public class Edge {

	private Node parentA;
	private Node parentB;
	private int dst;
	private boolean explored;
	
	public Edge(Node a, Node b, int d) {
		parentA = a;
		parentB = b;
		dst = d;
		explored = false;
	}
	
	public Node getA() {
		return parentA;
	}
	
	public Node getB() {
		return parentB;
	}
	
	public int getDst() {
		return dst;
	}
	
	public boolean isExplored() {
		return explored;
	}
	
	public void setA(Node a) {
		parentA = a;
	}
	
	public void setB(Node b) {
		parentB = b;
	}
	
	public void setDst(int d) {
		dst = d;
	}
	
	public void explore() {
		explored = true;
	}
}
