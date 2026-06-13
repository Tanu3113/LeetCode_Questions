class Solution {
    public String mapWordWeights(String[] words, int[] weights) {
        StringBuilder result = new StringBuilder();
        
        for (String word : words) {
            int totalWeight = 0;
            
            
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a';
                totalWeight += weights[index];
            }
            
           
            int remainder = totalWeight % 26;
            
           
            char mappedChar = (char) ('z' - remainder);
            
           
            result.append(mappedChar);
        }
        
        return result.toString();
    }
}