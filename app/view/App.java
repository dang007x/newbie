package app.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
    private Maze maze = new Maze(mazeSize);

    public App() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 700, 50);
        buttonPanel.setLayout(new GridLayout(0, 5));

        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ArrayList<Node> path = maze.findPath();
                for (int i = 0; i < path.size(); i++) {
                    System.out.println(path.get(i));
                }
                mazePanel.move(path);
                


                System.out.println("Clicked Play");

            }

        });

        solveButton = new JButton("Solve");
        solveButton.setEnabled(false);
        changeMaze = new JButton("ChangeMaze");
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                maze = new Maze(mazeSize);
                maze.create();
                mazePanel.reload(maze.getMatrix());
                System.out.println("Clicked New Game");

            }

        });
        tutorial = new JButton("Tutorial");

        buttonPanel.add(playButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(changeMaze);
        buttonPanel.add(newGameButton);
        buttonPanel.add(tutorial);

        maze.create();
        mazePanel = new MazePanel(maze.getMatrix());

        mazePanel.setBounds(0, 50, 700, 620);

        this.add(mazePanel);
        this.add(buttonPanel);
    }

}