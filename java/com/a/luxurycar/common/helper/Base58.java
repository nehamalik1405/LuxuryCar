package com.a.luxurycar.common.helper;

import java.math.BigInteger;

public class Base58 {

    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger BASE = BigInteger.valueOf(58);

    public static String encode(byte[] input) {
        // TODO: This could be a lot more efficient.
        BigInteger bi = new BigInteger(1, input);
        StringBuffer s = new StringBuffer();
        while (bi.compareTo(BASE) >= 0) {
            BigInteger mod = bi.mod(BASE);
            s.insert(0, ALPHABET.charAt(mod.intValue()));
            bi = bi.subtract(mod).divide(BASE);
        }
        s.insert(0, ALPHABET.charAt(bi.intValue()));
        // Convert leading zeros too.
        for (byte anInput : input) {
            if (anInput == 0)
                s.insert(0, ALPHABET.charAt(0));
            else
                break;
        }
        return s.toString();
    }

    public static byte[] decode(String input) {
        return decodeToBigInteger(input).toByteArray();
    }

    public static BigInteger decodeToBigInteger(String input) {
        BigInteger bi = BigInteger.valueOf(0);
        // Work backwards through the string.
        for (int i = input.length() - 1; i >= 0; i--) {
            int alphaIndex = ALPHABET.indexOf(input.charAt(i));
            bi = bi.add(BigInteger.valueOf(alphaIndex).multiply(BASE.pow(input.length() - 1 - i)));
        }
        return bi;
    }

}
