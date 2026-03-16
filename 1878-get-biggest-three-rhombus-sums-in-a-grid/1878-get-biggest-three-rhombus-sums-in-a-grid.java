class Solution {
    public int[] getBiggestThree(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        TreeSet<Integer> topThree = new TreeSet<>();

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                addSum(topThree, grid[r][c]);

                for (int k = 1; ; k++) {
                    if (r - k < 0 || r + k >= m || c - k < 0 || c + k >= n) break;

                    int currentSum = 0;
                    
                  
                    for (int i = 0; i < k; i++) {
                        currentSum += grid[r - k + i][c + i];   
                        currentSum += grid[r + i][c + k - i];   
                        currentSum += grid[r + k - i][c - i];   
                        currentSum += grid[r - i][c - k + i];     
                    }
                    
                    addSum(topThree, currentSum);
                }
            }
        }

        
        int size = topThree.size();
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            res[i] = topThree.pollLast();
        }
        return res;
    }

    private void addSum(TreeSet<Integer> set, int sum) {
        set.add(sum);
        if (set.size() > 3) {
            set.pollFirst(); 
        }
    }
}