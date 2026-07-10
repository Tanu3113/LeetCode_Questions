import java.util.*;

class Solution {
    public int[] cantTransform(int n, int[] nums, int maxDiff, int[][] queries) {
        return pathExistenceQueries(n, nums, maxDiff, queries);
    }

    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[] sortedUnique = Arrays.stream(nums).distinct().sorted().toArray();
        int m = sortedUnique.length;
        
        int[] valToIdx = new int[100001];
        Arrays.fill(valToIdx, -1);
        for (int i = 0; i < m; i++) {
            valToIdx[sortedUnique[i]] = i;
        }
        
        int[][] up = new int[m][18];
        int r = 0;
        for (int i = 0; i < m; i++) {
            while (r < m && sortedUnique[r] - sortedUnique[i] <= maxDiff) {
                r++;
            }
            up[i][0] = r - 1;
        }
        
        for (int j = 1; j < 18; j++) {
            for (int i = 0; i < m; i++) {
                up[i][j] = up[up[i][j - 1]][j - 1];
            }
        }
        
        int[] ans = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int u = queries[q][0];
            int v = queries[q][1];
            
            if (u == v) {
                ans[q] = 0;
                continue;
            }
            
            int numU = nums[u];
            int numV = nums[v];
            
            if (numU == numV) {
                ans[q] = 1;
                continue;
            }
            
            int idxU = valToIdx[numU];
            int idxV = valToIdx[numV];
            
            if (idxU > idxV) {
                int temp = idxU;
                idxU = idxV;
                idxV = temp;
            }
            
            int steps = 0;
            int curr = idxU;
            for (int j = 17; j >= 0; j--) {
                if (up[curr][j] < idxV) {
                    curr = up[curr][j];
                    steps += (1 << j);
                }
            }
            
            if (up[curr][0] >= idxV) {
                ans[q] = steps + 1;
            } else {
                ans[q] = -1;
            }
        }
        
        return ans;
    }
}