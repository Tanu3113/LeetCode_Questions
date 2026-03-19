class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
       
        int[] prevX = new int[n];
        int[] prevY = new int[n];
        int result = 0;
        
        for (int i = 0; i < m; i++) {
            int rowX = 0; 
            int rowY = 0; 
            
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'X') rowX++;
                else if (grid[i][j] == 'Y') rowY++;
                
                
               
                int totalX = rowX + prevX[j];
                int totalY = rowY + prevY[j];
                
                if (totalX == totalY && totalX > 0) {
                    result++;
                }
                
                
                prevX[j] = totalX;
                prevY[j] = totalY;
            }
        }
        
        return result;
    }
}