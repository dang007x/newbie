package api;

import java.util.ArrayList;

public class Graph<E, V> {

    private ArrayList<Vertex<V>> vertices;
    private int numEdge;

    public Graph() {
        this.vertices = new ArrayList<>();
    }

    public void addVertex(V element) throws IllegalArgumentException {
        Vertex<V> v = new Vertex<V>(element);
        if (vertices.contains(v)) {
            throw new IllegalArgumentException("Vertex already exists");
        } else {
            vertices.add(v);
        }
    }

    public ArrayList<Vertex<V>> vertices() {
        return vertices;
    }

    public ArrayList<Vertex<V>> adjVertices(Vertex<V> v) {
        for (int i = 0; i < vertices.size(); i++) {
            if(v.equals(vertices.get(i))){
                return vertices.get(i).getAdjVertice();
            }
        }
        return null;
    }
    public int numEdge(){
       return numEdge;
    }

    public int size() {
        return vertices.size();
    }

    public void addEdge(Vertex<V> v, Vertex<V> u) throws IllegalArgumentException {
        if (!vertices.contains(v)) {
            throw new IllegalArgumentException("Vertex v: " + v + " not exists");
        }
        if (!vertices.contains(u)) {
            throw new IllegalArgumentException("Vertex u: " + u + " not exists");
        }
        v.getAdjVertice().add(u);
        u.getAdjVertice().add(v);
        numEdge++;
    }

    public Vertex<V> get(V element){
        for (int i = 0; i < vertices.size(); i++) {
            if(vertices.get(i).getElement().equals(element)){
                return vertices.get(i);
            }
        }
        return null;
    }

    public Vertex<V> remove(V element){
        Vertex<V> v = get(element);
        vertices.remove(v);
        for (int i = 0; i < v.getAdjVertice().size(); i++) {
            if(v.getAdjVertice().get(i).getAdjVertice().contains(v)){
                v.getAdjVertice().get(i).getAdjVertice().remove(v);
            }
        }
        return v;
    }

    public void print() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(vertices.get(i).toString());
        }
    }

    public static void main(String[] args) {
        Graph<Integer, Integer> g = new Graph<>();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        Vertex<Integer> v = new Vertex<Integer>(1);
        Vertex<Integer> u = new Vertex<Integer>(2);
        Vertex<Integer> z = new Vertex<Integer>(3);
        g.addEdge(v, u);
        g.addEdge(v, z);
        //g.remove(2);
        System.out.println(g.adjVertices(v));
    }
}