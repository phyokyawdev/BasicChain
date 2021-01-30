package utils;

import org.bouncycastle.jcajce.provider.digest.Keccak;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;

public class StringUtil {
    public static byte[] applyKeccak(byte[] input) {
        Keccak.DigestKeccak digestKeccak = new Keccak.Digest256();
        return digestKeccak.digest(input);
    }

    public static byte[] hexToBytes(String hex) {
        return DatatypeConverter.parseHexBinary(hex);
    }

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static byte[] stringToBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static String bytesToString(byte[] bytes) {
        return new String(bytes);
    }
}