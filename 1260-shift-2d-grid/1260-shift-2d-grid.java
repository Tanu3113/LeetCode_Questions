class Solution {
    public java.util.List<java.util.List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;
        k %= total;

        int[][] res = new int[m][n];

        for (int i = 0; i < total; i++) {
            int ni = (i + k) % total;
            res[ni / n][ni % n] = grid[i / n][i % n];
        }

        java.util.List<java.util.List<Integer>> ans = new java.util.ArrayList<>();

        for (int i = 0; i < m; i++) {
            java.util.List<Integer> row = new java.util.ArrayList<>();
            for (int j = 0; j < n; j++) {
                row.add(res[i][j]);
            }
            ans.add(row);
        }

        return ans;
    }
}