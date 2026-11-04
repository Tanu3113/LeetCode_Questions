class Solution {

    class Fenwick {
        int n;
        long[] count;
        long[] sum;

        Fenwick(int n) {
            this.n = n;
            count = new long[n + 1];
            sum = new long[n + 1];
        }

        void update(int i, int c, long s) {
            while (i <= n) {
                count[i] += c;
                sum[i] += s;
                i += i & -i;
            }
        }

        long queryCount(int i) {
            long result = 0;

            while (i > 0) {
                result += count[i];
                i -= i & -i;
            }

            return result;
        }

        long querySum(int i) {
            long result = 0;

            while (i > 0) {
                result += sum[i];
                i -= i & -i;
            }

            return result;
        }

        int kth(long k) {
            int index = 0;

            for (int bit = Integer.highestOneBit(n);
                 bit > 0; bit >>= 1) {

                int next = index + bit;

                if (next <= n && count[next] < k) {
                    index = next;
                    k -= count[next];
                }
            }

            return index + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int v : sorted) {
            if (m == 0 || sorted[m - 1] != v) {
                sorted[m++] = v;
            }
        }

        Fenwick bit = new Fenwick(m);

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[i] = Arrays.binarySearch(
                sorted, 0, m, nums[i]
            ) + 1;
        }

        long[] cost = new long[n - x + 1];
        long totalSum = 0;

        for (int i = 0; i < n; i++) {

            bit.update(rank[i], 1, nums[i]);
            totalSum += nums[i];

            if (i >= x) {
                bit.update(rank[i - x], -1, -nums[i - x]);
                totalSum -= nums[i - x];
            }

            if (i >= x - 1) {

                int medianIndex = bit.kth((x + 1) / 2);
                long median = sorted[medianIndex - 1];

                long leftCount = bit.queryCount(medianIndex);
                long leftSum = bit.querySum(medianIndex);

                long rightCount = x - leftCount;
                long rightSum = totalSum - leftSum;

                long leftCost =
                    median * leftCount - leftSum;

                long rightCost =
                    rightSum - median * rightCount;

                cost[i - x + 1] = leftCost + rightCost;
            }
        }

        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[k + 1][n + 1];

        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        for (int j = 1; j <= k; j++) {

            for (int i = j * x; i <= n; i++) {

                dp[j][i] = dp[j][i - 1];

                long previous = dp[j - 1][i - x];

                if (previous != INF) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        previous + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}