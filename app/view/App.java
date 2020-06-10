package app.view;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.model.Maze;

public class App extends JFrame{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JButton playButton;
    private JButton solveButton;
    private JButton changeMaze;
    private JButton newGameButton;
    private JButton tutorial;
    private JPanel mazePanel;
    private int mazeSize = 5;
    private Maze maze = new Maze(mazeSize);
    private MazePanel mainPanel;

    public App(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 700, 50);
        buttonPanel.setLayout(new GridLayout(0, 5));

        playButton = new JButton("Play");
        
        solveButton = new JButton("Solve");
        solveButton.setEnabled(false);
        changeMaze = new JButton("ChangeMaze");
        newGameButton = new JButton("New Game");
        tutorial = new JButton("Tutorial");

        buttonPanel.add(playButton);
        buttonPanel.add(solveButton);
        buttonPanel.add(changeMaze);
        buttonPanel.add(newGameButton);
        buttonPanel.add(tutorial);

        mazePanel = new JPanel();
        mazePanel.setLayout(new BorderLayout());
        mazePanel.setBounds(0, 50, 700, 620);

        maze.create();
        mainPanel = new MazePanel(maze.getMatrix());
       
        mazePanel.add(mainPanel, BorderLayout.CENTER);
        
        this.add(mazePanel);
        this.add(buttonPanel);
    }
    
}