class Solution {
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();

        int[][] dist = new int[m][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        Deque<int[]> deque = new ArrayDeque<>();

        dist[0][0] = grid.get(0).get(0);
        deque.offerFirst(new int[]{0, 0});

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        while (!deque.isEmpty()) {
            int[] cur = deque.pollFirst();
            int x = cur[0];
            int y = cur[1];

            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];

                if (nx < 0 || ny < 0 || nx >= m || ny >= n)
                    continue;

                int cost = grid.get(nx).get(ny);

                if (dist[x][y] + cost < dist[nx][ny]) {
                    dist[nx][ny] = dist[x][y] + cost;

                    if (cost == 0) {
                        deque.offerFirst(new int[]{nx, ny});
                    } else {
                        deque.offerLast(new int[]{nx, ny});
                    }
                }
            }
        }

        return dist[m - 1][n - 1] < health;
    }
}