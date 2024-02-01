package week01.opgave5;

import week01.util.ArrayFormatPrint;

public class Main {

    public static void main(String[] args) {
        int[] intArray = {1,2,3,4,5,6,7,8,9,1337};
        ArrayFormatPrint.print(
                /* what each array should be formatted with before print */
                new String[]{"Mapper %s ", "gives %s"},
                /* arrays wanted to be printed */
                new int[][]{
                        intArray,
                        week01.opgave2.Main.map(intArray, Main::doubleValue)
                });
    }

    public static int doubleValue(int x){
        return x<<1; //// this doubles the Integer, also called shift right, but can't be done to non-int variables.
        //return x*2; //// included this in case you'd rather see this.
    }
}
