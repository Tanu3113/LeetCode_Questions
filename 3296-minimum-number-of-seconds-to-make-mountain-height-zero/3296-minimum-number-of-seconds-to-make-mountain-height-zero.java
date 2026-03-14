class Solution {
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        long low = 1;
        long high = 10000000000000000L;
        long ans = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (canFinish(mid, mountainHeight, workerTimes)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    private boolean canFinish(long totalTime, int targetHeight, int[] workerTimes) {
        long totalReducedHeight = 0;
        for (int w : workerTimes) {
           
            double val = (2.0 * totalTime) / w;
            long x = (long) ((-1 + Math.sqrt(1 + 4 * val)) / 2);
            
            totalReducedHeight += x;
            if (totalReducedHeight >= targetHeight) return true;
        }
        return totalReducedHeight >= targetHeight;
    }
}