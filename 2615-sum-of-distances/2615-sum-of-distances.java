import java.util.*;

class Solution {
    public long[] distance(int[] nums) {
        int n = nums.length;
        long[] res = new long[n];
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        for (List<Integer> indices : map.values()) {
            int size = indices.size();
            if (size <= 1) continue;

            long totalSum = 0;
            for (int index : indices) {
                totalSum += index;
            }

            long prefixSum = 0;
            for (int i = 0; i < size; i++) {
                int index = indices.get(i);
                long leftCount = i;
                long rightCount = size - 1 - i;

                long leftContribution = leftCount * index - prefixSum;
                long rightContribution = (totalSum - prefixSum - index) - rightCount * index;

                res[index] = leftContribution + rightContribution;
                prefixSum += index;
            }
        }

        return res;
    }
}