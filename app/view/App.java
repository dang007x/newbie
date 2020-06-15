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

    private MazePanel mazePanel;

    private int mazeSize = 100;
    private int status = 0;

    private Integer[] currentPos = { 0, 1 };
    
    private Maze maze = new Maze(mazeSize);
    private int [][] matrix = maze.getMatrix();
    private Color buttonColor = new Color(19, 15, 64);
    private Color fore = new Color(255, 255, 255);

    private String message = "1. Click \"Play\" to start a game. \n" + 
                             "2. Using W, A, S, D to move Up, Left, Down, Right. \n" + 
                             "3. Click \"Solve\" to find the path.\n" + 
                             "4. Click \" Change Maze\" to create a new maze.\n" +
                             "5. Click \"New Game\" to create a new game.\n";

    public App(int size){
        if(size != 0){
            this.mazeSize = size;
            this.maze = new Maze(mazeSize);
            this.currentPos = new Integer[2];
            currentPos[0] = 0;
            currentPos[1] = 1;
        }
       
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(810, 830);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 800, 50);
        buttonPanel.setLayout(new GridLayout(0, 5));

        playButton = new JButton("Play");

        playButton.setForeground(fore);
        playButton.setBackground(buttonColor);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                changeMaze.setEnabled(false);
                solveButton.setEnabled(true);
                status = 1;
                System.out.println("Clicked Play");

            }

        });
        playButton.addKeyListener(new CustomKeyListener());
        solveButton = new JButton("Solve");
        solveButton.setEnabled(false);
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Node> path = maze.findPath();
                // for (int i = 0; i < path.size(); i++) {
                //     System.out.println(path.get(i));
                // }
                mazePanel.move(path);
                mazePanel.reload(maze.getMatrix());
                System.out.println("Clicked Play");

            }

        });
        changeMaze = new JButton("ChangeMaze");
        // changeMaze.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent arg0) {
        //         maze = new Maze(mazeSize);
        //         maze.create();
        //         mazePanel.reload(maze.getMatrix());
        //         currentPos[0] = 0;
        //         currentPos[1] = 1;
        //         status = 0;
        //         solveButton.setEnabled(false);
        //         System.out.println("Clicked New Game");
        //     }

        // });
        newGameButton = new JButton("New Game");
        
        solveButton.setForeground(fore);
        solveButton.setBackground(buttonColor);

        changeMaze.setForeground(fore);
        changeMaze.setBackground(buttonColor);
        changeMaze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String size = JOptionPane.showInputDialog(null, "Input size");
                
                if(!size.matches("\\d+")){
                    JOptionPane.showMessageDialog(null, "Invalid size");
                    
                }
                
                dispose();
                App app = new App(Integer.valueOf(size));
                app.setVisible(true);
                System.out.println("Clicked New Game");
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
                mazePanel.reload(maze.getMatrix());
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
        mazePanel = new MazePanel(maze.getMatrix());

        mazePanel.setBounds(0, 60, 790, 750);

        this.add(mazePanel);
        this.add(buttonPanel);
    }
    class CustomKeyListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            // Up
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_W) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (i - 1 > 0 && matrix[i - 1][j] != 0) {
                    mazePanel.step(i, j, 0);
                    currentPos[0] = i - 1;
                    System.out.println("Up");
                }

            }
            // down
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_S) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (matrix[i + 1][j] != 0) {
                    mazePanel.step(i, j, 2);
                    currentPos[0] = i + 1;
                    System.out.println("Down");
                }

            }
            // left
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_A) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (matrix[i][j - 1] != 0) {
                    mazePanel.step(i, j, 3);
                    currentPos[1] = j - 1;
                    System.out.println("Left");
                }

            }
            // right.
            if (status == 1 && e.getKeyCode() == KeyEvent.VK_D) {
                int i = currentPos[0];
                int j = currentPos[1];
                if (currentPos[0] == matrix.length - 2 && currentPos[1] == matrix.length - 1) {
                    JOptionPane.showMessageDialog(mazePanel, "Congratulation! \n You have completed the maze");
                    solveButton.setEnabled(false);
                    changeMaze.setEnabled(true);
                    maze = new Maze(mazeSize);
                    maze.create();
                    matrix = maze.getMatrix();
                    mazePanel.reload(maze.getMatrix());
                    status = 0;
                    currentPos[0] = 0;
                    currentPos[1] = 1;
                    
                } else if (matrix[i][j + 1] != 0) {
                    mazePanel.step(i, j, 1);
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

}