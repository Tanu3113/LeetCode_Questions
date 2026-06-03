class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {

        long ans = Long.MAX_VALUE;

        int n = landStartTime.length;
        long[] landFinish = new long[n];
        for (int i = 0; i < n; i++) {
            landFinish[i] = (long) landStartTime[i] + landDuration[i];
        }

        int m = waterStartTime.length;
        long[] waterFinish = new long[m];
        for (int i = 0; i < m; i++) {
            waterFinish[i] = (long) waterStartTime[i] + waterDuration[i];
        }

        ans = Math.min(ans, solve(landFinish, waterStartTime, waterDuration));
        ans = Math.min(ans, solve(waterFinish, landStartTime, landDuration));

        return (int) ans;
    }

    private long solve(long[] finishTimes, int[] start, int[] duration) {
        int n = start.length;

        int[][] rides = new int[n][2];
        for (int i = 0; i < n; i++) {
            rides[i][0] = start[i];
            rides[i][1] = duration[i];
        }

        Arrays.sort(rides, Comparator.comparingInt(a -> a[0]));

        int[] starts = new int[n];
        long[] prefixMinDur = new long[n];
        long[] suffixMinOpenFinish = new long[n];

        for (int i = 0; i < n; i++) {
            starts[i] = rides[i][0];
        }

        prefixMinDur[0] = rides[0][1];
        for (int i = 1; i < n; i++) {
            prefixMinDur[i] = Math.min(prefixMinDur[i - 1], rides[i][1]);
        }

        suffixMinOpenFinish[n - 1] =
                (long) rides[n - 1][0] + rides[n - 1][1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMinOpenFinish[i] = Math.min(
                    suffixMinOpenFinish[i + 1],
                    (long) rides[i][0] + rides[i][1]
            );
        }

        long ans = Long.MAX_VALUE;

        for (long t : finishTimes) {

            int idx = upperBound(starts, t);

            long best = Long.MAX_VALUE;

            if (idx > 0) {
                best = Math.min(best, t + prefixMinDur[idx - 1]);
            }

            if (idx < n) {
                best = Math.min(best, suffixMinOpenFinish[idx]);
            }

            ans = Math.min(ans, best);
        }

        return ans;
    }

    private int upperBound(int[] arr, long target) {
        int l = 0, r = arr.length;

        while (l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }
}