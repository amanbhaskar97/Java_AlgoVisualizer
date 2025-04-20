import java.util.*;

public class MazeSolver {
    private int[][] maze;
    private boolean[][] visited;
    private int rows, cols;
    private Stack<int[]> path = new Stack<>();
    private MazeSolverVisualizer visualizer;

    private static final int[] DIR_X = {-1, 0, 1, 0};
    private static final int[] DIR_Y = {0, 1, 0, -1};

    public MazeSolver(int[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.visualizer = new MazeSolverVisualizer(maze);
    }

    public MazeSolverVisualizer getVisualizer() {
        return visualizer;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < rows && y < cols && maze[x][y] == 0;
    }

    private void addStep(int x, int y) {
        path.push(new int[]{x, y});
        visualizer.updatePath(path);
        try {
            Thread.sleep(1000);  // 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean bfs(int sx, int sy, int ex, int ey) {
        visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        queue.offer(new int[]{sx, sy});
        visited[sx][sy] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];
            if (x == ex && y == ey) {
                reconstructPath(parent, sx, sy, ex, ey);
                return true;
            }
            for (int i = 0; i < 4; i++) {
                int nx = x + DIR_X[i], ny = y + DIR_Y[i];
                if (isValid(nx, ny) && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.offer(new int[]{nx, ny});
                    parent.put(nx + "," + ny, x + "," + y);
                }
            }
        }
        return false;
    }

    public boolean dfs(int sx, int sy, int ex, int ey) {
        visited = new boolean[rows][cols];
        return dfsUtil(sx, sy, ex, ey);
    }

    private boolean dfsUtil(int x, int y, int ex, int ey) {
        if (!isValid(x, y) || visited[x][y]) return false;
        visited[x][y] = true;
        addStep(x, y);
        if (x == ex && y == ey) return true;

        for (int i = 0; i < 4; i++) {
            if (dfsUtil(x + DIR_X[i], y + DIR_Y[i], ex, ey)) return true;
        }
        path.pop();
        return false;
    }

    public boolean aStar(int sx, int sy, int ex, int ey) {
        visited = new boolean[rows][cols];
        int[][] gCost = new int[rows][cols];
        int[][] fCost = new int[rows][cols];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.offer(new int[]{sx, sy, 0});
        gCost[sx][sy] = 0;
        fCost[sx][sy] = heuristic(sx, sy, ex, ey);

        Map<String, String> parent = new HashMap<>();

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int x = current[0], y = current[1];

            if (x == ex && y == ey) {
                reconstructPath(parent, sx, sy, ex, ey);
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + DIR_X[i], ny = y + DIR_Y[i];
                if (isValid(nx, ny)) {
                    int newGCost = gCost[x][y] + 1;
                    if (gCost[nx][ny] == 0 || newGCost < gCost[nx][ny]) {
                        gCost[nx][ny] = newGCost;
                        fCost[nx][ny] = newGCost + heuristic(nx, ny, ex, ey);
                        pq.offer(new int[]{nx, ny, fCost[nx][ny]});
                        parent.put(nx + "," + ny, x + "," + y);
                    }
                }
            }
        }
        return false;
    }

    private int heuristic(int x, int y, int ex, int ey) {
        return Math.abs(x - ex) + Math.abs(y - ey);
    }

    private void reconstructPath(Map<String, String> parent, int sx, int sy, int ex, int ey) {
        Stack<int[]> temp = new Stack<>();
        String key = ex + "," + ey;
        while (!key.equals(sx + "," + sy)) {
            String[] parts = key.split(",");
            temp.push(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
            key = parent.get(key);
        }
        temp.push(new int[]{sx, sy});
        while (!temp.isEmpty()) {
            int[] step = temp.pop();
            addStep(step[0], step[1]);
        }
    }

    public boolean dijkstra(int sx, int sy, int ex, int ey) {
        visited = new boolean[rows][cols];
        int[][] dist = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[sx][sy] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.offer(new int[]{sx, sy, 0});
        Map<String, String> parent = new HashMap<>();

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int x = current[0], y = current[1], d = current[2];

            if (x == ex && y == ey) {
                reconstructPath(parent, sx, sy, ex, ey);
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + DIR_X[i], ny = y + DIR_Y[i];
                if (isValid(nx, ny) && dist[nx][ny] > d + 1) {
                    dist[nx][ny] = d + 1;
                    pq.offer(new int[]{nx, ny, dist[nx][ny]});
                    parent.put(nx + "," + ny, x + "," + y);
                }
            }
        }
        return false;
    }
}
