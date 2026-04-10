import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        int n = nums.length;
        int minDistance = Integer.MAX_VALUE;
        boolean found = false;

        Map<Integer, List<Integer>> indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indexMap.computeIfAbsent(nums[i], x -> new ArrayList<>()).add(i);
        }

        for (List<Integer> indices : indexMap.values()) {
            if (indices.size() >= 3) {
                for (int i = 0; i <= indices.size() - 3; i++) {
                    int first = indices.get(i);
                    int third = indices.get(i + 2);
                    int currentDistance = 2 * (third - first);
                    minDistance = Math.min(minDistance, currentDistance);
                    found = true;
                }
            }
        }

        return found ? minDistance : -1;
    }
}