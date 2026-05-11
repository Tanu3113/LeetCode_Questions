import java.util.*;

class Solution {
    public int[] separateDigits(int[] nums) {
        List<Integer> resultList = new ArrayList<>();
        
        for (int num : nums) {
            String s = Integer.toString(num);
            for (int i = 0; i < s.length(); i++) {
                resultList.add(s.charAt(i) - '0');
            }
        }
        int[] answer = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            answer[i] = resultList.get(i);
        }
        return answer;
    }
}