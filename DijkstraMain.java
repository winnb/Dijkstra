import java.util.ArrayList;
import java.util.Random;

public class DijkstraMain {

	public static void main(String[] args) {
		ArrayList<Node> nodeList = createNodes(); // Create nodes
		nodeList = createEdges(nodeList); // Create edges
		//Node start = nodeList.get(new Random().nextInt(nodeList.size())); // Set initial node to random
		Node start = nodeList.get(0); // Set initial node to a
		dijkstra(start); // Run Dijkstra's Algorithm
	}
	
	static ArrayList<Node> createNodes() {
		Node a = new Node('a');
		Node b = new Node('b');
		Node c = new Node('c');
		Node d = new Node('d');
		Node e = new Node('e');
		Node f = new Node('f');
		Node g = new Node('g');
		Node h = new Node('h');
		ArrayList<Node> nodeList = new ArrayList<Node>();
		nodeList.add(a);
		nodeList.add(b);
		nodeList.add(c);
		nodeList.add(d);
		nodeList.add(e);
		nodeList.add(f);
		nodeList.add(g);
		nodeList.add(h);
		return nodeList;
	}
	
	static ArrayList<Node> createEdges(ArrayList<Node> nodeList) {
		Edge AB = new Edge(nodeList.get(0), nodeList.get(1), 15);
		Edge AC = new Edge(nodeList.get(0), nodeList.get(2), 2);
		Edge AD = new Edge(nodeList.get(0), nodeList.get(3), 3);
		Edge BC = new Edge(nodeList.get(1), nodeList.get(2), 8);
		Edge BE = new Edge(nodeList.get(1), nodeList.get(4), 2);
		Edge BF = new Edge(nodeList.get(1), nodeList.get(5), 1);
		Edge CF = new Edge(nodeList.get(2), nodeList.get(5), 7);
		Edge CG = new Edge(nodeList.get(2), nodeList.get(6), 5);
		Edge DE = new Edge(nodeList.get(3), nodeList.get(4), 1);
		Edge FG = new Edge(nodeList.get(5), nodeList.get(6), 2);
		Edge GH = new Edge(nodeList.get(6), nodeList.get(7), 1);
		nodeList.get(0).addEdge(AB);
		nodeList.get(0).addEdge(AC);
		nodeList.get(0).addEdge(AD);
		nodeList.get(1).addEdge(AB);
		nodeList.get(1).addEdge(BC);
		nodeList.get(1).addEdge(BE);
		nodeList.get(2).addEdge(AC);
		nodeList.get(2).addEdge(BC);
		nodeList.get(2).addEdge(CF);
		nodeList.get(2).addEdge(CG);
		nodeList.get(3).addEdge(AD);
		nodeList.get(3).addEdge(DE);
		nodeList.get(4).addEdge(DE);
		nodeList.get(4).addEdge(BE);
		nodeList.get(5).addEdge(BF);
		nodeList.get(5).addEdge(CF);
		nodeList.get(5).addEdge(FG);
		nodeList.get(6).addEdge(CG);
		nodeList.get(6).addEdge(FG);
		nodeList.get(6).addEdge(GH);
		nodeList.get(7).addEdge(GH);
		return nodeList;
	}

	static void dijkstra(Node start) {
		ArrayList<Edge> Q = new ArrayList<Edge>(); // Unlike BFS/DFS all edges must be explored, not just all nodes
		for (int i=0; i<start.getEdges().size(); i++) // Add all edges of start into queue
			Q.add(start.getEdges().get(i));
		start.setDst(0);
		start.setParent(start);
		System.out.println("Shortest Paths and their Distances:\n"+start.getName()+": Dst=0 Path="+start.getName());
		while (Q.size()>0) {
			Edge temp = Q.get(0); // Pop current edge
			//System.out.println("|"+temp.getA().getName()+temp.getB().getName()+"|");
			Q.remove(0);
			temp.explore();
			if (temp.getA().hasParent() == false && temp.getB().hasParent() == true) { // First node of current edge has no parent, so set parent to other node
				temp.getA().setParent(temp.getB());
				temp.getA().setDst(temp.getB().getDst()+temp.getDst()); // Child dst = parent dst + dst of edge between them
				System.out.print(temp.getA().getName()+": Dst="+temp.getA().getDst()+" Path="+temp.getA().getName());
				Node current = temp.getA();
				while (current.getParent().getName() != current.getName()) { // Trace ancestors to print path
					System.out.print("->"+current.getParent().getName());
					current = current.getParent();
				}
				System.out.print("\n");
				for (int j=0; j<temp.getA().getEdges().size(); j++) // Add all edges of child to queue if they haven't been explored
					if (temp.getA().getEdges().get(j).isExplored() == false)
						Q.add(temp.getA().getEdges().get(j));
			}
			else if (temp.getB().hasParent() == false && temp.getA().hasParent() == true) { // First node of current edge already has parent, so set other node to child
				temp.getB().setParent(temp.getA());
				temp.getB().setDst(temp.getA().getDst()+temp.getDst()); // Child dst = parent dst + dst of edge between them
				System.out.print(temp.getB().getName()+": Dst="+temp.getB().getDst()+" Path="+temp.getB().getName());
				Node current = temp.getB();
				while (current.getParent().getName() != current.getName()) { // Trace ancestors to print path
					System.out.print("->"+current.getParent().getName());
					current = current.getParent();
				}
				System.out.print("\n");
				for (int j=0; j<temp.getB().getEdges().size(); j++) // Add all edges of child to queue
					if (temp.getB().getEdges().get(j).isExplored() == false)
						Q.add(temp.getB().getEdges().get(j));
			}
			else { // Both nodes already have parents 
				if (temp.getA().getDst()+temp.getDst() < temp.getB().getDst()) {
					Node oldParent = temp.getB().getParent();
					Edge oldEdge = temp.getB().getParentEdge();
					temp.getB().setParent(temp.getA());
					temp.getB().setDst(temp.getA().getDst()+temp.getDst());
					System.out.print(temp.getB().getName()+": Dst="+temp.getB().getDst()+" Path="+temp.getB().getName());
					Node current = temp.getB();
					while (current.getParent().getName() != current.getName()) { // Trace ancestors to print path
						System.out.print("->"+current.getParent().getName());
						current = current.getParent();
					}
					System.out.print("\n");
					if (temp.getB().getDst()+oldEdge.getDst() < oldParent.getDst()) {
						oldParent.setParent(temp.getB());
						oldParent.setDst(temp.getB().getDst()+oldEdge.getDst());
						System.out.print(oldParent.getName()+": Dst="+oldParent.getDst()+" Path="+oldParent.getName());
						current = oldParent;
						while (current.getParent().getName() != current.getName()) { // Trace ancestors to print path
							System.out.print("->"+current.getParent().getName());
							current = current.getParent();
						}
						System.out.print("\n");
					}
				}
				else if (temp.getB().getDst()+temp.getDst() < temp.getA().getDst()) {
					Node oldParent = temp.getA().getParent();
					Edge oldEdge = temp.getA().getParentEdge();
					temp.getA().setParent(temp.getB());
					temp.getA().setDst(temp.getB().getDst()+temp.getDst());
					System.out.print(temp.getA().getName()+": Dst="+temp.getA().getDst()+" Path="+temp.getA().getName());
					Node current = temp.getA();
					while (current.getParent().getName() != current.getName()) { // Trace ancestors to print path
						System.out.print("->"+current.getParent().getName());
						current = current.getParent();
					}
					System.out.print("\n");
					if (temp.getA().getDst()+oldEdge.getDst() < oldParent.getDst()) {
						oldParent.setParent(temp.getA());
						oldParent.setDst(temp.getA().getDst()+oldEdge.getDst());
						System.out.print(oldParent.getName()+": Dst="+oldParent.getDst()+" Path="+oldParent.getName());
						current = oldParent;
						while (current.getParent().getName() != current.getName()) { // Trace ancestors to print path
							System.out.print("->"+current.getParent().getName());
							current = current.getParent();
						}
						System.out.print("\n");
					}
				}
				
			}
		}
	}
	
}
