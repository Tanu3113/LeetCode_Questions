class Solution {
    private int[][] maxST;
    private int[][] minST;
    private int[] logTable;

    public long maxTotalValue(int[] nums, int k) {
        int n = nums.length;
        
        
        logTable = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            logTable[i] = logTable[i >> 1] + 1;
        }

        int maxLog = logTable[n] + 1;
        maxST = new int[maxLog][n];
        minST = new int[maxLog][n];

        for (int i = 0; i < n; i++) {
            maxST[0][i] = nums[i];
            minST[0][i] = nums[i];
        }

        for (int j = 1; j < maxLog; j++) {
            for (int i = 0; i + (1 << j) <= n; i++) {
                maxST[j][i] = Math.max(maxST[j - 1][i], maxST[j - 1][i + (1 << (j - 1))]);
                minST[j][i] = Math.min(minST[j - 1][i], minST[j - 1][i + (1 << (j - 1))]);
            }
        }

    
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(b[0], a[0]));

        for (int l = 0; l < n; l++) {
            long val = queryValue(l, n - 1);
            pq.offer(new long[]{val, l, n - 1});
        }

        long totalValue = 0;

       
        for (int i = 0; i < k; i++) {
            if (pq.isEmpty()) break;

            long[] curr = pq.poll();
            long val = curr[0];
            int l = (int) curr[1];
            int r = (int) curr[2];

            totalValue += val;

            if (r > l) {
                long nextVal = queryValue(l, r - 1);
                pq.offer(new long[]{nextVal, l, r - 1});
            }
        }

        return totalValue;
    }
    private long queryValue(int l, int r) {
        int len = r - l + 1;
        int k = logTable[len];
        
        int maxVal = Math.max(maxST[k][l], maxST[k][r - (1 << k) + 1]);
        int minVal = Math.min(minST[k][l], minST[k][r - (1 << k) + 1]);
        
        return (long) maxVal - minVal;
    }
}
