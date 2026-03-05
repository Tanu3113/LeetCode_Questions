class Solution {
    public int minOperations(String s) {
    
        int res = 0;
        int n = s.length();
        
        for (int i = 0; i < n; i++) {
            
            
            if (i % 2 == 0) {
                if (s.charAt(i) != '0') {
                    res++;
                }
            } else {
                if (s.charAt(i) != '1') {
                    res++;
                }
            }
        }
        
        
        return Math.min(res, n - res);
    }
}
   