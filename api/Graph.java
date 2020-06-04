package api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph<E, V> {

    private Map<Vertex<V>, ArrayList<Vertex<V>>> adjVertex;

    public Graph() {
        this.adjVertex = new HashMap<>();
    }

    public int numVertices() {
        return adjVertex.size();
    }

    public void addVertex(V element) {
        adjVertex.putIfAbsent(new Vertex<V>(element), new ArrayList<Vertex<V>>());

    }

    public void addEdge(Vertex<V> v, Vertex<V> u, E element) {
        if(!adjVertex.containsKey(v) || !adjVertex.containsKey(u)){
            return;
        }
        if (adjVertex.get(v).contains(u)) {
            return;
        }
        adjVertex.get(v).add(u);
        adjVertex.get(u).add(v);
    }

    public void print(){

    }

}