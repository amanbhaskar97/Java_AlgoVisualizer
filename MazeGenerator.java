import java.util.*;

public class MazeGenerator {
    private int rows, cols;
    private int[][] maze;
    private Random random = new Random();

    private static final int[] DIR_X = {-1, 0, 1, 0};
    private static final int[] DIR_Y = {0, 1, 0, -1};

    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new int[rows][cols];
    }

    public void generateMaze() {
        for (int i = 0; i < rows; i++)
            Arrays.fill(maze[i], 1); // 1 = wall

        recursiveBacktracking(1, 1);
    }

    private void recursiveBacktracking(int x, int y) {
        maze[x][y] = 0;

        List<int[]> directions = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            directions.add(new int[]{DIR_X[i], DIR_Y[i]});
        Collections.shuffle(directions);

        for (int[] dir : directions) {
            int nx = x + dir[0] * 2;
            int ny = y + dir[1] * 2;
            if (isValid(nx, ny)) {
                maze[x + dir[0]][y + dir[1]] = 0;
                recursiveBacktracking(nx, ny);
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x > 0 && y > 0 && x < rows - 1 && y < cols - 1 && maze[x][y] == 1;
    }

    public int[][] getMaze() {
        return maze;
    }
}
