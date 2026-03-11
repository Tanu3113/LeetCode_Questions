class Solution {
    public int bitwiseComplement(int n) {
        
        if (n == 0) return 1;

        int number_of_bits = 0;
        int temp = n;

        while (temp > 0) {
            number_of_bits++;
            temp >>= 1;
        }

        
        int mask = (1 << number_of_bits) - 1;

        return n ^ mask;
    }
}