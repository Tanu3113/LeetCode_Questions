class Solution {

    public String lexGreaterPermutation(String s, String target) {

        int n = s.length();

        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        char[] ans = new char[n];

        int i;

        for (i = 0; i < n; i++) {

            int c = target.charAt(i) - 'a';

            if (freq[c] > 0) {
                ans[i] = target.charAt(i);
                freq[c]--;
            } else {
                break;
            }
        }

        if (i < n) {

            int greater = findGreater(freq, target.charAt(i) - 'a');

            if (greater != -1) {

                ans[i] = (char) ('a' + greater);
                freq[greater]--;

                fillRemaining(ans, i + 1, freq);

                return new String(ans);
            }
        }

        for (int j = i - 1; j >= 0; j--) {

            int current = ans[j] - 'a';
            freq[current]++;

            int greater = findGreater(
                    freq,
                    target.charAt(j) - 'a'
            );

            if (greater != -1) {

                ans[j] = (char) ('a' + greater);
                freq[greater]--;

                fillRemaining(ans, j + 1, freq);

                return new String(ans);
            }
        }

        return "";
    }

    private int findGreater(int[] freq, int targetChar) {

        for (int c = targetChar + 1; c < 26; c++) {

            if (freq[c] > 0) {
                return c;
            }
        }

        return -1;
    }

    private void fillRemaining(
            char[] ans,
            int start,
            int[] freq) {

        int index = start;

        for (int c = 0; c < 26; c++) {

            while (freq[c] > 0) {

                ans[index++] = (char) ('a' + c);
                freq[c]--;
            }
        }
    }
}