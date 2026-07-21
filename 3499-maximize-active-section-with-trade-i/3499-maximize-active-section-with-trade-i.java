class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        java.util.List<Integer> zeroGroupLengths = new java.util.ArrayList<>();
        int originalOnes = 0;
        int n = s.length();
        int i = 0;

        while (i < n) {
            if (s.charAt(i) == '1') {
                originalOnes++;
                i++;
            } else {
                int j = i;
                while (j < n && s.charAt(j) == '0') {
                    j++;
                }
                zeroGroupLengths.add(j - i);
                i = j;
            }
        }

        int maxZeroMerge = 0;
        for (int k = 0; k < zeroGroupLengths.size() - 1; k++) {
            maxZeroMerge = Math.max(maxZeroMerge, zeroGroupLengths.get(k) + zeroGroupLengths.get(k + 1));
        }

        return originalOnes + maxZeroMerge;
    }
}