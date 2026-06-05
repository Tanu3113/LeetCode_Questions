class Solution {

    String s;
    long[][][][][] cntMemo;
    long[][][][][] wavMemo;
    boolean[][][][][] vis;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long x) {

        if (x <= 0) return 0;

        s = String.valueOf(x);

        int n = s.length();

        cntMemo = new long[n + 1][11][11][2][2];
        wavMemo = new long[n + 1][11][11][2][2];
        vis = new boolean[n + 1][11][11][2][2];

        return dfs(0, 10, 10, 1, 0)[1];
    }

    private long[] dfs(int pos,
                       int prev1,
                       int prev2,
                       int tight,
                       int started) {

        if (pos == s.length()) {
            return new long[]{1, 0};
        }

        if (vis[pos][prev1][prev2][tight][started]) {
            return new long[]{
                    cntMemo[pos][prev1][prev2][tight][started],
                    wavMemo[pos][prev1][prev2][tight][started]
            };
        }

        vis[pos][prev1][prev2][tight][started] = true;

        int limit = tight == 1
                ? s.charAt(pos) - '0'
                : 9;

        long totalCnt = 0;
        long totalWav = 0;

        for (int d = 0; d <= limit; d++) {

            int ntight =
                    (tight == 1 && d == limit)
                            ? 1 : 0;

            if (started == 0 && d == 0) {

                long[] nxt =
                        dfs(pos + 1,
                                10,
                                10,
                                ntight,
                                0);

                totalCnt += nxt[0];
                totalWav += nxt[1];
            }
            else {

                int add = 0;

                if (started == 1 && prev2 != 10) {

                    if ((prev1 > prev2 && prev1 > d)
                            ||
                            (prev1 < prev2 && prev1 < d)) {

                        add = 1;
                    }
                }

                long[] nxt =
                        dfs(pos + 1,
                                d,
                                prev1,
                                ntight,
                                1);

                totalCnt += nxt[0];

                totalWav +=
                        nxt[1]
                                + nxt[0] * add;
            }
        }

        cntMemo[pos][prev1][prev2][tight][started]
                = totalCnt;

        wavMemo[pos][prev1][prev2][tight][started]
                = totalWav;

        return new long[]{totalCnt, totalWav};
    }
}