package week01.util;

import java.util.Arrays;

public class ArrayFormatPrint {
    public static void print(String[] formats, int[][] arrays){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < formats.length; i++) {
            result.append(String.format(formats[i], Arrays.toString(arrays[i])));
        }
        System.out.println(result);
    }
}
