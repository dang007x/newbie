package api;

import java.util.Arrays;

public class Edge<E, V> {
    private Vertex<V>[] endpoints;
    private E element;

    public Edge(Vertex<V>[] endpoints, E element) {
        this.endpoints = endpoints;
        this.element = element;
    }

    public Edge() {
    }

    public Vertex<V>[] getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(Vertex<V>[] endpoints) {
        this.endpoints = endpoints;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    //dddd

    @Override
    public String toString() {
        return "Edge [endpoints=" + Arrays.toString(endpoints) + ", element=" + element + "]";
    }
    
    
    
}