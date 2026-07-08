class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        int MOD = 1_000_000_007;
        int m = s.length();
        
        // Precompute powers of 10 modulo MOD
        long[] pow10 = new long[m + 1];
        pow10[0] = 1;
        for (int i = 1; i <= m; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }
        
        // Prefix arrays:
        // cnt[i]      -> count of non-zero digits in s[0...i-1]
        // pref[i]     -> rolling value of non-zero digits in s[0...i-1] % MOD
        // sumPref[i]  -> prefix sum of all digit values in s[0...i-1]
        int[] cnt = new int[m + 1];
        long[] pref = new long[m + 1];
        long[] sumPref = new long[m + 1];
        
        for (int i = 0; i < m; i++) {
            int d = s.charAt(i) - '0';
            sumPref[i + 1] = sumPref[i] + d;
            
            if (d != 0) {
                cnt[i + 1] = cnt[i] + 1;
                pref[i + 1] = (pref[i] * 10 + d) % MOD;
            } else {
                cnt[i + 1] = cnt[i];
                pref[i + 1] = pref[i];
            }
        }
        
        int[] answer = new int[queries.length];
        
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            
            // Number of non-zero digits in s[l...r]
            int k = cnt[r + 1] - cnt[l];
            
            if (k == 0) {
                answer[i] = 0;
                continue;
            }
            
            // Extract x % MOD using the prefix rolling hash technique
            long x = (pref[r + 1] - (pref[l] * pow10[k]) % MOD + MOD) % MOD;
            
            // Get the sum of the digits in s[l...r]
            long digitSum = sumPref[r + 1] - sumPref[l];
            
            // Calculate final answer for the query
            answer[i] = (int) ((x * digitSum) % MOD);
        }
        
        return answer;
    }
}