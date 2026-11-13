class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {

        int n = nums.length;
        int LOG = 17;

        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {

            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = (top == -1) ? -1 : stack[top];

            stack[++top] = i;
        }

        int[][] jump = new int[LOG][n];
        long[][] sum = new long[LOG][n];

        for (int i = 0; i < n; i++) {
            jump[0][i] = next[i];

            if (next[i] != -1) {
                sum[0][i] = (long) nums[i] * (next[i] - i);
            }
        }

        for (int p = 1; p < LOG; p++) {

            for (int i = 0; i < n; i++) {

                int mid = jump[p - 1][i];

                if (mid != -1) {
                    jump[p][i] = jump[p - 1][mid];

                    sum[p][i] = sum[p - 1][i]
                              + sum[p - 1][mid];
                } else {
                    jump[p][i] = -1;
                }
            }
        }

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        long ans = 0;

        for (int r = 0; r < n; r++) {

            int low = 0;
            int high = r;

            while (low < high) {

                int mid = low + (high - low) / 2;

                long maxSum = 0;
                int pos = mid;

                for (int p = LOG - 1; p >= 0; p--) {

                    if (jump[p][pos] != -1 &&
                        jump[p][pos] <= r) {

                        maxSum += sum[p][pos];
                        pos = jump[p][pos];
                    }
                }

                maxSum += (long) nums[pos] * (r - pos + 1);

                long originalSum = prefix[r + 1] - prefix[mid];

                long operations = maxSum - originalSum;

                if (operations <= k) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            ans += r - low + 1;
        }

        return ans;
    }
}
