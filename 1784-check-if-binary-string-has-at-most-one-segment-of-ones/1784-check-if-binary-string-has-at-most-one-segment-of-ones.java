class Solution {
    public boolean checkOnesSegment(String s) {
        
        int length = s.length();

        if(s.charAt(0) == '0'){
            return false;
        }

        for(int i = 1; i<length;i++){
            if(s.charAt(i) == '1' && s.charAt(i-1) == '0'){
                return false;
            }
        }
        return true;
    }
}