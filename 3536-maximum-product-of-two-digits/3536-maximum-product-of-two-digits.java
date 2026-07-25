class Solution {
    public int maxProduct(int n) {
        String s = String.valueOf(n);
        int maxProd = 0;
        
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                int d1 = s.charAt(i) - '0';
                int d2 = s.charAt(j) - '0';
                maxProd = Math.max(maxProd, d1 * d2);
            }
        }
        
        return maxProd;
    }
}