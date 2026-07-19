class Solution {
    public String smallestSubsequence(String s) {
        int[] last = new int[26];
        boolean[] used = new boolean[26];

        for (int i = 0; i < s.length(); i++) {
            last[s.charAt(i) - 'a'] = i;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int idx = c - 'a';

            if (used[idx]) {
                continue;
            }

            while (sb.length() > 0 &&
                   sb.charAt(sb.length() - 1) > c &&
                   last[sb.charAt(sb.length() - 1) - 'a'] > i) {
                used[sb.charAt(sb.length() - 1) - 'a'] = false;
                sb.deleteCharAt(sb.length() - 1);
            }

            sb.append(c);
            used[idx] = true;
        }

        return sb.toString();
    }
}