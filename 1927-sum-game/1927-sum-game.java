class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int sumDiff = 0;
        int countDiff = 0;

        for (int i = 0; i < n / 2; i++) {
            char c = num.charAt(i);
            if (c == '?') {
                countDiff++;
            } else {
                sumDiff += (c - '0');
            }
        }

        for (int i = n / 2; i < n; i++) {
            char c = num.charAt(i);
            if (c == '?') {
                countDiff--;
            } else {
                sumDiff -= (c - '0');
            }
        }
        return (countDiff % 2 != 0) || (2 * sumDiff + countDiff * 9 != 0);
    }
}