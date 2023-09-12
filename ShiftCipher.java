/**
 * HW1 Q2
 * Write a program (in C, Java, Python, Rust, …) that can implement the shift
 * cipher. It takes as input plain ascii text, a character “D” or “E” to denote
 * decryption or encryption and a key k that is also a capital alphabet. It
 * converts the text into all caps, ignores all other characters and outputs the
 * ciphertext or plaintext as the case may be. Make sure that it prints an error
 * if anything other than D or E is typed or if the key is not a capital
 * alphabet.
 * a. Attach the source code with your homework.
 * b. Show some example inputs and outputs.
 */
public class ShiftCipher {
    // Driver code
    public static void main(String[] args) {
        String text = "z ba";
        int key = 1;
        System.out.println("Text  : " + text);
        System.out.println("Shift : " + key);
        System.out.println("Cipher: " + encrypt(text, key));
    }

    // Encrypts text using a shift od s
    public static String encrypt(String str, int key) {
        StringBuilder sb = new StringBuilder();
        for (char ch : str.replaceAll("[^a-zA-Z\\s]", "").toUpperCase().toCharArray()) {
            sb.append(Character.isSpaceChar(ch) ? ' ' : (char) ((ch + key - 'A') % 26 + 'A'));
        }
        return sb.toString();
    }
}