package algorithms;

import java.util.*;
import models.Graph;
import models.Node;

import java.util.LinkedList;
import java.util.Queue;
import models.Edge;

public class Maximum {

    private int[] parent;
    private Queue<Integer> queue;
    private Graph myGraph;
    private boolean[] fvisited;
    private int numberOfVertices;
     private boolean safe = false;
    private String message = null;
    /*
*/
    /**
     *
     * @param g
     */
    public Maximum(Graph g) {
        this.myGraph = g;
        safe = evaluate();
        numberOfVertices = myGraph.getSize();
        this.queue = new LinkedList<>();
        parent = new int[numberOfVertices + 1];
        fvisited = new boolean[numberOfVertices + 1];
        System.out.println("I am here ");

    }

 private boolean evaluate(){
        if(myGraph.getSource()==null){
            message = "Source must be present in the graph";
            return false;
        }

        if(myGraph.getDestination()==null){
            message = "Destination must be present in the graph";
            return false;
        }

        for(Node node : myGraph.getNodes()){
            if(!myGraph.isNodeReachable(node)){
                message = "Graph contains unreachable nodes";
                return false;
            }
        }

        return true;
    }

    private boolean bfs(int source, int goal, int graph[][]) {
        boolean pathFound = false;
        int destination, element;

        for (int vertex = 1; vertex <= numberOfVertices; vertex++) {
            parent[vertex] = -1;
            fvisited[vertex] = false;
        }
        queue.add(source);
        parent[source] = -1;
        fvisited[source] = true;

        while (!queue.isEmpty()) {
            element = queue.remove();
            destination = 1;

            while (destination <= numberOfVertices) {
                if (graph[element][destination] > 0 && !fvisited[destination]) {
                    parent[destination] = element;
                    queue.add(destination);
                    fvisited[destination] = true;
                }
                destination++;
            }
        }
        if (fvisited[goal]) {
            pathFound = true;
        }
        return pathFound;
    }

    private int fordFulkerson(int graph[][], int source, int destination) {
        int u, v;
        int maxFlow = 0;
        int pathFlow;

        int[][] residualGraph = new int[numberOfVertices + 1][numberOfVertices + 1];
        for (int sourceVertex = 1; sourceVertex <= numberOfVertices; sourceVertex++) {
            System.arraycopy(graph[sourceVertex], 1, residualGraph[sourceVertex], 1, numberOfVertices);
        }

        while (bfs(source, destination, residualGraph)) {
            pathFlow = Integer.MAX_VALUE;
            for (v = destination; v != source; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            for (v = destination; v != source; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public  String main (Graph G) throws IllegalStateException  {
        if(!safe) {
            throw new IllegalStateException(message);
        }
        
        int[][] graph; // Graph G
        int numberOfNodes;// G.nodes.size();
        int source;//G.getsource
        int sink;//G.getDistination
        int maxFlow;
        /****************************/
        // G=new Graph();
        /*Node N1=new Node(1),N2=new Node(2),N3=new Node(3),N4=new Node(4);
        Edge e1=new Edge(N1, N3),e2=new Edge(N3, N4),e3=new Edge(N2, N3),e4=new Edge(N1, N2),e5=new Edge(N2, N4);
        e1.setWeight(1); e2.setWeight(1);e4.setWeight(1);e5.setWeight(1);e2.setWeight(5);
        List<Node> nodes = Arrays.asList(N1, N2, N3,N4);
        List<Edge> edges = Arrays.asList(e1, e2, e3,e4,e5);
        G.setNodes(nodes);
        G.setEdges(edges);
        G.setSource(N1);
        G.setDestination(N4);
        */
       
        

        numberOfNodes = G.getSize();
        graph = new int[numberOfNodes + 1][numberOfNodes + 1];
        
        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++) {
            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++) {
                graph[sourceVertex][destinationVertex] = G.getEdges().get(destinationVertex).getWeight();
            }
        }

      
        source = G.getSource().getId();

        
        sink = G.getDestination().getId();

        Maximum fordFulkerson = new Maximum(G);
        maxFlow = fordFulkerson.fordFulkerson(graph, source, sink);
        return ("The Max Flow is " + maxFlow);
        
    }
}
