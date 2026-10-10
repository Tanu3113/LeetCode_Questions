class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        long k = (long) k1 + k2;
        int[] diff = new int[n];
        int max = 0;
        long total = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            max = Math.max(max, diff[i]);
            total += diff[i];
        }

        if (total <= k) {
            return 0;
        }

        int low = 0, high = max;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long ops = 0;

            for (int d : diff) {
                ops += Math.max(0, d - mid);
            }

            if (ops <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int level = low;
        long ops = 0;
        long count = 0;
        long answer = 0;

        for (int d : diff) {
            if (d > level) {
                ops += d - level;
            }

            if (d >= level) {
                count++;
            }

            int remaining = Math.min(d, level);
            answer += (long) remaining * remaining;
        }

        long extra = k - ops;
        answer -= extra * (2L * level - 1);

        return answer;
    }
}