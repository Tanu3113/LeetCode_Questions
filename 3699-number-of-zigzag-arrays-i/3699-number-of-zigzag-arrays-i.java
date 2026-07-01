class Solution {

    static final int MOD = 1_000_000_007;

    public int zigZagArrays(int n, int l, int r) {

        int m = r - l + 1;

        long[][] up = new long[2][m];
        long[][] down = new long[2][m];

        // Initialize length = 2
        for (int last = 0; last < m; last++) {

            up[0][last] = last;

            down[0][last] = m - 1 - last;
        }

        if (n == 2) {
            long ans = 0;
            for (int i = 0; i < m; i++) {
                ans = (ans + up[0][i] + down[0][i]) % MOD;
            }
            return (int) ans;
        }

        for (int len = 3; len <= n; len++) {

            int cur = (len - 2) & 1;
            int prev = cur ^ 1;

            long[] prefix = new long[m];
            long[] suffix = new long[m];

            prefix[0] = up[prev][0];
            for (int i = 1; i < m; i++)
                prefix[i] = (prefix[i - 1] + up[prev][i]) % MOD;

            suffix[m - 1] = down[prev][m - 1];
            for (int i = m - 2; i >= 0; i--)
                suffix[i] = (suffix[i + 1] + down[prev][i]) % MOD;

            for (int j = 0; j < m; j++) {

                if (j == 0)
                    up[cur][j] = 0;
                else
                    up[cur][j] = suffix[0] - suffix[j];

                up[cur][j] %= MOD;
                if (up[cur][j] < 0)
                    up[cur][j] += MOD;

                if (j == m - 1)
                    down[cur][j] = 0;
                else
                    down[cur][j] = prefix[m - 1] - prefix[j];

                down[cur][j] %= MOD;
                if (down[cur][j] < 0)
                    down[cur][j] += MOD;
            }
        }

        int last = (n - 2) & 1;

        long ans = 0;

        for (int i = 0; i < m; i++) {
            ans = (ans + up[last][i] + down[last][i]) % MOD;
        }

        return (int) ans;
    }
}