package app.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class MazePanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int timeSleep = 1000;
    private JPanel[][] maze;

    public MazePanel(int matrix[][]) {
        maze = new JPanel[matrix.length][matrix.length];
        this.setLayout(new GridLayout(maze.length, maze.length));
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                JPanel p = new JPanel();
                maze[i][j] = p;
                this.add(p);
                if (matrix[i][j] == 0) {

                    p.setBackground(Color.BLACK);

                }
                if (matrix[i][j] == 3) {
                    maze[i][j].setBackground(Color.GREEN);
                } if(matrix[i][j] == 1){
                    p.setBackground(Color.WHITE);
                }
            }
        }
    }

    public void reload(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                maze[i][j].setBackground(Color.white);
                if (matrix[i][j] == 0) {

                    maze[i][j].setBackground(Color.BLACK);

                }
                if (matrix[i][j] == 3) {
                    maze[i][j].setBackground(Color.GREEN);
                }
            }
        }
    }

    public void move(int x1, int y1, int x2, int y2) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (x1 == x2) {
                    for (int i = 0; i < y2 - y1; i++) {
                        maze[y1 + i][x1].setBackground(Color.GREEN);
                        try {
                            Thread.sleep(timeSleep);
                        } catch (InterruptedException e) {
                            
                            e.printStackTrace();
                        }
                    }
                }
                if (y1 == y2) {
                    for (int i = 0; i < x2 - x1; i++) {
                        maze[y1][x1 + i].setBackground(Color.GREEN);
                        try {
                            Thread.sleep(timeSleep);
                        } catch (InterruptedException e) {
                           
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        ;

    }

    public void step(int x1, int y1, int x2, int y2) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (x1 == x2) {
                    int i = y1;
                    while (i < y2) {
                        maze[i + 1][x1].setBackground(Color.GREEN);
                       
                        maze[i][x1].setBackground(Color.WHITE);
                        try {
                            Thread.sleep(timeSleep);
                        } catch (InterruptedException e) {
                            
                            e.printStackTrace();
                        }
                        i++;

                        
                    }
                }
                if (y1 == y2) {
                    int i = x1;
                    while (i < x2) {
                        maze[y1][i + 1].setBackground(Color.GREEN);
                        
                        maze[y1][i].setBackground(Color.WHITE);
                        try {
                            Thread.sleep(timeSleep);
                        } catch (InterruptedException e) {
                            
                            e.printStackTrace();
                        }
                        i++;

                       
                    }
                }
            }
        }).start();
    }
}
