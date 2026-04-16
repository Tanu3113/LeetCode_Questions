import java.util.*;

class Solution {
    public List<Integer> solveQueries(int[] nums, int[] queries) {
        int n = nums.length;
        Map<Integer, List<Integer>> posMap = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            posMap.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (int idxInQueries : queries) {
            int val = nums[idxInQueries];
            List<Integer> positions = posMap.get(val);

            if (positions == null || positions.size() <= 1) {
                result.add(-1);
                continue;
            }

            int listIdx = Collections.binarySearch(positions, idxInQueries);
            int prev, next;

            if (listIdx == 0) {
                prev = positions.get(positions.size() - 1);
                next = positions.get(1);
            } else if (listIdx == positions.size() - 1) {
                prev = positions.get(listIdx - 1);
                next = positions.get(0);
            } else {
                prev = positions.get(listIdx - 1);
                next = positions.get(listIdx + 1);
            }

            int dist1 = Math.min(Math.abs(idxInQueries - next), n - Math.abs(idxInQueries - next));
            int dist2 = Math.min(Math.abs(idxInQueries - prev), n - Math.abs(idxInQueries - prev));

            result.add(Math.min(dist1, dist2));
        }

        return result;
    }
}