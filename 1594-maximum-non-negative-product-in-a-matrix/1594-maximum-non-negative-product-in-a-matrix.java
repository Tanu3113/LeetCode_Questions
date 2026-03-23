class Solution {
    public int maxProductPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1_000_000_007;

       
        long[][] maxDP = new long[m][n];
        long[][] minDP = new long[m][n];

    
        maxDP[0][0] = minDP[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            maxDP[i][0] = minDP[i][0] = maxDP[i - 1][0] * grid[i][0];
        }

        for (int j = 1; j < n; j++) {
            maxDP[0][j] = minDP[0][j] = maxDP[0][j - 1] * grid[0][j];
        }

        
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                long val = grid[i][j];
                
                
                long a = maxDP[i - 1][j] * val;
                long b = maxDP[i][j - 1] * val;
                long c = minDP[i - 1][j] * val;
                long d = minDP[i][j - 1] * val;

                maxDP[i][j] = Math.max(Math.max(a, b), Math.max(c, d));
                minDP[i][j] = Math.min(Math.min(a, b), Math.min(c, d));
            }
        }

        long result = maxDP[m - 1][n - 1];
        
       
        return result < 0 ? -1 : (int) (result % MOD);
    }
}