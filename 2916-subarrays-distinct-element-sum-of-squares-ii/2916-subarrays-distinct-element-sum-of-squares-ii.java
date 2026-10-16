class Solution {
    private static final long MOD = 1_000_000_007L;

    private long[] sum;
    private long[] sumSq;
    private long[] lazy;
    private int n;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        sumSq = new long[4 * n];
        lazy = new long[4 * n];

        int[] lastSeen = new int[100001];
        java.util.Arrays.fill(lastSeen, -1);

        long ans = 0;

        for (int r = 0; r < n; r++) {
            int num = nums[r];

            int prev = lastSeen[num];

           
            update(1, 0, n - 1, prev + 1, r);

            ans = (ans + sumSq[1]) % MOD;

            lastSeen[num] = r;
        }

        return (int) ans;
    }

    private void update(int node, int start, int end,
                       int left, int right) {

        if (start > right || end < left) {
            return;
        }

        if (start >= left && end <= right) {
            apply(node, start, end);
            return;
        }

        pushDown(node, start, end);

        int mid = start + (end - start) / 2;

        update(node * 2, start, mid, left, right);
        update(node * 2 + 1, mid + 1, end, left, right);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        sumSq[node] = (sumSq[node * 2] + sumSq[node * 2 + 1]) % MOD;
    }

    private void apply(int node, int start, int end) {
        long len = end - start + 1L;

        sumSq[node] = (sumSq[node] + 2L * sum[node] + len) % MOD;

        sum[node] = (sum[node] + len) % MOD;

        lazy[node]++;
    }

    private void pushDown(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = start + (end - start) / 2;

        long pending = lazy[node];

        applyMultiple(node * 2, start, mid, pending);
        applyMultiple(node * 2 + 1, mid + 1, end, pending);

        lazy[node] = 0;
    }

    private void applyMultiple(int node, int start, int end, long delta) {
        long len = end - start + 1L;

        sumSq[node] = (sumSq[node]
                + 2L * delta % MOD * sum[node] % MOD
                + delta % MOD * delta % MOD * len % MOD) % MOD;

        sum[node] = (sum[node] + delta % MOD * len) % MOD;

        lazy[node] += delta;
    }
}