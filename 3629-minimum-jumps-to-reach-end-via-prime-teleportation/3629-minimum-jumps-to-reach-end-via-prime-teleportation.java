import java.util.*;

class Solution {
    public int minJumps(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;

        int maxVal = 0;
        for (int num : nums) maxVal = Math.max(maxVal, num);

        int[] minPrime = new int[maxVal + 1];
        for (int i = 2; i <= maxVal; i++) {
            if (minPrime[i] == 0) {
                for (int j = i; j <= maxVal; j += i) {
                    if (minPrime[j] == 0) minPrime[j] = i;
                }
            }
        }

        Map<Integer, List<Integer>> primeToIndices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            while (val > 1) {
                int p = minPrime[val];
                primeToIndices.computeIfAbsent(p, k -> new ArrayList<>()).add(i);
                while (val % p == 0) val /= p;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[0] = 0;
        
        boolean[] usedPrimes = new boolean[maxVal + 1];

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            
            if (curr == n - 1) return dist[curr];

            if (curr + 1 < n && dist[curr + 1] == -1) {
                dist[curr + 1] = dist[curr] + 1;
                queue.offer(curr + 1);
            }
            if (curr - 1 >= 0 && dist[curr - 1] == -1) {
                dist[curr - 1] = dist[curr] + 1;
                queue.offer(curr - 1);
            }

            int val = nums[curr];
            if (val >= 2 && minPrime[val] == val) {
                if (!usedPrimes[val]) {
                    usedPrimes[val] = true;
                    List<Integer> targets = primeToIndices.get(val);
                    if (targets != null) {
                        for (int nextIdx : targets) {
                            if (dist[nextIdx] == -1) {
                                dist[nextIdx] = dist[curr] + 1;
                                queue.offer(nextIdx);
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }
}