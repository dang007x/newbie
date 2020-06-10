package app.model;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

import api.Graph;
import api.Vertex;

public class Maze {
    private int[][] matrix;
    private int size;
    private int count;
    private int[] pos = { 1, 1 };
    private Graph<Double, Node> g = new Graph<>();
    private Vertex<Node> v;
    private int index = 0;

    public Maze(int size) {
        if (size % 2 == 0) {
            this.size = size - 1;
        } else {
            this.size = size;
        }
        this.matrix = new int[this.size][this.size];
        this.count = (this.size - 1) * (this.size - 1) / 4;
    }
    // Create maze and graph
    public void create() {
        // Set beginning
        matrix[0][1] = 3;
        Node n = new Node(String.valueOf(index++), 0, 1, 0);

        // Add first node to graph
        matrix[pos[0]][pos[1]] = 1;
        count--;
        Node m = new Node(String.valueOf(index++), 1, 1, 0);

        g.addVertex(n);
        g.addVertex(m);
        v = g.get(n);
        Vertex<Node> u = g.get(m);
        g.addEdge(v, u);
        v = u;
        Random r = new Random();

        while (count > 0) {
            ArrayList<Integer> direction = getDirection(pos);
            int k = r.nextInt(direction.size());
            createPath(pos, direction.get(k));
        }
        // Set ending
        matrix[size - 2][size - 1] = 3;
        n = new Node(String.valueOf(index++), size - 1, size - 2, 0);
        m = new Node(String.valueOf(index), size - 2, size - 2, 0);
        g.addVertex(n);
        g.addEdge(g.get(n), g.get(m));

    }

    // Evaluate distance betweent two nodes
    private double evalWeight(Node a, Node b) {
        if (a.getX() == b.getX()) {
            if (a.getY() - b.getY() > 0) {
                return a.getY() - b.getY();
            } else {
                return b.getY() - a.getY();
            }
        } else {
            if (a.getX() - b.getX() > 0) {
                return a.getX() - b.getX();
            } else {
                return b.getX() - a.getX();
            }
        }
    }

    public void print() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    // Create path and add nodes to graph
    private void createPath(int[] pos, Integer direction) {
        int i = pos[0], j = pos[1];
        switch (direction) {
            case 0: // up

                // g.print();
                // System.out.println("----------");
                if (matrix[i - 2][j] == 0) { // if not visit
                    Node a = new Node(String.valueOf(index++), j, i - 2, 0);

                    matrix[i - 1][j] = 1;
                    count--;
                    matrix[i - 2][j] = 1;
                    g.addVertex(a);
                    Vertex<Node> u = g.get(a);
                    g.addEdge(v, u);
                }
                // if visited
                Node a = new Node(String.valueOf(index), j, i - 2, 0);
                v = g.get(a);
                i = i - 2;
                pos[0] = i;
                break;
            case 1: // right

                // g.print();
                // System.out.println("----------");
                if (matrix[i][j + 2] == 0) { // if node visit
                    Node b = new Node(String.valueOf(index++), j + 2, i, 0);
                    matrix[i][j + 1] = 1;
                    count--;
                    matrix[i][j + 2] = 1;
                    g.addVertex(b);
                    Vertex<Node> u = g.get(b);
                    g.addEdge(v, u);
                }
                // if visited
                Node b = new Node(String.valueOf(index), j + 2, i, 0);
                v = g.get(b);
                j += 2;
                pos[1] = j;
                break;
            case 2: // down

                // g.print();
                // System.out.println("----------");
                if (matrix[i + 2][j] == 0) { // if not visit
                    Node c = new Node(String.valueOf(index++), j, i + 2, 0);
                    matrix[i + 1][j] = 1;
                    count--;
                    matrix[i + 2][j] = 1;
                    g.addVertex(c);
                    Vertex<Node> u = g.get(c);
                    g.addEdge(v, u);
                }
                // if visited
                Node c = new Node(String.valueOf(index), j, i + 2, 0);
                i = i + 2;
                pos[0] = i;
                v = g.get(c);
                break;
            case 3: // left

                // g.print();
                // System.out.println("----------");
                if (matrix[i][j - 2] == 0) { // if not visit
                    Node d = new Node(String.valueOf(index++), j - 2, i, 0);
                    matrix[i][j - 1] = 1;
                    count--;
                    matrix[i][j - 2] = 1;
                    g.addVertex(d);
                    Vertex<Node> u = g.get(d);
                    g.addEdge(v, u);
                }
                // if visited
                Node d = new Node(String.valueOf(index), j - 2, i, 0);
                j = j - 2;
                pos[1] = j;
                v = g.get(d);
                break;
            default:
                break;
        }
    }

    // Return possible direction of position
    private ArrayList<Integer> getDirection(int[] pos) {
        ArrayList<Integer> dir = new ArrayList<>();
        int i = pos[0], j = pos[1];
        if (i - 2 > 0) {
            dir.add(0);
        }
        if (j + 2 < matrix.length) {
            dir.add(1);
        }
        if (i + 2 < matrix.length) {
            dir.add(2);
        }
        if (j - 2 > 0) {
            dir.add(3);
        }
        return dir;
    }

    public int[][] getMatrix() {
        return matrix;
    }
    public static void main(String[] args) {
        Maze m = new Maze(5);
        // double s = System.currentTimeMillis();
        // System.out.println(m.count);
        m.create();

        // m.format();
        m.print();
        System.out.println(m.g.adjVertices(new Vertex<Node>(new Node("5", 4, 3, 0))));
        m.g.remove(new Node("5", 4, 3, 0));
        ArrayList<Vertex<Node>> vertices = m.g.vertices();
        for (int j = 0; j < vertices.size(); j++) {
            System.out.println("Vertex: " + vertices.get(j).getElement().getX() + " "
                    + vertices.get(j).getElement().getY() + "------");
            for (int i = 0; i < vertices.get(j).getAdjVertice().size(); i++) {
                System.out.println(vertices.get(j).getAdjVertice().get(i));
            }
        }
        
        // System.out.println(m.g.numEdge());
        // double e = System.currentTimeMillis();
        // System.out.println(e - s);
        // PriorityQueue<Node> pq = new PriorityQueue<>();
        // pq.add(new Node("2", 0, 1, 10));
        // pq.add(new Node("1", 1, 1, 20));
        // pq.add(new Node("4", 1, 2, 012));
        // pq.add(new Node("5", 0, 2, 11));
        // System.out.println(pq.poll());



    }

}
