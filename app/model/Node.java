package app.model;
public class Node implements Comparable<Node>{
    private String label;
    private double x;
    private double y;
    private double f;

    public Node(String label, double x, double y, double f) {
        this.label = label;
        this.x = x;
        this.y = y;
        this.f = f;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    @Override
    public boolean equals(Object object) {
        Node other = (Node) object;
        if(this.x == other.getX() && this.y == other.getY()){
            return true;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Vertex [label=" + label + ", x=" + x + ", y=" + y + ", f=" + f + "]";
    }

    @Override
    public int compareTo(Node o) {
        if(o.getF() > this.getF()){
            return -1;
        }
        else if(o.getF() < this.getF()){
            return 1;
        }
        
        return 0;
    }
}