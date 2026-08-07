class Solution {

   
    private static class Factors {
        int c2 = 0, c3 = 0, c5 = 0, c7 = 0;

        Factors() {}

        Factors(int c2, int c3, int c5, int c7) {
            this.c2 = c2;
            this.c3 = c3;
            this.c5 = c5;
            this.c7 = c7;
        }

        boolean isEmpty() {
            return c2 <= 0 && c3 <= 0 && c5 <= 0 && c7 <= 0;
        }

        Factors subtract(Factors o) {
            return new Factors(
                Math.max(0, c2 - o.c2),
                Math.max(0, c3 - o.c3),
                Math.max(0, c5 - o.c5),
                Math.max(0, c7 - o.c7)
            );
        }
    }

    private Factors getDigitFactors(char d) {
        int v = d - '0';
        Factors f = new Factors();
        if (v == 0) return f;
        while (v % 2 == 0) { f.c2++; v /= 2; }
        while (v % 3 == 0) { f.c3++; v /= 3; }
        if (v == 5) f.c5++;
        if (v == 7) f.c7++;
        return f;
    }

   
    private String factorsToDigits(Factors f) {
        int count7 = f.c7;
        int count5 = f.c5;
        
        int count9 = f.c3 / 2;
        int rem3 = f.c3 % 2;
        
        int count8 = f.c2 / 3;
        int rem2 = f.c2 % 3;
        
        int count4 = rem2 / 2;
        rem2 %= 2;

        int count2 = rem2;
        int count3 = rem3;
        int count6 = 0;

        
        if (count2 == 1 && count3 == 1) {
            count2 = 0;
            count3 = 0;
            count6 = 1;
        } else if (count3 == 1 && count4 == 1) {
            count3 = 0;
            count4 = 0;
            count2 = 1;
            count6 = 1;
        }

        StringBuilder sb = new StringBuilder();
        appendRepeated(sb, '2', count2);
        appendRepeated(sb, '3', count3);
        appendRepeated(sb, '4', count4);
        appendRepeated(sb, '5', count5);
        appendRepeated(sb, '6', count6);
        appendRepeated(sb, '7', count7);
        appendRepeated(sb, '8', count8);
        appendRepeated(sb, '9', count9);
        return sb.toString();
    }

    private void appendRepeated(StringBuilder sb, char ch, int count) {
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
    }

    public String smallestNumber(String num, long t) {
        Factors targetF = new Factors();
        long tempT = t;
        
        
        while (tempT % 2 == 0) { targetF.c2++; tempT /= 2; }
        while (tempT % 3 == 0) { targetF.c3++; tempT /= 3; }
        while (tempT % 5 == 0) { targetF.c5++; tempT /= 5; }
        while (tempT % 7 == 0) { targetF.c7++; tempT /= 7; }

        
        if (tempT > 1) return "-1";

        int n = num.length();

        
        Factors[] pref = new Factors[n + 1];
        pref[0] = new Factors();
        
        int firstZero = n;
        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') {
                firstZero = i;
                break;
            }
            Factors f = getDigitFactors(num.charAt(i));
            pref[i + 1] = new Factors(
                pref[i].c2 + f.c2,
                pref[i].c3 + f.c3,
                pref[i].c5 + f.c5,
                pref[i].c7 + f.c7
            );
        }

        
        if (firstZero == n) {
            Factors rem = targetF.subtract(pref[n]);
            if (rem.isEmpty()) return num;
        }

        
        for (int i = Math.min(n - 1, firstZero); i >= 0; i--) {
            Factors currentPref = pref[i];
            int availableSpace = n - 1 - i;

            int startDigit = (i < firstZero) ? (num.charAt(i) - '0' + 1) : 1;

            for (int d = startDigit; d <= 9; d++) {
                Factors dFactors = getDigitFactors((char) ('0' + d));
                Factors combinedPref = new Factors(
                    currentPref.c2 + dFactors.c2,
                    currentPref.c3 + dFactors.c3,
                    currentPref.c5 + dFactors.c5,
                    currentPref.c7 + dFactors.c7
                );

                Factors needed = targetF.subtract(combinedPref);
                String tailDigits = factorsToDigits(needed);

                if (tailDigits.length() <= availableSpace) {
                    StringBuilder res = new StringBuilder();
                    res.append(num, 0, i);
                    res.append(d);
                    
                    int onesToPad = availableSpace - tailDigits.length();
                    appendRepeated(res, '1', onesToPad);
                    res.append(tailDigits);
                    
                    return res.toString();
                }
            }
        }

        
        String minDigits = factorsToDigits(targetF);
        int targetLen = Math.max(n + 1, minDigits.length());
        int onesToPad = targetLen - minDigits.length();
        
        StringBuilder res = new StringBuilder();
        appendRepeated(res, '1', onesToPad);
        res.append(minDigits);
        return res.toString();
    }
}