class Solution {
    public int totalNumbers(int[] digits) {
        HashSet<Integer> uniqueNumbers = new HashSet<>();
        int n = digits.length;

        for (int i = 0; i < n; i++) {
            if (digits[i] == 0) continue;
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    
                    int lastDigit = digits[k];
                    if (lastDigit % 2 != 0) continue;

                    int num = digits[i] * 100 + digits[j] * 10 + lastDigit;
                    uniqueNumbers.add(num);
                }
            }
        }

        return uniqueNumbers.size();
    }
}