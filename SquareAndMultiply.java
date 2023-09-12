
class SquareAndMultiply {
    public static void main(String[] args) {
        System.out.println(sqm(15  , 47, 277));
    }

    /**
     * adapted from lecture 10 pg 9
     * 
     * @param base
     * @param exponent
     * @param mod
     * @return
     */
    private static int sqm(int base, int exponent, int mod) {
        System.err.println("SQM for base = " + base + ", exponent = " + exponent + ", mod = " + mod);

        System.err.println("exponent in binary = " + Integer.toBinaryString(exponent));
        char[] b = Integer.toBinaryString(exponent).toCharArray();
        int u = 1;
        System.err.println("         | i | b | u | x");
        for (int i = 0; i < b.length; i++) {
            System.err.println("---------------------");
            System.err.print("Square   |         " + u + " | " + base);
            base = (base * base) % mod;

            if (b[i] == '1') {
                System.err.print("\nMultiply | ");
                u = (u * base) % mod;

            } else {
                System.err.print("\n         | ");
            }
            System.err.println(i + " | " + b[i] + " | " + u + " | " + base);
        }
        return u % mod;

    }

}