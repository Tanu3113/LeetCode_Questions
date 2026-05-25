class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();
      
        if (s.charAt(n - 1) == '1') {
            return false;
        }

        boolean[] dp = new boolean[n];
        dp[0] = true; 

       
        int reachableOriginCount = 0;

  
        for (int i = 1; i < n; i++) {
            
            int enteringIndex = i - minJump;
            if (enteringIndex >= 0 && dp[enteringIndex]) {
                reachableOriginCount++;
            }
            
            int leavingIndex = i - maxJump - 1;
            if (leavingIndex >= 0 && dp[leavingIndex]) {
                reachableOriginCount--;
            }
          
            if (s.charAt(i) == '0' && reachableOriginCount > 0) {
                dp[i] = true;
            }
        }

        return dp[n - 1];
    }
}