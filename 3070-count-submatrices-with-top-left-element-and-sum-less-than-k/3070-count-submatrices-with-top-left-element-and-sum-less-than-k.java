class Solution {
    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;
        
        // Use a 2D long array for sums to prevent potential overflow 
        // though k is 10^9 and grid values are up to 1000, 
        // 1000 * 1000 * 1000 = 10^9 fits in an int, but long is safer.
        long[][] pref = new long[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                long currentSum = grid[i][j];
                
                if (i > 0) currentSum += pref[i - 1][j];
                if (j > 0) currentSum += pref[i][j - 1];
                if (i > 0 && j > 0) currentSum -= pref[i - 1][j - 1];
                
                pref[i][j] = currentSum;
                
                if (pref[i][j] <= k) {
                    count++;
                } else {
                    // Since grid elements are >= 0, once a row sum exceeds k, 
                    // further elements in this row will also exceed k.
                    break; 
                }
            }
        }
        
        return count;
    }
}