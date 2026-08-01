class Solution {
    public java.util.List<Integer> sequentialDigits(int low, int high) {
        java.util.List<Integer> ans = new java.util.ArrayList<>();
        String s = "123456789";

        for (int len = String.valueOf(low).length(); len <= String.valueOf(high).length(); len++) {
            for (int i = 0; i + len <= 9; i++) {
                int num = Integer.parseInt(s.substring(i, i + len));
                if (num >= low && num <= high) {
                    ans.add(num);
                }
            }
        }

        return ans;
    }
}