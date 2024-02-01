package week01.opgave2;

import week01.util.ArrayFormatPrint;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] intArray = {1,2,3,4,5,6,7,8,9,1337};
        ArrayFormatPrint.print(new String[]{"Mapper %s ", "gives %s"}, new int[][]{intArray, map(intArray, x -> x * 2)});
        ArrayFormatPrint.print(new String[]{"Filter %s ", "gives %s"}, new int[][]{intArray, filter(intArray, a -> a%3==0)});
    }

    public static int[] map(int[] a, MyTransformingType trans){
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = trans.transform(a[i]);
        }
        return result;
    }

    public static int[] filter(int[] a, MyValidatingType validation){
        int[] endResult = new int[a.length];
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            if(validation.validate(a[i])){
                endResult[count] = a[i];
                count++;
            }
        }
        return Arrays.copyOf(endResult, count);
    }
}
