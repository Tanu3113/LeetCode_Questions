class Solution {
    public int[] pathsWithMaxScore(List<String> board) {
        int n = board.size();
        int mod = 1_000_000_007;
        
        int[][][] dp = new int[n][n][2];
        
        dp[n - 1][n - 1][1] = 1;
        
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}};
        
        for (int r = n - 1; r >= 0; r--) {
            for (int c = n - 1; c >= 0; c--) {
                if (board.get(r).charAt(c) == 'X' || (r == n - 1 && c == n - 1)) {
                    continue;
                }
                
                int maxScore = -1;
                int pathsCount = 0;
                
                for (int[] dir : directions) {
                    int nr = r + dir[0];
                    int nc = c + dir[1];
                    
                    if (nr < n && nc < n && dp[nr][nc][1] > 0) {
                        int score = dp[nr][nc][0];
                        int paths = dp[nr][nc][1];
                        
                        if (score > maxScore) {
                            maxScore = score;
                            pathsCount = paths;
                        } else if (score == maxScore) {
                            pathsCount = (pathsCount + paths) % mod;
                        }
                    }
                }
                
                if (maxScore != -1) {
                    char ch = board.get(r).charAt(c);
                    int currentVal = Character.isDigit(ch) ? (ch - '0') : 0;
                    dp[r][c][0] = maxScore + currentVal;
                    dp[r][c][1] = pathsCount;
                }
            }
        }
        
        return dp[0][0];
    }
}