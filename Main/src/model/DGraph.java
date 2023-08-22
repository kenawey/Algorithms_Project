/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author mohamed
 */
public class DGraph extends Graph{
   @Override
   public void addEdge(Edge e){
          boolean added = false;
        for(Edge edge : edges){
            if(edge.equalsForD(e)){
                added = true;
                break;
            }
        }
        if(!added)
            edges.add(e);
   }
   
int[][] rMatrix(Graph g){
int [][]graph=new int[getSize()+1][ getSize()+1];
Graph G=g;
for (int sourceVertex = 1; sourceVertex <= getSize(); sourceVertex++) {
            for (int destinationVertex = 1; destinationVertex <= getSize(); destinationVertex++) {
                graph[sourceVertex][destinationVertex] = G.getEdges().get(destinationVertex).getWeight();
            }
        }
return graph;
}

    
}
