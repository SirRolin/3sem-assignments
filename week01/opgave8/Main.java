package week01.opgave8;

public class Main {

    public static void main(String[] args) {
        int errorCount = 0;


        DataStorage<Float> floaty = new MemoryStorage<>();
        float firstfloat = 2f;
        String first = floaty.store(firstfloat);
        String second = floaty.store(firstfloat + 5);

        //// They should NOT return the same value.
        if(floaty.retrieve(first).equals(floaty.retrieve(second))){
            System.out.println("MemoryStorage Returns wrong value.");
            System.out.println(floaty.retrieve(first));
            System.out.println(floaty.retrieve(second));
            errorCount++;
        }

        DataStorage<String> stringy = new FileStorage<>();
        String firstSource = stringy.store(first);
        String firstAfterFileRetrieval = stringy.retrieve(firstSource);
        //// making sure that what we put in also is what comes out.
        if(!firstAfterFileRetrieval.equals(first)){
            System.out.println("FileStorage Returns wrong value.");
            System.out.println(firstAfterFileRetrieval + " expected: " + first);
            errorCount++;
        }


        floaty.delete(first);
        floaty.delete(second);
        stringy.delete(firstAfterFileRetrieval);


        if(errorCount > 0){
            System.out.println(errorCount + " Errors occurred");
        }
    }

}
