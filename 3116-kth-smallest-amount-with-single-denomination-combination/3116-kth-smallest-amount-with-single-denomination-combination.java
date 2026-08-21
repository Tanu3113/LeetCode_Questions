class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long left = 1;
        long minCoin = Long.MAX_VALUE;
        for (int c : coins) {
            minCoin = Math.min(minCoin, c);
        }
        long right = minCoin * k;
        long ans = right;

        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (countMultiples(mid, coins) >= k) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private long countMultiples(long x, int[] coins) {
        int n = coins.length;
        long total = 0;
        int numSubsets = 1 << n;

        for (int mask = 1; mask < numSubsets; mask++) {
            long currentLcm = 1;
            int bitCount = 0;
            boolean overflow = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bitCount++;
                    currentLcm = lcm(currentLcm, coins[i]);
                    if (currentLcm > x) {
                        overflow = true;
                        break;
                    }
                }
            }

            if (!overflow) {
                long count = x / currentLcm;
                if (bitCount % 2 == 1) {
                    total += count;
                } else {
                    total -= count;
                }
            }
        }

        return total;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}