import java.util.*;

class Solution {
    private Integer[][] memo;
    private String s;

    public int minimumDistance(String word) {
        this.s = word;
        this.memo = new Integer[word.length()][27];
        return solve(0, 26);
    }

    private int solve(int idx, int otherFinger) {
        if (idx == s.length()) return 0;
        if (memo[idx][otherFinger] != null) return memo[idx][otherFinger];

        int currChar = s.charAt(idx) - 'A';
        int prevChar = (idx == 0) ? 26 : s.charAt(idx - 1) - 'A';

        int movePrevFinger = getDist(prevChar, currChar) + solve(idx + 1, otherFinger);
        int moveOtherFinger = getDist(otherFinger, currChar) + solve(idx + 1, prevChar);

        return memo[idx][otherFinger] = Math.min(movePrevFinger, moveOtherFinger);
    }

    private int getDist(int a, int b) {
        if (a == 26 || b == 26) return 0;
        return Math.abs(a / 6 - b / 6) + Math.abs(a % 6 - b % 6);
    }
}