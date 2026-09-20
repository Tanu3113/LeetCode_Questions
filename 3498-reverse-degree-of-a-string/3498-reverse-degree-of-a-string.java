class Solution {
    public int reverseDegree(String s) {
        long totalSum = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            long revAlphabet = 26 - (c - 'a');
            long stringIndex = i + 1;
            totalSum += revAlphabet * stringIndex;
        }
        return (int) totalSum;
    }
}