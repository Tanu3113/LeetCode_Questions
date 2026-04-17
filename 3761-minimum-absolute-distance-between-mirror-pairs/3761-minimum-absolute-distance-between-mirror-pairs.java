import java.util.*;

class Solution {
    public int minMirrorPairDistance(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> lastSeen = new HashMap<>();
        int minDistance = Integer.MAX_VALUE;

        for (int j = 0; j < n; j++) {
            int currentVal = nums[j];
            
            if (lastSeen.containsKey(currentVal)) {
                minDistance = Math.min(minDistance, j - lastSeen.get(currentVal));
            }

            int rev = reverse(currentVal);
            lastSeen.put(rev, j);
        }

        return minDistance == Integer.MAX_VALUE ? -1 : minDistance;
    }

    private int reverse(int n) {
        long rev = 0;
        while (n > 0) {
            rev = rev * 10 + (n % 10);
            n /= 10;
        }
        return (rev > Integer.MAX_VALUE) ? -1 : (int) rev;
    }
}