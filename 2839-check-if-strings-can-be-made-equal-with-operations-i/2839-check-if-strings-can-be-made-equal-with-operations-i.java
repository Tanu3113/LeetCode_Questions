class Solution {
    public boolean canBeEqual(String s1, String s2) {

        // group 1: index 0 & 2
        char a1 = s1.charAt(0), a2 = s1.charAt(2);
        char b1 = s2.charAt(0), b2 = s2.charAt(2);

        // group 2: index 1 & 3
        char c1 = s1.charAt(1), c2 = s1.charAt(3);
        char d1 = s2.charAt(1), d2 = s2.charAt(3);

        return ((a1 == b1 && a2 == b2) || (a1 == b2 && a2 == b1)) &&
               ((c1 == d1 && c2 == d2) || (c1 == d2 && c2 == d1));
    }
}