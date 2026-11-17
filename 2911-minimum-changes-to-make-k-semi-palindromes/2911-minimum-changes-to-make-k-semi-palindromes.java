class Solution {
    public int minimumChanges(String s, int k) {

        int n = s.length();

        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int len = j - i + 1;
                int minChanges = Integer.MAX_VALUE;

                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                  
                    for (int offset = 0; offset < d; offset++) {

                        int left = i + offset;
                        int right = i + offset
                                + ((len - 1 - offset) / d) * d;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    minChanges = Math.min(minChanges, changes);
                }

                cost[i][j] = minChanges;
            }
        }

        int INF = 1_000_000;
        int[][] dp = new int[k + 1][n + 1];

        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = INF;
            }
        }

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {

            for (int end = 2 * parts; end <= n; end++) {

                for (int start = 2 * (parts - 1);
                     start <= end - 2; start++) {

                    if (dp[parts - 1][start] == INF) {
                        continue;
                    }

                    dp[parts][end] = Math.min(
                        dp[parts][end],
                        dp[parts - 1][start] + cost[start][end - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
