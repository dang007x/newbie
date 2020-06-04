package app.model;

public class Vertex {
    private String label;
    private double x;
    private double y;
    private double f;

    public Vertex(String label, double x, double y, double f) {
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
    public String toString() {
        return "Vertex [f=" + f + ", label=" + label + ", x=" + x + ", y=" + y + "]";
    }
    
    
}