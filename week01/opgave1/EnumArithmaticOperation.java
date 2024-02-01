package week01.opgave1;

public enum EnumArithmaticOperation implements ArithmeticOperation {
    addition((a,b)->a+b),
    subtraction((a,b)->a-b),
    multiplication((a, b)->a*b),
    division((a,b)->a/b),
    modulus((a,b)->a%b),
    power((a,b)->{
        int result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    });

    private final ArithmeticOperation operation;
    EnumArithmaticOperation(ArithmeticOperation a){
        this.operation = a;
    }

    @Override
    public int perform(int a, int b) {
        return operation.perform(a,b);
    }
}
