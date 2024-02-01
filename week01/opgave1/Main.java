package week01.opgave1;

import java.util.Arrays;

import static java.lang.String.*;

public class Main {
    public static void main(String[] args) {
        int a = 4;
        int b = 2;
        System.out.printf("a is: %d & b is: %d adding them gets: %d%n", a, b, operate(a, b, EnumArithmaticOperation.addition));
        System.out.printf("a is: %d & b is: %d subtracting them gets: %d%n", a, b, operate(a, b, EnumArithmaticOperation.subtraction));
        System.out.printf("a is: %d & b is: %d multiplying them gets: %d%n", a, b, operate(a, b, EnumArithmaticOperation.multiplication));
        System.out.printf("a is: %d & b is: %d dividing them gets: %d%n", a, b, operate(a, b, EnumArithmaticOperation.division));
        System.out.printf("a is: %d & b is: %d module them gets: %d%n", a, b, operate(a, b, EnumArithmaticOperation.modulus));
        System.out.printf("a is: %d & b is: %d powering them gets: %d%n", a, b, operate(a, b, EnumArithmaticOperation.power));

        int[] aarray = {2, 4, 6, 8};
        int[] barray = {1, 2, 3, 4};
        System.out.printf("a is: %s & b is: %s adding them gets: %s%n", Arrays.toString(aarray), Arrays.toString(barray), Arrays.toString(operate(aarray, barray, EnumArithmaticOperation.addition)));
        System.out.printf("a is: %s & b is: %s subtracting them gets: %s%n", Arrays.toString(aarray), Arrays.toString(barray), Arrays.toString(operate(aarray, barray, EnumArithmaticOperation.subtraction)));
        System.out.printf("a is: %s & b is: %s multiplying them gets: %s%n", Arrays.toString(aarray), Arrays.toString(barray), Arrays.toString(operate(aarray, barray, EnumArithmaticOperation.multiplication)));
        System.out.printf("a is: %s & b is: %s dividing them gets: %s%n", Arrays.toString(aarray), Arrays.toString(barray), Arrays.toString(operate(aarray, barray, EnumArithmaticOperation.division)));
        System.out.printf("a is: %s & b is: %s modulus them gets: %s%n", Arrays.toString(aarray), Arrays.toString(barray), Arrays.toString(operate(aarray, barray, EnumArithmaticOperation.modulus)));
        System.out.printf("a is: %s & b is: %s power them gets: %s%n", Arrays.toString(aarray), Arrays.toString(barray), Arrays.toString(operate(aarray, barray, EnumArithmaticOperation.power)));
    }

    public static int operate(int a, int b, ArithmeticOperation op){
        return op.perform(a,b);
    }
    public static int[] operate(int[] a, int[] b, ArithmeticOperation op){
        int length = Math.min(a.length, b.length);
        int[] result = new int[length];
        for (int i = 0; i < length; i++) {
            result[i] = operate(a[i], b[i], op);
        }
        return result;
    }
}