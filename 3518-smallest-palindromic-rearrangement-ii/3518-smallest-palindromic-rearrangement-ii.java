class Solution {
    private static final long INF = 1_000_000_001L;

    public String smallestPalindrome(String s, long k) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        int halfLen = 0;
        char midChar = 0;
        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
            halfLen += halfCount[i];
            if (count[i] % 2 != 0) {
                midChar = (char) ('a' + i);
            }
        }

        long totalPerms = countArrangements(halfCount);
        if (k > totalPerms) {
            return "";
        }

        StringBuilder leftHalf = new StringBuilder();
        for (int pos = 0; pos < halfLen; pos++) {
            for (int i = 0; i < 26; i++) {
                if (halfCount[i] == 0) continue;

                halfCount[i]--;
                long subPerms = countArrangements(halfCount);

                if (k <= subPerms) {
                    leftHalf.append((char) ('a' + i));
                    break;
                } else {
                    k -= subPerms;
                    halfCount[i]++;
                }
            }
        }

        String left = leftHalf.toString();
        String right = new StringBuilder(left).reverse().toString();
        return midChar == 0 ? left + right : left + midChar + right;
    }

    private long countArrangements(int[] count) {
        int total = 0;
        for (int c : count) total += c;

        long res = 1;
        for (int c : count) {
            if (c == 0) continue;
            res = multiplySafely(res, nCk(total, c));
            total -= c;
        }
        return res;
    }

    private long nCk(int n, int k) {
        k = Math.min(k, n - k);
        long res = 1;
        for (int i = 1; i <= k; i++) {
            long numerator = n - i + 1;
            long gcd = gcd(numerator, i);
            numerator /= gcd;
            long denominator = i / gcd;

            if (res > (INF - 1) / numerator) {
                return INF;
            }
            res = (res / denominator) * numerator;
            if (res >= INF) return INF;
        }
        return res;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long multiplySafely(long a, long b) {
        if (a == 0 || b == 0) return 0;
        if (INF / a < b) return INF;
        return Math.min(INF, a * b);
    }
}