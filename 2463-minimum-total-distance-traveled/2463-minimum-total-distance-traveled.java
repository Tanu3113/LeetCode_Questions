import java.util.*;

class Solution {
    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        Collections.sort(robot);
        Arrays.sort(factory, (a, b) -> Integer.compare(a[0], b[0]));

        List<Integer> factoryList = new ArrayList<>();
        for (int[] f : factory) {
            for (int i = 0; i < f[1]; i++) {
                factoryList.add(f[0]);
            }
        }

        int n = robot.size();
        int m = factoryList.size();
        long[][] dp = new long[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            dp[i][0] = (long) 1e15;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                long assign = dp[i - 1][j - 1] + Math.abs(robot.get(i - 1) - factoryList.get(j - 1));
                long skip = dp[i][j - 1];
                dp[i][j] = Math.min(assign, skip);
            }
        }

        return dp[n][m];
    }
}