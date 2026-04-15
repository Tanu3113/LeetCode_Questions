class Solution {
    public int closestTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int minDist = n; 
        boolean found = false;

        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {
                found = true;
                int diff = Math.abs(i - startIndex);
                
                int currentDist = Math.min(diff, n - diff);
                minDist = Math.min(minDist, currentDist);
                
                if (minDist == 0) return 0;
            }
        }

        return found ? minDist : -1;
    }
}