package app.model;

public class Edge {
    private double weight;
    private String label;

    public Edge(double weight, String label) {
        this.weight = weight;
        this.label = label;
    }

    public Edge() {

    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Edge [label=" + label + ", weight=" + weight + "]";
    }

}