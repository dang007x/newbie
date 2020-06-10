package app.view;

import javax.swing.JPanel;
import java.awt.*;

public class MazePanel extends JPanel{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private JPanel[][] maze;
    private int width = 580/11;
    private int height = 575/11;

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
                }
            }
        }
        // for (int i = 0; i < matrix.length; i++) {
        // for (int j = 0; j < matrix.length; j++) {
        // System.out.print(matrix[i][j] + " ");
        // }
        // System.out.println();
        // }
    }

}