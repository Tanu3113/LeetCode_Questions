class Solution {
    public boolean checkDivisibility(int n) {
        int temp = n;
        int digitSum = 0;
        int digitProduct = 1;

        while (temp > 0) {
            int d = temp % 10;
            digitSum += d;
            digitProduct *= d;
            temp /= 10;
        }

        int total = digitSum + digitProduct;
        return n % total == 0;
    }
}