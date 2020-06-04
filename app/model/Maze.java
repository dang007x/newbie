package app.model;

import java.util.ArrayList;
import java.util.Random;

import api.Graph;

public class Maze {
    private int[][] matrix;
    private int size;
    private int count;
    private int[] pos = { 1, 1 };
    private Graph<Vertex, Edge> g = new Graph<>();

    public Maze(int size) {
        this.size = formatSize(size);
    }

    private int formatSize(int size) {
        if (size % 2 == 0) {
            size--;
        }
        return size;
    }

    public void createMatrix() {
        matrix = new int[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i % 2 != 0 && j % 2 != 0) {
                    matrix[i][j] = 0;
                    count++;
                } else {
                    matrix[i][j] = 1;
                }
            }
        }
    }

    public void createMaze() {
        matrix[pos[0]][pos[1]] = 2;
        count--;
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
                    matrix[i - 1][j] = 0;
                    count--;
                    matrix[i - 2][j] = 2;
                }
                i = i - 2;
                pos[0] = i;
                break;
            case 1:
                if (matrix[i][j + 2] == 0) {
                    matrix[i][j + 1] = 0;
                    count--;
                    matrix[i][j + 2] = 2;
                }
                j += 2;
                pos[1] = j;
                break;
            case 2:
                if (matrix[i + 2][j] == 0) {
                    matrix[i + 1][j] = 0;
                    count--;
                    matrix[i + 2][j] = 2;
                }
                i = i + 2;
                pos[0] = i;
                break;
            case 3:
                if (matrix[i][j - 2] == 0) {
                    matrix[i][j - 1] = 0;
                    count--;
                    matrix[i][j - 2] = 2;
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
        Maze m = new Maze(10);
        // double s = System.currentTimeMillis();
        m.createMatrix();
        m.createMaze();
        m.format();
        m.print();
        // double e = System.currentTimeMillis();
        // System.out.println(e - s);

    }

}
