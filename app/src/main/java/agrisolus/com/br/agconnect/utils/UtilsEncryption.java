/**
 *
 */
package agrisolus.com.br.agconnect.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author EGILCRU
 */
@SuppressLint("NewApi")
public class UtilsEncryption {

    public static String encryptMD5(String value) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(value.getBytes(), 0, value.length());
            String hash = new BigInteger(1, digest.digest()).toString(16);
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decryptMD5(String value) {
        return null;
    }

    public static String encryptBase64(String value) {
        return Base64.encodeToString(value.getBytes(), Base64.NO_WRAP);
    }

    public static String decryptBase64(String value) {
        return new String(Base64.decode(value.getBytes(), Base64.NO_WRAP));
    }

    /**
     * Converter String para Hexadecimal
     *
     * @param arg
     * @return String
     */
    public static String toHex(String arg) throws Exception{
        return new BigInteger(1, arg.getBytes("UTF-8")).toString(16);

        //return String.format("%x", new BigInteger(1, arg.getBytes(Charset.defaultCharset())));
        /*StringBuilder buf = new StringBuilder(200);
        for (char ch : arg.toCharArray()) {
            if (buf.length() > 0)
                buf.append(' ');
            buf.append(String.format("%04x", (int) ch));
        }
        return buf.toString();*/
    }

}
