class Solution {
    public String getHappyString(int n, int k) {
        int total = 3 * (int) Math.pow(2, n - 1);
        if (k > total) return "";

        StringBuilder sb = new StringBuilder();
       
        char[] choices = {'a', 'b', 'c'};
        
       
        int rangeSize = (int) Math.pow(2, n - 1);
       
        int index = (k - 1) / rangeSize;
        sb.append(choices[index]);
        
        
        k -= index * rangeSize;

       
        for (int i = 1; i < n; i++) {
            rangeSize /= 2;
            char last = sb.charAt(sb.length() - 1);
            
            
            char nextChar1 = (last == 'a') ? 'b' : 'a';
            char nextChar2 = (last == 'c') ? 'b' : 'c';
            
          
            char firstChoice = (nextChar1 < nextChar2) ? nextChar1 : nextChar2;
            char secondChoice = (nextChar1 < nextChar2) ? nextChar2 : nextChar1;

            if (k <= rangeSize) {
                sb.append(firstChoice);
            } else {
                sb.append(secondChoice);
                k -= rangeSize;
            }
        }

        return sb.toString();
    }
}