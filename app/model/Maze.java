package app.model;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

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
        this.pos = new int[2];
        pos[0] = 1;
        pos[1] = 1;
    }

    // Create maze and graph
    public void create() {
        // Set beginning
        matrix[0][1] = 3;
        Node n = new Node(String.valueOf(index++), 1, 0, 0);

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
        removeInvalidNode();

        // mark();

    }

    public void removeInvalidNode() {
        ArrayList<Vertex<Node>> vertices = g.vertices();
        int i = 1;
        while (i < vertices.size() - 2) {
            Vertex<Node> v = vertices.get(i);
            if (v.getElement().equals(new Node("1", 1, 1, 0))
                    || (v.getElement().equals(new Node("1", size - 2, size - 2, 0)))) {
                i++;

            } else {
                if (v.getAdjVertice().size() == 2) {
                    double[] pos1 = { v.getElement().getX(), v.getElement().getY() };
                    double[] pos2 = { v.getAdjVertice().get(0).getElement().getX(),
                            v.getAdjVertice().get(0).getElement().getY() };
                    double[] pos3 = { v.getAdjVertice().get(1).getElement().getX(),
                            v.getAdjVertice().get(1).getElement().getY() };
                    if ((pos1[0] == pos2[0] && pos2[0] == pos3[0]) || (pos1[1] == pos2[1] && pos2[1] == pos3[1])) {
                        g.addEdge(v.getAdjVertice().get(0), v.getAdjVertice().get(1));
                        g.remove(v.getElement());
                        continue;
                    }
                }
                i++;
            }
        }
    }

    // Coding continues...
    public ArrayList<Node> findPath() {
        ArrayList<Vertex<Node>> vertex = g.vertices();

        Node start = vertex.get(0).getElement();
        Node end = vertex.get(vertex.size() - 1).getElement();
        Node current = start;

        ArrayList<Node> open = new ArrayList<Node>();
        ArrayList<Node> close = new ArrayList<Node>();
        ArrayList<Node> path = new ArrayList<Node>();

        double sumCost = 0;

        open.add(start);
        start.setH(euclidDistence(start, end));
        start.setF(start.getH());
        start.setG(0);
        current.setParent(start);

        while (!open.isEmpty()) {
            Node min = new Node("label", 0, 0, 100000);
            for (Node i : open) {
                double gx = evalWeight(i, current) + sumCost;
                double hx = euclidDistence(i, end);
                double fx = gx + hx;
                i.setG(gx);
                i.setH(hx);
                i.setF(fx);

                if (i.getF() < min.getF()) {
                    min = i;
                }
            }
            current = min;
            double current_fx = current.getF();
            current.setF(current_fx);

            if (current.equals(end)) {
                break;
            }

            open.remove(current);

            Vertex<Node> neighbors = g.get(current);
            for (int i = 0; i < neighbors.getAdjVertice().size(); i++) {
                Node u = neighbors.getAdjVertice().get(i).getElement();
                
                u.setG(current.getF() - euclidDistence(u, end) + evalWeight(current, u));
                u.setF(u.getG() + euclidDistence(u, end));
                double i_cost = current.getG() + evalWeight(u, current);
                if (open.contains(u)) {
                    if (u.getG() <= i_cost) {
                        continue;
                    }
                } else if (close.contains(u)) {
                    if (u.getG() <= i_cost) {
                        continue;
                    }
                    open.add(u);
                    close.remove(u);
                    
                } else {
                    open.add(u);
                    u.setH(euclidDistence(u, end));
                    u.setG(i_cost);
                    u.setF(u.getG() + u.getH());
                    u.setParent(current);
                }

                sumCost += evalWeight(current, u);

            }
            close.add(current);
        }

        Node run = end;
        while (!run.equals(start)) {
            path.add(run.getParent());
            run = run.getParent();
        }

        Collections.reverse(path);

        return path;
    }

    public double euclidDistence(Node one, Node two) {
        return Math.sqrt((Math.pow(two.getX() - one.getX(), 2) + Math.pow(two.getY() - one.getY(), 2)));
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

    public void writer() {
        try {
            FileWriter write = new FileWriter("maze.txt");
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    write.write(matrix[i][j] + " ");
                }
                write.write("\n");
            }

            write.close();
        } catch (Exception e) {
            e.printStackTrace();
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

    public void mark() {
        ArrayList<Vertex<Node>> vertices = g.vertices();
        for (int i = 1; i < vertices.size() - 1; i++) {
            int row = (int) vertices.get(i).getElement().getY();
            int col = (int) vertices.get(i).getElement().getX();
            // System.out.println(row + " " + col);
            matrix[row][col] = 3;
        }
    }

    public static void main(String[] args) {
        Maze m = new Maze(15);
        // double s = System.currentTimeMillis();
        // System.out.println(m.count);
        m.create();

        // m.format();
        m.print();
        m.writer();
        // System.out.println(m.g.adjVertices(new Vertex<Node>(new Node("5", 4, 3,
        // 0))));
        // m.g.remove(new Node("5", 4, 3, 0));
        // ArrayList<Vertex<Node>> vertices = m.g.vertices();
        // for (int j = 0; j < vertices.size(); j++) {
        // System.out.println("Vertex: " + vertices.get(j).getElement().getX() + " "
        // + vertices.get(j).getElement().getY() + "------");
        // for (int i = 0; i < vertices.get(j).getAdjVertice().size(); i++) {
        // System.out.println(vertices.get(j).getAdjVertice().get(i));
        // }
        // }
        // =======
        // System.out.println(m.g.numEdge());
        // // System.out.println(m.g.adjVertices(new Vertex<Node>(new Node("5", 4, 3,
        // // 0))));
        // // m.g.remove(new Node("5", 4, 3, 0));
        // ArrayList<Vertex<Node>> vertices = m.g.vertices();
        // for (int j = 0; j < vertices.size(); j++) {
        // System.out.println("Vertex: " + vertices.get(j).getElement().getX() + " "
        // + vertices.get(j).getElement().getY() + "------");
        // for (int i = 0; i < vertices.get(j).getAdjVertice().size(); i++) {
        // System.out.println(vertices.get(j).getAdjVertice().get(i));
        // }
        // }
        // >>>>>>> d40d4465532d6e742097b507cbd3790095519704

        // System.out.println(m.g.numEdge());
        // double e = System.currentTimeMillis();
        // System.out.println(e - s);
        // PriorityQueue<Node> pq = new PriorityQueue<>();
        // pq.add(new Node("2", 0, 1, 10));
        // pq.add(new Node("1", 1, 1, 20));
        // pq.add(new Node("4", 1, 2, 012));
        // pq.add(new Node("5", 0, 2, 11));
        // System.out.println(pq.poll());
        ArrayList<Node> node = new ArrayList<Node>();
        node = m.findPath();

        for (int i = 0; i < node.size(); i++) {
            System.out.println(node.get(i));
        }

    }

}
