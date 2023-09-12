/**
 * Midterm Q5
 */
public class CipherBlockChaining {
    public static void main(String[] args) {
        String target = "NZRWHTX";
        for (char ch : target.toCharArray()) {
            System.out.print(ch - 'A' + " ");
        }
        System.out.println("cipherText=" + target);
        String forcedAns = bruteForce(target);
        System.out.println("forced plainText=" + forcedAns);
        System.out.println("isCorrect?" + target.equals(encrypt(forcedAns, 'S', 'F')));
        System.out.println("decrypted=" + decrypt(target, 'S', 'F'));
    }

    public static String bruteForce(String target) {
        // brute force
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < target.length(); i++) { // check each position
            for (int j = 'A'; j <= 'Z'; j++) { // check each char for each position
                sb.append((char) j);
                if (target.substring(0, i + 1).equals(encrypt(sb.toString(), 'S', 'F'))) {
                    break;
                } else {
                    sb.setLength(sb.length() - 1);
                }
            }
        }
        return sb.toString();
    }

    /**
     * CBC encrypt
     * 
     * @param plainText    will be converted to uppercase
     * @param key
     * @param initialValue initial value
     * @return encrypted msg
     */
    public static String encrypt(String plainText, char key, char initialValue) {
        int k = Character.toUpperCase(key) - 'A';
        int iv = Character.toUpperCase(initialValue) - 'A';
        int[] pt = new int[plainText.length()];
        for (int i = 0; i < pt.length; i++) {
            pt[i] = Character.toUpperCase(plainText.charAt(i)) - 'A';
        }

        StringBuilder sb = new StringBuilder();

        int prev = iv;
        for (int x : pt) {
            x = Math.floorMod(x + prev, 26);
            x = Math.floorMod(x + k, 26);
            sb.append((char) ('A' + x));
            prev = x;
        }
        return sb.toString();
    }

    public static String decrypt(String cipherText, char key, char initialValue) {
        int k = Character.toUpperCase(key) - 'A';
        int iv = Character.toUpperCase(initialValue) - 'A';
        int[] ct = new int[cipherText.length()];
        for (int i = 0; i < ct.length; i++) {
            ct[i] = Character.toUpperCase(cipherText.charAt(i)) - 'A';
        }

        StringBuilder sb = new StringBuilder();

        for (int i = ct.length - 1; i > 0; i--) {
            int x = Math.floorMod(ct[i] - ct[i - 1] - k, 26);
            sb.insert(0, (char) ('A' + x));
        }
        sb.insert(0, (char) ('A' + iv));
        return sb.toString();
    }
}