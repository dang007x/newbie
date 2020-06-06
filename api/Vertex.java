package api;

import java.util.ArrayList;

public class Vertex<V> {

    private V element;
    private ArrayList<Vertex<V>> adjVertices;

    public Vertex(V element) {
        this.element = element;
        this.adjVertices = new ArrayList<>();
    }

    public Vertex() {

    }

    public ArrayList<Vertex<V>> getAdjVertice() {
        return adjVertices;
    }

    public void setAdjVertice(ArrayList<Vertex<V>> adjVertice) {
        this.adjVertices = adjVertice;
    }

    public V getElement() {
        return element;
    }

    public void setElement(V element) {
        this.element = element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object other) {
        Vertex<V> o = (Vertex<V>) other;
        if (this.element.equals(o.getElement())) {
            return true;
        }
        return false;
    }

   

    @Override
    public String toString() {
        return element.toString();
    }
}