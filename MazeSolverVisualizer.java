import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class MazeSolverVisualizer extends JPanel {
    private int[][] maze;
    private Stack<int[]> path = new Stack<>();
    private final int CELL_SIZE = 30;
    private int[] start, end;

    public MazeSolverVisualizer(int[][] maze) {
        this.maze = maze;
    }

    public void updatePath(Stack<int[]> path) {
        this.path = (Stack<int[]>) path.clone();
        repaint();
    }

    public void setStartEnd(int[] start, int[] end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze(g);
        drawPath(g);
    }

    private void drawMaze(Graphics g) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        if (start != null) {
            g.setColor(Color.GREEN);
            g.fillRect(start[1] * CELL_SIZE, start[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        if (end != null) {
            g.setColor(Color.BLUE);
            g.fillRect(end[1] * CELL_SIZE, end[0] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private void drawPath(Graphics g) {
        g.setColor(Color.RED);
        for (int[] p : path) {
            g.fillOval(p[1] * CELL_SIZE + CELL_SIZE / 4, p[0] * CELL_SIZE + CELL_SIZE / 4,
                    CELL_SIZE / 2, CELL_SIZE / 2);
        }
    }
}
