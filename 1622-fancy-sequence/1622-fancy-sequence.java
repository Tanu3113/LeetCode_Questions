class Fancy {
    private List<Long> list;
    private long M = 1;
    private long A = 0;
    private static final int MOD = 1_000_000_007;

    public Fancy() {
        list = new ArrayList<>();
    }

    public void append(int val) {
        long reverseVal = (val - A + MOD) % MOD;
        reverseVal = (reverseVal * power(M, MOD - 2)) % MOD;
        list.add(reverseVal);
    }

    public void addAll(int inc) {
        A = (A + inc) % MOD;
    }

    public void multAll(int m) {
        M = (M * m) % MOD;
        A = (A * m) % MOD;
    }

    public int getIndex(int idx) {
        if (idx >= list.size()) return -1;
        long res = (list.get(idx) * M) % MOD;
        res = (res + A) % MOD;
        return (int) res;
    }

    private long power(long a, long b) {
        long res = 1;
        a %= MOD;
        while (b > 0) {
            if (b % 2 == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            b /= 2;
        }
        return res;
    }
}