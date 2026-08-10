import java.util.*;

class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        
        int[] last = new int[m];
        int ptr = n - 1;

        for (int j = m - 1; j >= 0; j--) {
            while (ptr >= 0 && word1.charAt(ptr) != word2.charAt(j)) {
                ptr--;
            }
            last[j] = ptr;
            if (ptr >= 0) {
                ptr--; 
            }
        }

        int[] result = new int[m];
        boolean changed = false;
        int i = 0; 

        for (int j = 0; j < m; j++) {
            boolean matched = false;

            while (i < n) {
                boolean isCharMatch = word1.charAt(i) == word2.charAt(j);

                if (isCharMatch) {
                    result[j] = i;
                    i++;
                    matched = true;
                    break;
                } else if (!changed) {
                  
                    boolean canCompleteSuffix = (j == m - 1) || (last[j + 1] > i);

                    if (canCompleteSuffix) {
                        result[j] = i;
                        changed = true;
                        i++;
                        matched = true;
                        break;
                    }
                }

                i++;
            }

            
            if (!matched) {
                return new int[0];
            }
        }

        return result;
    }
}