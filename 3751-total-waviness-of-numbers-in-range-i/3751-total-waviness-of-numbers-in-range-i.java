class Solution {
    public int totalWaviness(int num1, int num2) {
        int ans = 0;

        for (int num = num1; num <= num2; num++) {
            String s = String.valueOf(num);
            int n = s.length();

            for (int i = 1; i < n - 1; i++) {
                char prev = s.charAt(i - 1);
                char cur = s.charAt(i);
                char next = s.charAt(i + 1);

                if ((cur > prev && cur > next) ||
                    (cur < prev && cur < next)) {
                    ans++;
                }
            }
        }

        return ans;
    }
}