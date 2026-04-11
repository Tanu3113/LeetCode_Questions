import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        int minTotalDistance = Integer.MAX_VALUE;
        boolean found = false;

        for (int i = 0; i < n; i++) {
            int val = nums[i];
            
            if (!map.containsKey(val)) {
                map.put(val, new ArrayList<>());
            }
            
            List<Integer> indices = map.get(val);
            indices.add(i);

            if (indices.size() >= 3) {
                int firstIdx = indices.get(indices.size() - 3);
                int thirdIdx = indices.get(indices.size() - 1);
                
                int currentDistance = 2 * (thirdIdx - firstIdx);
                minTotalDistance = Math.min(minTotalDistance, currentDistance);
                found = true;
                
                indices.remove(0);
            }
        }

        return found ? minTotalDistance : -1;
    }
}