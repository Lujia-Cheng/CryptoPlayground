/**
 * HW2 Q5
 * Decrypt (by exhaustive search) the following ciphertext which was obtained
 * from the autokeyed Vigenere cipher: XBBALBUQXHHTKOWWGWLZBRQWVZWIMDJ. You can
 * use a modification of the program you wrote in Homework 1
 */
public class VigenereCipher {
    // Driver code
    public static void main(String[] args) {
        String str = "XBBALBUQXHHTKOWWGWLZBRQWVZWIMDJ".toUpperCase();
        String keyword = "AYUSH".toUpperCase();

        String key = generateKey(str, keyword);
        String cipher_text = cipherText(str, key);

        System.out.println("Ciphertext : " + cipher_text + "\n");
        System.out.println("Original/Decrypted Text : " + originalText(cipher_text, key));
    }

    private static String generateKey(String str, String key) {
        int x = str.length();

        for (int i = 0;; i++) {
            if (x == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key += (key.charAt(i));
        }
        return key;
    }

    // This function returns the encrypted text
    // generated with the help of the key
    private static String cipherText(String str, String key) {
        String cipher_text = "";

        for (int i = 0; i < str.length(); i++) {
            // converting in range 0-25
            int x = (str.charAt(i) + key.charAt(i)) % 26;

            // convert into alphabets(ASCII)
            x += 'A';

            cipher_text += (char) (x);
        }
        return cipher_text;
    }

    // This function decrypts the encrypted text
    // and returns the original text
    static String originalText(String cipher_text, String key) {
        String orig_text = "";

        for (int i = 0; i < cipher_text.length() &&
                i < key.length(); i++) {
            // converting in range 0-25
            int x = (cipher_text.charAt(i) -
                    key.charAt(i) + 26) % 26;

            // convert into alphabets(ASCII)
            x += 'A';
            orig_text += (char) (x);
        }
        return orig_text;
    }

}