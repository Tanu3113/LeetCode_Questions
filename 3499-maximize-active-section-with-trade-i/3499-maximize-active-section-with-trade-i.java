class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int totalOnes = 0;
        List<Integer> zeroBlocks = new ArrayList<>();
        
        int n = s.length();
        int i = 0;
        while (i < n) {
            if (s.charAt(i) == '1') {
                totalOnes++;
                i++;
            } else {
                int len = 0;
                while (i < n && s.charAt(i) == '0') {
                    len++;
                    i++;
                }
                zeroBlocks.add(len);
            }
        }
        
        int maxTradeGain = 0;
        for (int k = 0; k < zeroBlocks.size() - 1; k++) {
            maxTradeGain = Math.max(maxTradeGain, zeroBlocks.get(k) + zeroBlocks.get(k + 1));
        }
        
        return totalOnes + maxTradeGain;
    }
}