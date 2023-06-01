package com.blackbox.dashmesh.ui.utils;

public class HexCoder {

    private final static String HEX = "0123456789ABCDEF";

    /**
     *
     * @param txt The {@link String} that you want converted to a Hex String.
     * @return Hex String
     */
    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    /**
     *
     * @param hex The Hex {@link String} that you want converted to plain text.
     * @return Plain String
     */
    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    /**
     *
     * @param hexString
     * @return
     */
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    /**
     *
     * @param buf
     * @return
     */
    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

}
