package com.huhuo.integration.util;

/**
 * Title      : convert tools of common Description: convert sequence from BIG_ENGIAN to LITTLE_ENDIAN
 * Company    : Century Comm Tech.CO.,TLD
 *
 * @author : lzb
 * @version : v1.0 Date	      : 2003-5-14
 */
public class Convert {
    public static final int MIN_VALUE_To_4 = 1;
    public static final int MAX_VALUE_To_4 = 9999;
    public static final byte[] Hex_value = {
        0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
        0x38, 0x39, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66};

    public static String IntToString4(int i) {
        if (i <= MIN_VALUE_To_4) {
            return "0001";
        } else if (i > MAX_VALUE_To_4) {
            return "9999";
        }
        switch (Integer.toString(i).length()) {
            case 1:
                return "000" + Integer.toString(i);
            case 2:
                return "00" + Integer.toString(i);
            case 3:
                return "0" + Integer.toString(i);

        }
        return Integer.toString(i);
    }

    public static byte[] byteToHex(byte b1) {
        byte[] bTo = new byte[2];
        bTo[0] = 0x00;
        bTo[1] = 0x00;
        bTo[0] = Hex_value[(byte) ((b1 >> 4) & 0x0f)];
        bTo[1] = Hex_value[(byte) (b1 & 0x0f)];
        return bTo;
    }

    /**
     * convert BIG_ENGIAN(int) To LITTLE_ENDIAN(int)
     *
     * @return LITTLE_ENDIAN(int)
     */
    public static int WinToJava(int WindowInt) {
        int int1 = (int) (WindowInt >> 24 & 0x000000FF);
        int int2 = (int) (WindowInt >> 16 & 0x000000FF);
        int int3 = (int) (WindowInt >> 8 & 0x000000FF);
        int int4 = (int) (WindowInt >> 0 & 0x000000FF);
        return (int4 << 24) + (int3 << 16) + (int2 << 8) + (int1 << 0);
    }

    /**
     * convert  LITTLE_ENDIAN(int) to BIG_ENGIAN(int)
     *
     * @return BIG_ENGIAN(int)
     */
    public static int JavaToWin(int JavaInt) {
        int int1 = (int) (JavaInt >> 24 & 0x000000FF);
        int int2 = (int) (JavaInt >> 16 & 0x000000FF);
        int int3 = (int) (JavaInt >> 8 & 0x000000FF);
        int int4 = (int) (JavaInt >> 0 & 0x000000FF);
        return (int4 << 24) + (int3 << 16) + (int2 << 8) + (int1 << 0);
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
}