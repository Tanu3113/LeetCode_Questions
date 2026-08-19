class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> rowMask = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int r = seat[0];
            int c = seat[1];
  
            if (c >= 2 && c <= 9) {
                rowMask.put(r, rowMask.getOrDefault(r, 0) | (1 << c));
            }
        }

        int totalGroups = 0;
        int leftMask = 60;    
        int rightMask = 960;  
        int midMask = 240;    

        for (int mask : rowMask.values()) {
            boolean leftOpen = (mask & leftMask) == 0;
            boolean rightOpen = (mask & rightMask) == 0;
            boolean midOpen = (mask & midMask) == 0;

            if (leftOpen && rightOpen) {
                totalGroups += 2;
            } else if (leftOpen || rightOpen || midOpen) {
                totalGroups += 1;
            }
        }

        int emptyRows = n - rowMask.size();
        totalGroups += emptyRows * 2;

        return totalGroups;
    }
}