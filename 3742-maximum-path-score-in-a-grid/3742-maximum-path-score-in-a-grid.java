class Solution {
    public int maxPathScore(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
          
        int[][][] dp = new int[m][n][k + 1];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        dp[0][0][0] = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int c = 0; c <= k; c++) {
                    if (dp[i][j][c] == -1) continue;

                    if (j + 1 < n) {
                        update(grid, dp, i, j + 1, c, dp[i][j][c], k);
                    }

                    if (i + 1 < m) {
                        update(grid, dp, i + 1, j, c, dp[i][j][c], k);
                    }
                }
            }
        }

        int maxScore = -1;
        for (int c = 0; c <= k; c++) {
            maxScore = Math.max(maxScore, dp[m - 1][n - 1][c]);
        }

        return maxScore;
    }

    private void update(int[][] grid, int[][][] dp, int r, int c, int currentCost, int currentScore, int kLimit) {
        int cellValue = grid[r][c];
        int nextCost = currentCost + (cellValue == 0 ? 0 : 1);
        int nextScore = currentScore + (cellValue == 2 ? 2 : (cellValue == 1 ? 1 : 0));

        if (nextCost <= kLimit) {
            dp[r][c][nextCost] = Math.max(dp[r][c][nextCost], nextScore);
        }
    }
}