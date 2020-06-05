package api;

import java.util.ArrayList;

public class Vertex<V> {

    private V element;
    private ArrayList<Vertex<V>> adjVertice;

    public Vertex(V element) {
        this.element = element;
        this.adjVertice = new ArrayList<>();
    }

    public Vertex() {

    }

    public ArrayList<Vertex<V>> getAdjVertice() {
        return adjVertice;
    }

    public void setAdjVertice(ArrayList<Vertex<V>> adjVertice) {
        this.adjVertice = adjVertice;
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
        return String.valueOf(element);
    }
}