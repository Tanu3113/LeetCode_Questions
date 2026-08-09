class Solution {
    private int[][] memo;
    private int[] suffixSum;

    public int stoneGameII(int[] piles) {
        int n = piles.length;
        memo = new int[n][n + 1];
        suffixSum = new int[n];

        
        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = suffixSum[i + 1] + piles[i];
        }

        return dp(0, 1, piles);
    }

    private int dp(int i, int M, int[] piles) {
        int n = piles.length;

        if (i + 2 * M >= n) {
            return suffixSum[i];
        }

        if (memo[i][M] > 0) {
            return memo[i][M];
        }

        int maxStones = 0;

        for (int X = 1; X <= 2 * M; X++) {
           
            int currentStones = suffixSum[i] - dp(i + X, Math.max(M, X), piles);
            maxStones = Math.max(maxStones, currentStones);
        }

        memo[i][M] = maxStones;
        return maxStones;
    }
}