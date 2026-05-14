class Solution {
    public boolean isGood(int[] nums) {
        int n = nums.length - 1;
        int[] freq = new int[201];
        for (int x : nums) freq[x]++;
        if (freq[n] != 2) return false;
        for (int i = 1; i < n; i++) {
            if (freq[i] != 1) return false;
        }
        return true;
    }
}