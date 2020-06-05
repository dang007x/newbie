package app.model;

import java.util.ArrayList;
import java.util.Random;

import api.Graph;
import api.Vertex;

public class Maze {
    private int[][] matrix;
    private int size;
    private int count;
    private int[] pos = { 1, 1 };
    private Graph<Double, Node> g = new Graph<>();
    private Node curNode;
    private Vertex<Node> v;
    private int index;
    private int oldDirection = -1;

    public Maze(int size) {
        if (size % 2 == 0) {
            this.size = size - 1;
        } else {
            this.size = size;
        }
        this.matrix = new int[this.size][this.size];
        this.count = (this.size - 1) * (this.size - 1) / 4;
    }

    public void createMatrix() {
        index = count + 1;
        matrix[0][1] = 3;
        curNode = new Node(String.valueOf(index - count), 0, 1, 0);
        v = new Vertex<Node>(curNode);
       
    }

    public void createMaze() {
        matrix[pos[0]][pos[1]] = 1;
        count--;
        curNode = new Node(String.valueOf(index - count), 1, 1, 0);
       
        Random r = new Random();

        while (count > 0) {

            ArrayList<Integer> direction = getDirection(pos);
            int k = r.nextInt(direction.size());
            createPath(pos, direction.get(k));
        }
    }

    private void format() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] == 2) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

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

    private void createPath(int[] pos, Integer integer) {
        int i = pos[0], j = pos[1];
        switch (integer) {
            case 0:
                if (matrix[i - 2][j] == 0) {
                    matrix[i - 1][j] = 1;
                    count--;
                    matrix[i - 2][j] = 1;
                }
                i = i - 2;
                pos[0] = i;
                break;
            case 1:
                if (matrix[i][j + 2] == 0) {
                    matrix[i][j + 1] = 1;
                    count--;
                    matrix[i][j + 2] = 1;
                }
                j += 2;
                pos[1] = j;
                break;
            case 2:
                if (matrix[i + 2][j] == 0) {
                    matrix[i + 1][j] = 1;
                    count--;
                    matrix[i + 2][j] = 1;
                }
                i = i + 2;
                pos[0] = i;
                break;
            case 3:
                if (matrix[i][j - 2] == 0) {
                    matrix[i][j - 1] = 1;
                    count--;
                    matrix[i][j - 2] = 1;
                }
                j = j - 2;
                pos[1] = j;
                break;
            default:
                break;
        }
    }

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

    public static void main(String[] args) {
        Maze m = new Maze(1000);
        // double s = System.currentTimeMillis();
        // m.createMatrix();
        m.createMaze();
        // m.format();
        m.print();
        // double e = System.currentTimeMillis();
        // System.out.println(e - s);

    }

}
