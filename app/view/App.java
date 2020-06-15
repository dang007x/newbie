package app.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;

import app.model.Maze;
import app.model.Node;

public class App extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JButton playButton;
    private JButton solveButton;
    private JButton changeMaze;
    private JButton newGameButton;
    private JButton tutorial;
    private MazePanel mazePanel1;
    private int status = 0;
    private int mazeSize = 20;
    private Integer[] currentPos = { 0, 1 };
    private Integer[] currentPos2 = { 0, 1 };

    private Maze maze = new Maze(mazeSize);
    private int[][] matrix = maze.getMatrix();
    private Color buttonColor = new Color(19, 15, 64);
    private Color fore = new Color(255, 255, 255);
    private MazePanel mazePanel2;

    private String message = "1. Click \"Play\" to start a game. \n"
            + "2. Player 1 using W, A, S, D to move Up, Left, Down, Right. \n"
            + "    Player 2 using I, J, K, L to move Up, Left, Down, Right. \n"
            + "3. Click \"Solve\" to find the path.\n" + "4. Click \" Change Maze\" to create a new maze.\n"
            + "5. Click \"New Game\" to create a new game.\n";

    public App(int size) {
        if (size != 0) {
            this.mazeSize = size;
            this.maze = new Maze(mazeSize);
            this.currentPos = new Integer[2];
            currentPos[0] = 0;
            currentPos[1] = 1;
        }
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1300, 700);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 1300, 50);
        buttonPanel.setLayout(new GridLayout(0, 5));

        playButton = new JButton("Play");

        playButton.setForeground(fore);
        playButton.setBackground(buttonColor);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                changeMaze.setEnabled(false);
                solveButton.setEnabled(true);
                maze.writer();
                status = 1;
                System.out.println("Clicked Play");

            }

        });
        playButton.addKeyListener(new CustomKeyListener2());
        playButton.addKeyListener(new CustomKeyListener());
        solveButton = new JButton("Solve");
        solveButton.setEnabled(false);
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ArrayList<Node> path1 = maze.findPath();
                // for (int i = 0; i < path.size(); i++) {
                // System.out.println(path.get(i));
                // }

                mazePanel1.move(path1);
                mazePanel1.reload(maze.getMatrix());
                maze.reload();
                ArrayList<Node> path2 = maze.BFS();
                mazePanel2.move(path2);
                mazePanel2.reload(maze.getMatrix());
                System.out.println("Clicked Play");

            }

        });
        changeMaze = new JButton("ChangeMaze");
        
        newGameButton = new JButton("New Game");

        solveButton.setForeground(fore);
        solveButton.setBackground(buttonColor);

        changeMaze.setForeground(fore);
        changeMaze.setBackground(buttonColor);
        changeMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String size = JOptionPane.showInputDialog(null, "Input size");

                if (!size.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Invalid size");

                }
                dispose();
                App app = new App(Integer.valueOf(size));
                app.setVisible(true);

                System.out.println("Clicked Change Maze");
            }
        });

        newGameButton.setForeground(fore);
        newGameButton.setBackground(buttonColor);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                maze = new Maze(mazeSize);
                maze.create();
                matrix = maze.getMatrix();
                mazePanel1.reload(maze.getMatrix());
                mazePanel2.reload(maze.getMatrix());
                currentPos[0] = 0;
                currentPos[1] = 1;
                status = 0;
                solveButton.setEnabled(false);
                changeMaze.setEnabled(true);
                System.out.println("Clicked New Game");

            }

        });

        tutorial = new JButton("Tutorial");
        tutorial.setForeground(fore);
        tutorial.setBackground(buttonColor);
        tutorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(null, message);
            }

        });

        buttonPanel.add(playButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(changeMaze);
        buttonPanel.add(newGameButton);
        buttonPanel.add(tutorial);

        maze.create();
        mazePanel1 = new MazePanel(maze.getMatrix());

        mazePanel1.setBounds(0, 50, 650, 620);

        mazePanel2 = new MazePanel(maze.getMatrix());

        mazePanel2.setBounds(650, 50, 650, 620);

        this.add(mazePanel1);
        this.add(buttonPanel);
        this.add(mazePanel2);
    }

    class CustomKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            // Up
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_W) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (i - 1 > 0 && matrix[i - 1][j] != 0) {
                    mazePanel1.step(i, j, 0);
                    currentPos[0] = i - 1;
                    System.out.println("Up");
                }

            }
            // down
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_S) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (matrix[i + 1][j] != 0) {
                    mazePanel1.step(i, j, 2);
                    currentPos[0] = i + 1;
                    System.out.println("Down");
                }

            }
            // left
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_A) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (matrix[i][j - 1] != 0) {
                    mazePanel1.step(i, j, 3);
                    currentPos[1] = j - 1;
                    System.out.println("Left");
                }

            }
            // right.
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_D) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (currentPos[0] == matrix.length - 2 && currentPos[1] == matrix.length - 1) {
                    JOptionPane.showMessageDialog(mazePanel1, "Congratulation! \n You have completed the maze");
                    solveButton.setEnabled(false);
                    changeMaze.setEnabled(true);
                    maze = new Maze(mazeSize);
                    maze.create();
                    matrix = maze.getMatrix();
                    mazePanel1.reload(maze.getMatrix());
                    mazePanel2.reload(maze.getMatrix());

                    status = 0;
                    currentPos[0] = 0;
                    currentPos[1] = 1;
                    currentPos2[0] = 0;
                    currentPos2[1] = 1;

                } else if (matrix[i][j + 1] != 0) {
                    mazePanel1.step(i, j, 1);
                    currentPos[1] = j + 1;
                    System.out.println("Right");
                }

            }

        }

        @Override
        public void keyReleased(KeyEvent arg0) {

        }

        @Override
        public void keyTyped(KeyEvent arg0) {

        }
    }

    class CustomKeyListener2 implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            // Up
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_I) {
                int i = currentPos2[0];
                int j = currentPos2[1];
                if (i - 1 > 0 && matrix[i - 1][j] != 0) {
                    mazePanel2.step(i, j, 0);
                    currentPos2[0] = i - 1;
                    System.out.println("Up");
                }

            }
            // down
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_K) {
                int i = currentPos2[0];
                int j = currentPos2[1];
                if (matrix[i + 1][j] != 0) {
                    mazePanel2.step(i, j, 2);
                    currentPos2[0] = i + 1;
                    System.out.println("Down");
                }

            }
            // left
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_J) {
                int i = currentPos2[0];
                int j = currentPos2[1];
                if (matrix[i][j - 1] != 0) {
                    mazePanel2.step(i, j, 3);
                    currentPos2[1] = j - 1;
                    System.out.println("Left");
                }

            }
            // right.
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_L) {
                int i = currentPos2[0];
                int j = currentPos2[1];
                if (currentPos2[0] == matrix.length - 2 && currentPos2[1] == matrix.length - 1) {
                    JOptionPane.showMessageDialog(mazePanel2, "Congratulation! \n You have completed the maze");
                    solveButton.setEnabled(false);
                    changeMaze.setEnabled(true);
                    maze = new Maze(mazeSize);
                    maze.create();
                    matrix = maze.getMatrix();
                    mazePanel2.reload(maze.getMatrix());
                    mazePanel1.reload(maze.getMatrix());
                    status = 0;
                    currentPos2[0] = 0;
                    currentPos2[1] = 1;
                    currentPos[0] = 0;
                    currentPos[1] = 1;

                } else if (matrix[i][j + 1] != 0) {
                    mazePanel2.step(i, j, 1);
                    currentPos2[1] = j + 1;
                    System.out.println("Right");
                }

            }

        }

        @Override
        public void keyReleased(KeyEvent arg0) {

        }

        @Override
        public void keyTyped(KeyEvent arg0) {

        }
    }
}