import java.util.Arrays;

class Solution {
    public int maxBuilding(int n, int[][] restrictions) {
        int m = restrictions.length;
        
        int[][] r = new int[m + 2][2];
        r[0] = new int[]{1, 0};
        r[1] = new int[]{n, n - 1};
        
        System.arraycopy(restrictions, 0, r, 2, m);
        
        Arrays.sort(r, (a, b) -> Integer.compare(a[0], b[0]));
        
        int len = r.length;
        
     
        for (int i = 1; i < len; i++) {
            int dist = r[i][0] - r[i - 1][0];
            r[i][1] = Math.min(r[i][1], r[i - 1][1] + dist);
        }
        
        for (int i = len - 2; i >= 0; i--) {
            int dist = r[i + 1][0] - r[i][0];
            r[i][1] = Math.min(r[i][1], r[i + 1][1] + dist);
        }
        
        int maxOverallHeight = 0;
        for (int i = 1; i < len; i++) {
            int id1 = r[i - 1][0];
            int h1 = r[i - 1][1];
            int id2 = r[i][0];
            int h2 = r[i][1];
         
            int peak = (h1 + h2 + (id2 - id1)) / 2;
            maxOverallHeight = Math.max(maxOverallHeight, peak);
        }
        
        return maxOverallHeight;
    }
}