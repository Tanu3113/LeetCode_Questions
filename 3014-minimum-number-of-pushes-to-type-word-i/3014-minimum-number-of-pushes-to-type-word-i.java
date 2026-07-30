class Solution {
    public int minimumPushes(String word) {
        int n = word.length();
        int totalPushes = 0;
        int iteration = 1;
        
        while (n > 0) {
            if (n >= 8) {
                totalPushes += 8 * iteration;
                n -= 8;
            } else {
                totalPushes += n * iteration;
                n = 0;
            }
            iteration++;
        }
        
        return totalPushes;
    }
}