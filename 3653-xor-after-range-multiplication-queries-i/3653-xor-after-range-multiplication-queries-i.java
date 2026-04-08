class Solution {
    public int xorAfterQueries(int[] nums, int[][] queries) {
        long MOD = 1_000_000_007L;

        for (int[] query : queries) {
            int l = query[0];
            int r = query[1];
            int k = query[2];
            int v = query[3];

            for (int idx = l; idx <= r; idx += k) {
                long newVal = (1L * nums[idx] * v) % MOD;
                nums[idx] = (int) newVal;
            }
        }

        int xorSum = 0;
        for (int num : nums) {
            xorSum ^= num;
        }

        return xorSum;
    }
}