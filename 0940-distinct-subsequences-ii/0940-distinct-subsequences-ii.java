class Solution {
    public int distinctSubseqII(String s) {
        int MOD = 1_000_000_007;
        int[] last = new int[26];
        long total = 0;

        for (char c : s.toCharArray()) {
            int index = c - 'a';
            long newAdded = (total + 1 - last[index] + MOD) % MOD;
            total = (total + newAdded) % MOD;
            last[index] = (last[index] + (int) newAdded) % MOD;
        }

        return (int) total;
    }
}