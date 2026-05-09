class Solution {
    public int[][] rotateGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int numLayers = Math.min(m, n) / 2;

        for (int layer = 0; layer < numLayers; layer++) {
            List<Integer> elements = new ArrayList<>();
            
            for (int j = layer; j < n - 1 - layer; j++) elements.add(grid[layer][j]);
            for (int i = layer; i < m - 1 - layer; i++) elements.add(grid[i][n - 1 - layer]);
            for (int j = n - 1 - layer; j > layer; j--) elements.add(grid[m - 1 - layer][j]);
            for (int i = m - 1 - layer; i > layer; i--) elements.add(grid[i][layer]);

            int size = elements.size();
            int rotations = k % size;

            int index = rotations;
            
            for (int j = layer; j < n - 1 - layer; j++) grid[layer][j] = elements.get(index++ % size);
            for (int i = layer; i < m - 1 - layer; i++) grid[i][n - 1 - layer] = elements.get(index++ % size);
            for (int j = n - 1 - layer; j > layer; j--) grid[m - 1 - layer][j] = elements.get(index++ % size);
            for (int i = m - 1 - layer; i > layer; i--) grid[i][layer] = elements.get(index++ % size);
        }
        return grid;
    }
}