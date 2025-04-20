import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of rows (odd number, >= 5): ");
        int rows = scanner.nextInt();
        System.out.print("Enter number of columns (odd number, >= 5): ");
        int cols = scanner.nextInt();

        if (rows % 2 == 0) rows++;
        if (cols % 2 == 0) cols++;

        MazeGenerator generator = new MazeGenerator(rows, cols);
        generator.generateMaze();
        int[][] maze = generator.getMaze();

        MazeSolver solver = new MazeSolver(maze);
        solver.getVisualizer().setStartEnd(new int[]{1, 1}, new int[]{rows - 2, cols - 2});

        JFrame frame = new JFrame("Maze Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(cols * 30 + 20, rows * 30 + 40);
        frame.add(solver.getVisualizer());
        frame.setVisible(true);

        System.out.println("Choose algorithm to solve:");
        System.out.println("1. BFS\n2. DFS\n3. A*\n4. Dijkstra");
        int choice = scanner.nextInt();

        boolean solved = false;
        switch (choice) {
            case 1 -> solved = solver.bfs(1, 1, rows - 2, cols - 2);
            case 2 -> solved = solver.dfs(1, 1, rows - 2, cols - 2);
            case 3 -> solved = solver.aStar(1, 1, rows - 2, cols - 2);
            case 4 -> solved = solver.dijkstra(1, 1, rows - 2, cols - 2);
            default -> System.out.println("Invalid choice.");
        }

        if (solved) {
            System.out.println("Maze solved!");
        } else {
            System.out.println("No path found.");
        }
    }
}
