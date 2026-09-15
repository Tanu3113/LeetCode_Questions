class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        boolean[][] isPali = new boolean[n][n];

        for (int length = 1; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (length == 1) {
                    isPali[i][j] = true;
                } else if (length == 2) {
                    isPali[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    isPali[i][j] = (s.charAt(i) == s.charAt(j) && isPali[i + 1][j - 1]);
                }
            }
        }

        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1];
            if (i >= k && isPali[i - k][i - 1]) {
                dp[i] = Math.max(dp[i], dp[i - k] + 1);
            }
            if (i >= k + 1 && isPali[i - k - 1][i - 1]) {
                dp[i] = Math.max(dp[i], dp[i - k - 1] + 1);
            }
        }

        return dp[n];
    }
}