class Solution {
    public int[][] reverseSubmatrix(int[][] grid, int x, int y, int k) {
        // Iterate through the first half of the rows in the k x k square
        for (int i = 0; i < k / 2; i++) {
            // Find the two rows to swap
            int topRow = x + i;
            int bottomRow = x + k - 1 - i;
            
            // Swap elements in each column within the square range [y, y + k - 1]
            for (int j = y; j < y + k; j++) {
                int temp = grid[topRow][j];
                grid[topRow][j] = grid[bottomRow][j];
                grid[bottomRow][j] = temp;
            }
        }
        
        return grid;
    }
}