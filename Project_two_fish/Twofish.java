package Project;

import java.lang.String;
import java.nio.charset.StandardCharsets;

class Twofish {
    /**
     * set DEBUG true and set ROUNDS to a smaller number to see more detail
     */
    private static final boolean DEBUG = true;
    private static final int ROUNDS = 16;

    public static void main(String[] args) {
        System.out.println("---MAIN START---");
        System.out.println("DEBUG=" + DEBUG + "TOTAL_ROUNDS=" + ROUNDS);

        // plaintext below (16 char = 128 bit)
        String plainText = "128 bit sentence";
        System.out.println("plainText in bytes[]=" + byteArrayToHexString(plainText.getBytes(StandardCharsets.UTF_8)));

        // create a simple 128 bits key by concatenating 1,2,3,...16
        byte[] keyBytes = new byte[16]; // 128 bit
        for (int i = 0; i < 16; i++) {
            keyBytes[i] = (byte) i;
        }
        System.out.println("\nkeys in bytes[]=" + byteArrayToHexString(keyBytes));


        byte[] cipherText = encrypt(plainText, keyBytes);
        System.out.println("\ncipher text in hex:" + byteArrayToHexString(cipherText));

        byte[] decryptedText = decrypt(cipherText, keyBytes);
        System.out.println("\ndecrypted text in hex:" + byteArrayToHexString(decryptedText));
        System.out.println("decrypted text is: \"" + new String(decryptedText) + "\"");
        System.out.println("END");
    }

    private static final int[] ROUND_KEY = {
            0x36778527, 0xE196A5DA, 0xE3683D59, 0xD750042E, 0x34464907, 0xE2E45591, 0xB6BDB2D1, 0xE78EFBE4, 0x99B7BCE5,
            0x505D05EC, 0x35BFC23B, 0x2794E0D3, 0x02563C03, 0x9967AEC0, 0x989381D7, 0xEF4AD00C, 0x5FAB39B1, 0x54C26424,
            0x3E270697, 0x7774767D, 0x938A6ACA, 0x54DCC8FD, 0x9760F9FA, 0x87233505, 0x55C28087, 0x2E8658F3, 0x5E2BFFB2,
            0x0D7F5FB3, 0x620C9383, 0x6B571921, 0xDA50CBC2, 0x8E509BD2, 0x512CF533, 0xA117C585, 0xB3C81D97, 0x9EE01B31,
            0xDECFB00B, 0xC6A56859, 0x5FF82747, 0x4BBC3E72};

    private static final byte[] S_BOX = {
            (byte) 0x1B1B1114, (byte) 0x0BE75656, (byte) 0x83831F51, (byte) 0x6D2CAEAE,
            (byte) 0x0606F48D, (byte) 0xD0E48C8C, (byte) 0x84841A55, (byte) 0xB5C22C2C,
            (byte) 0x4F4F7D64, (byte) 0xF27A0303, (byte) 0x525298FD, (byte) 0x4AECC9C9,
            (byte) 0xB5B53D79, (byte) 0x23C56060, (byte) 0x6464BAD5, (byte) 0xF1975B5B,
            (byte) 0xEBEB24F7, (byte) 0xB499C3C3, (byte) 0xD6D682A8, (byte) 0xD2523B3B,
            (byte) 0x36362228, (byte) 0x38362A2A, (byte) 0x54546C70, (byte) 0x98BEF2F2,
            (byte) 0x7D7D20BA, (byte) 0x00000000, (byte) 0xEDEDD07A, (byte) 0x1FF64D4D,
            (byte) 0x38382820, (byte) 0x89F10101, (byte) 0x1414EA95, (byte) 0xBB7B9292,
            (byte) 0x878760A7, (byte) 0x8B47B6B6, (byte) 0xF7F730E7, (byte) 0xB9CD2525,
            (byte) 0x5B5B97F1, (byte) 0xFE750A0A, (byte) 0xABABA212, (byte) 0x3BDB7272,
            (byte) 0x2222C8BD, (byte) 0x57AC3333, (byte) 0x74742FB6, (byte) 0x8F42B1B1,
            (byte) 0x8C8CE4D0, (byte) 0xE8D2A6A6, (byte) 0xB9B9BC0A, (byte) 0x242D3F3F,
            (byte) 0x4C4C0796, (byte) 0x72DAE3E3, (byte) 0xC7C7E642, (byte) 0x4D049696,
            (byte) 0x9B9B74B7, (byte) 0x5BA33A3A, (byte) 0x7272DB3B, (byte) 0x6B9F1E1E,
            (byte) 0xDEDE7C2D, (byte) 0x63951010, (byte) 0x65654B5C, (byte) 0xDB03DADA,
            (byte) 0xF6F6C16E, (byte) 0xC1AB7F7F, (byte) 0x6D6DB5D9, (byte) 0x6929A9A9,
            (byte) 0x4E4E8CED, (byte) 0xECD7A1A1, (byte) 0xF4F44A15, (byte) 0x551A8484,
            (byte) 0x4141776C, (byte) 0x303C2424, (byte) 0x8686912E, (byte) 0x5FA63D3D,
            (byte) 0x8E8E6FAB, (byte) 0xD309D4D4, (byte) 0x6E6ECF2B, (byte) 0xA5D63030,
            (byte) 0x4B4B0292, (byte) 0x77840B0B, (byte) 0x98980E45, (byte) 0xEB3FFEFE,
            (byte) 0x7878AEC5, (byte) 0x81FB0F0F, (byte) 0xB2B2387D, (byte) 0x229E8F8F,
            (byte) 0x79795F4C, (byte) 0xB3719C9C, (byte) 0x808065A3, (byte) 0x5EFDD2D2,
            (byte) 0xCDCD93BC, (byte) 0x80A0E0E0, (byte) 0xBFBF4887, (byte) 0x1EADA2A2,
            (byte) 0x0101F189, (byte) 0xA9D93939, (byte) 0xECEC21F3, (byte) 0xF8C6BABA,
            (byte) 0x1313EF91, (byte) 0x85FE0808, (byte) 0x3333AC57, (byte) 0x27C06767,
            (byte) 0x1F1F6EE2, (byte) 0x6AC4F1F1, (byte) 0x202043C6, (byte) 0x1945FDFD,
            (byte) 0x3737D3A1, (byte) 0x17FC4343, (byte) 0xA8A8D8E0, (byte) 0x015BEFEF,
            (byte) 0x5656E70B, (byte) 0xE0D8A8A8, (byte) 0xA3A35C97, (byte) 0x99E51D1D,
            (byte) 0x474783E1, (byte) 0xE1834747, (byte) 0x9F9F0B41, (byte) 0x42E6C7C7,
            (byte) 0x0E0E0A08, (byte) 0xC8FA9E9E, (byte) 0x2C2CC2B5, (byte) 0xA882D6D6,
            (byte) 0xA0A02665, (byte) 0x2576D0D0, (byte) 0x5C5C92F5, (byte) 0xC31DC8C8,
            (byte) 0x6F6F3EA2, (byte) 0x7C635D5D, (byte) 0xF9F93AEF, (byte) 0x7AD0EDED,
            (byte) 0xE9E9AF8C, (byte) 0x28223636, (byte) 0x666631AE, (byte) 0x5AF8D5D5,
            (byte) 0x9797F5C4, (byte) 0x66CBF8F8, (byte) 0xC5C56D39, (byte) 0xC718CFCF,
            (byte) 0x3434A953, (byte) 0xE66B1818, (byte) 0x02028B7B, (byte) 0x793DB5B5,
            (byte) 0xEEEEAA88, (byte) 0x8A1C5959, (byte) 0xDADA03DB, (byte) 0x9F56ADAD,
            (byte) 0x6C6C4450, (byte) 0x60784848, (byte) 0xAAAA539B, (byte) 0xFA700D0D,
            (byte) 0x68683BA6, (byte) 0xE4DDAFAF, (byte) 0x2828BD43, (byte) 0x0D54E6E6,
            (byte) 0x3D3DA65F, (byte) 0x2FCA6969, (byte) 0x1E1E9F6B, (byte) 0xDA583535,
            (byte) 0xB4B4CCF0, (byte) 0xCB17C6C6, (byte) 0xA9A92969, (byte) 0x2173D7D7,
            (byte) 0x8F8F9E22, (byte) 0x2A948181, (byte) 0x57571682, (byte) 0x12A2ABAB,
            (byte) 0x70705040, (byte) 0xF321ECEC, (byte) 0x0C0C8173, (byte) 0x50446C6C,
            (byte) 0xF2F2BE98, (byte) 0xED8C4E4E, (byte) 0xD7D77321, (byte) 0xEA641111,
            (byte) 0x05058E7F, (byte) 0x82165757, (byte) 0x8282EED8, (byte) 0x34392323,
            (byte) 0x7E7E5A48, (byte) 0x84A5E7E7, (byte) 0x59591C8A, (byte) 0xDF06DDDD,
            (byte) 0xE2E22BFB, (byte) 0xAC87D1D1, (byte) 0x5F5FE807, (byte) 0xD6573C3C,
            (byte) 0xD0D07625, (byte) 0x06B3B0B0, (byte) 0x2626B74B, (byte) 0x7137BBBB,
            (byte) 0x8585EBDC, (byte) 0xDCEB8585, (byte) 0x17179067, (byte) 0xAA346161,
            (byte) 0x93938A32, (byte) 0x5C4B6565, (byte) 0xCBCB6731, (byte) 0xADDC3E3E,
            (byte) 0x1C1C1410, (byte) 0x269B8888, (byte) 0x5E5E198E, (byte) 0xF67F0404,
            (byte) 0x4444F913, (byte) 0xA7608787, (byte) 0x7B7BD437, (byte) 0x03ED5858,
            (byte) 0x3E3EDCAD, (byte) 0x07E85F5F, (byte) 0x2525CDB9, (byte) 0xBF7E9595,
            (byte) 0xB1B1428F, (byte) 0xC9A17171, (byte) 0x15151B1C, (byte) 0x1AA8A5A5,
            (byte) 0xFEFE3FEB, (byte) 0xC6432020, (byte) 0xBCBC3275, (byte) 0x94B1FBFB,
            (byte) 0x7171A1C9, (byte) 0xFB2BE2E2, (byte) 0xD4D409D3, (byte) 0x3167CBCB,
            (byte) 0xF1F1C46A, (byte) 0x1D40FAFA, (byte) 0xF3F34F11, (byte) 0xD8EE8282,
            (byte) 0x1A1AE09D, (byte) 0xA23E6F6F, (byte) 0xC2C2683D, (byte) 0x2D7CDEDE,
            (byte) 0x8D8D1559, (byte) 0xE26E1F1F, (byte) 0xDFDF8DA4, (byte) 0xD5BA6464,
            (byte) 0xAFAFDDE4, (byte) 0x706C5454, (byte) 0x73732AB2, (byte) 0xAE316666,
            (byte) 0xD5D5F85A, (byte) 0xA088D8D8, (byte) 0x6A6AB0DD, (byte) 0x485A7E7E,
            (byte) 0x18186BE6, (byte) 0x584E6262, (byte) 0xD2D2FD5E, (byte) 0x20283838,
            (byte) 0x3030D6A5, (byte) 0x4BB72626, (byte) 0xCCCC6235, (byte) 0x410B9F9F,
            (byte) 0x6363BFD1, (byte) 0x8DF40606, (byte) 0x24243C30, (byte) 0x2BCF6E6E,
            (byte) 0xE3E3DA72, (byte) 0x1C1B1515, (byte) 0xC8C81DC3, (byte) 0xD9B56D6D
    };

    /**
     * encryption
     *
     * @param plainText a string of plain text
     * @return
     */
    public static byte[] encrypt(String plainText, byte[] keyBytes) {
        System.out.println("---START encryption---");
        byte[] pt = plainText.getBytes(StandardCharsets.UTF_8);
        if (DEBUG) {
            System.out.println("plainText in bytes[]=" + byteArrayToHexString(pt));
        }
        int i = 0;
        // Below: the "& 0xFF" just to get byte value right in java
        // and input them to four 32 bit long bits respectively
        int x0 = pt[i++] & 0xFF | (pt[i++] & 0xFF) << 8 | (pt[i++] & 0xFF) << 16 | (pt[i++] & 0xFF) << 24;
        int x1 = pt[i++] & 0xFF | (pt[i++] & 0xff) << 8 | (pt[i++] & 0xFF) << 16 | (pt[i++] & 0xFF) << 24;
        int x2 = pt[i++] & 0xFF | (pt[i++] & 0xFF) << 8 | (pt[i++] & 0xFF) << 16 | (pt[i++] & 0xFF) << 24;
        int x3 = pt[i++] & 0xFF | (pt[i++] & 0xFF) << 8 | (pt[i++] & 0xFF) << 16 | (pt[i++] & 0xFF) << 24;
        if (DEBUG) {
            System.out.println("\nByte x0~x3 before initial XOR are");
            System.out.println(intToHexString(x0) + " " + intToHexString(x1) + " " + intToHexString(x2) + " " + intToHexString(x3));
        }

        // the initial round key (K0~K3) addition
        x0 ^= getRoundKey(0);
        x1 ^= getRoundKey(1);
        x2 ^= getRoundKey(2);
        x3 ^= getRoundKey(3);

        if (DEBUG) {
            System.out.println("\nkey and Byte x0~x3 after initial XOR are");
            System.out.println("key0=" + getRoundKey(0) + " x0=" + intToHexString(x0));
            System.out.println("key1=" + getRoundKey(1) + " x1=" + intToHexString(x1));
            System.out.println("key2=" + getRoundKey(2) + " x2=" + intToHexString(x2));
            System.out.println("key3=" + getRoundKey(3) + " x3=" + intToHexString(x3));
        }

        // The round key (K0~K7) are not in the Feistel network so we start here from 8
        int k = 8;
        for (int R = 0; R < ROUNDS; R += 2) { // R = round number; default = 16
            // Round odd
            /** Figure 1. top output of functionG */
            int t0 = functionG(x0);
            /** Figure 1. bottom output of functionG */
            int t1 = functionG(Integer.rotateLeft(x1, 8));
            // PHT and add round key
            x2 ^= t0 + t1 + getRoundKey(k++); // do PHT, add round key, then XOR into x2
            x2 = Integer.rotateRight(x2, 1); // right shift x2 1 bit after XOR. Thus, ready x2 for next round
            x3 = Integer.rotateLeft(x3, 1); // left shift x3 1 bit before XOR
            x3 ^= t0 + t1 + t1 + getRoundKey(k++); // do PHT, add round key, then XOR into x3. Thus, ready x3 for next
            // round
            // Note above that in Figure 1. PHT box, bottom functionG output (t1) is added
            // to top, and then brought back after addition with t0. Therefore, t1 was
            // summed twice at bottom in PHT

            if (DEBUG) {
                System.out.println("\nRound " + (R + 1) + " ended");
                System.out.println(
                        "key" + (k - 2) + "=" + intToHexString(getRoundKey(k - 2)) + " x2=" + intToHexString(x2));
                System.out.println(
                        "key" + (k - 1) + "=" + intToHexString(getRoundKey(k - 1)) + " x3=" + intToHexString(x3));
            }

            // Round even
            // identical procedure, just swap x0<>x2, x1<>x3
            t0 = functionG(x2);
            t1 = functionG(Integer.rotateLeft(x3, 8));
            x0 ^= t0 + t1 + getRoundKey(k++);
            x0 = Integer.rotateRight(x0, 1);
            x1 = Integer.rotateLeft(x1, 1);
            x1 ^= t0 + t1 + t1 + getRoundKey(k++);

            if (DEBUG) {
                System.out.println("\nRound " + (R + 2) + " ended");
                System.out.println(
                        "key" + (k - 2) + "=" + intToHexString(getRoundKey(k - 2)) + " x0=" + intToHexString(x0));
                System.out.println(
                        "key" + (k - 1) + "=" + intToHexString(getRoundKey(k - 1)) + " x1=" + intToHexString(x1));
            }
        }

        // the final round key (K4~K7) addition
        x2 ^= getRoundKey(4);
        x3 ^= getRoundKey(5);
        x0 ^= getRoundKey(6);
        x1 ^= getRoundKey(7);

        byte[] result = new byte[]{(byte) x2, (byte) (x2 >>> 8), (byte) (x2 >>> 16), (byte) (x2 >>> 24), (byte) x3,
                (byte) (x3 >>> 8), (byte) (x3 >>> 16), (byte) (x3 >>> 24), (byte) x0, (byte) (x0 >>> 8),
                (byte) (x0 >>> 16), (byte) (x0 >>> 24), (byte) x1, (byte) (x1 >>> 8), (byte) (x1 >>> 16),
                (byte) (x1 >>> 24)};

        System.out.println("---END encryption---");
        return result;
    }

    /**
     * It's similar to encrypt. See encrypt for better comments
     *
     * @param cipherText
     * @param keyBytes
     * @return
     */
    public static byte[] decrypt(byte[] cipherText, byte[] keyBytes) {
        System.out.println("---START decryption---");
        byte[] ct = cipherText;
        if (DEBUG) {
            System.out.println("cipherText to begin with (in bytes[])=" + byteArrayToHexString(ct));
        }
        int i = 0;
        // Below: the "& 0xFF" just to get byte value right in java
        // and input them to four 32 bit long bits respectively
        int x0 = ct[i++] & 0xFF | (ct[i++] & 0xFF) << 8 | (ct[i++] & 0xFF) << 16 | (ct[i++] & 0xFF) << 24;
        int x1 = ct[i++] & 0xFF | (ct[i++] & 0xff) << 8 | (ct[i++] & 0xFF) << 16 | (ct[i++] & 0xFF) << 24;
        int x2 = ct[i++] & 0xFF | (ct[i++] & 0xFF) << 8 | (ct[i++] & 0xFF) << 16 | (ct[i++] & 0xFF) << 24;
        int x3 = ct[i++] & 0xFF | (ct[i++] & 0xFF) << 8 | (ct[i++] & 0xFF) << 16 | (ct[i++] & 0xFF) << 24;
        if (DEBUG) {
            System.out.println("Byte x0~x3 before XOR key=");
            System.out.println(intToHexString(x0));
            System.out.println(intToHexString(x1));
            System.out.println(intToHexString(x2));
            System.out.println(intToHexString(x3));
        }

        // swap
        x0 ^= getRoundKey(4);
        x1 ^= getRoundKey(5);
        x2 ^= getRoundKey(6);
        x3 ^= getRoundKey(7);

        if (DEBUG) {
            System.out.println("Byte x0~x3 after XOR key=");
            System.out.println("key0=" + getRoundKey(0) + " x0=" + intToHexString(x0));
            System.out.println("key1=" + getRoundKey(1) + " x1=" + intToHexString(x1));
            System.out.println("key2=" + getRoundKey(2) + " x2=" + intToHexString(x2));
            System.out.println("key3=" + getRoundKey(3) + " x3=" + intToHexString(x3));
        }

        // The round key (K0~K7) are not in the Feistel network so we start here from 8
        int k = 8 + 2 * ROUNDS - 1;
        for (int R = 0; R < ROUNDS; R += 2) { // R = round number; default = 16
            // Round odd
            int t0 = functionG(x0);
            int t1 = functionG(Integer.rotateLeft(x1, 8));
            // PHT and add round key
            x3 ^= t0 + t1 + t1 + getRoundKey(k--);
            x3 = Integer.rotateRight(x3, 1);
            x2 = Integer.rotateLeft(x2, 1);
            x2 ^= t0 + t1 + getRoundKey(k--);

            // Round even
            // identical procedure, just swap x0<>x2, x1<>x3
            t0 = functionG(x2);
            t1 = functionG(Integer.rotateLeft(x3, 8));

            x1 ^= t0 + t1 + t1 + getRoundKey(k--);
            x1 = Integer.rotateRight(x1, 1);
            x0 = Integer.rotateLeft(x0, 1);
            x0 ^= t0 + t1 + getRoundKey(k--);
        }
        // the final round key (K4~K7) addition
        x2 ^= getRoundKey(0);
        x3 ^= getRoundKey(1);
        x0 ^= getRoundKey(2);
        x1 ^= getRoundKey(3);

        byte[] result = new byte[]{(byte) x2, (byte) (x2 >>> 8), (byte) (x2 >>> 16), (byte) (x2 >>> 24), (byte) x3,
                (byte) (x3 >>> 8), (byte) (x3 >>> 16), (byte) (x3 >>> 24), (byte) x0, (byte) (x0 >>> 8),
                (byte) (x0 >>> 16), (byte) (x0 >>> 24), (byte) x1, (byte) (x1 >>> 8), (byte) (x1 >>> 16),
                (byte) (x1 >>> 24)};

        System.out.println("---END decryption---");
        return result;
    }

    public static final int functionG(int x) {
        x = Math.floorMod(x, S_BOX.length);
        return S_BOX[x];
    }

    /**
     * obtain round key
     *
     * @param key equals to K-subscript in Figure 1
     * @return an int which length is 4 bytes
     */
    public static int getRoundKey(int key) {
        return ROUND_KEY[key];
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * get human-readable hex string from a byte array
     *
     * @param ba byte array
     * @return hex string
     */
    public static String byteArrayToHexString(byte[] ba) {
        char[] hexChars = new char[ba.length * 2];
        for (int j = 0; j < ba.length; j++) {
            int v = ba[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * get human-readable hex string from a 32 bit integer
     *
     * @param i 32 bit int
     * @return hex string
     */
    public static String intToHexString(int i) {
        return Integer.toHexString(i);
    }
}