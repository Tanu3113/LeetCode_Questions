import java.util.HashMap;
import java.util.Map;

public class Solution {
    public int maximumLength(int[] nums) {
        Map<Long, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put((long) num, countMap.getOrDefault((long) num, 0) + 1);
        }

        int maxLen = 1;

        if (countMap.containsKey(1L)) {
            int onesCount = countMap.get(1L);
            maxLen = Math.max(maxLen, onesCount % 2 == 0 ? onesCount - 1 : onesCount);
        }

        for (long x : countMap.keySet()) {
            if (x == 1) continue;

            int currentLen = 0;
            long current = x;

            while (countMap.containsKey(current)) {
                int count = countMap.get(current);
                
                if (count >= 2) {
                    currentLen += 2;
                    current = current * current; 
                    
                    if (current > 1000000000L && countMap.containsKey(current)) {
                        if (countMap.get(current) >= 1) {
                            currentLen += 1;
                        } else {
                            currentLen -= 1; 
                        }
                        break;
                    }
                } else {
                    currentLen += 1;
                    break;
                }
            }

            if (!countMap.containsKey(current) && currentLen % 2 == 0 && currentLen > 0) {
                currentLen -= 1;
            }

            maxLen = Math.max(maxLen, currentLen);
        }

        return maxLen;
    }
}