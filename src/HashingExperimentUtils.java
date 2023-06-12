import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HashingExperimentUtils {
    final private static int k = 16;

    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
        HashFactory<Integer> factory = new ModularHash();
        HashFunctor<Integer> functor = factory.pickHash(k);
        HashingUtils utils = new HashingUtils();
        ChainedHashTable<Integer,Integer> table = new ChainedHashTable<>(factory, k, maxLoadFactor);
        int m = (int) Math.pow(2,16);
        Integer[] rnd = utils.genUniqueIntegers((int)(m*2)); //first half will be used for insertion, the others will be used for failed searches
        long begin = System.nanoTime();
        for (int i = 0; i < (m*maxLoadFactor)/2; i++) {
            table.insert(rnd[i],rnd[i]);
        }
        long end = System.nanoTime();
        Double avgInsertion = (end-begin)/((m*maxLoadFactor)-1);
        begin = System.nanoTime();
        for (int i = (int)(m*maxLoadFactor)-1; i >= (m*maxLoadFactor)/2; i--) { //failed search
            table.search(rnd[i]);
        }
        for (int i = 0; i < (m*maxLoadFactor)/2 ; i++) { //successful search
            table.search(rnd[i]);
        }
        end = System.nanoTime();
        Double avgSearch = (end-begin)/((m*maxLoadFactor)-1);
        return new Pair<>(avgInsertion,avgSearch);
    }

    public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
        HashFactory<Integer> factory = new ModularHash();
        HashFunctor<Integer> functor = factory.pickHash(k);
        HashingUtils utils = new HashingUtils();
        ProbingHashTable<Integer,Integer> table = new ProbingHashTable<>(factory, k, maxLoadFactor);
        int m = (int) Math.pow(2,16);
        Integer[] rnd = utils.genUniqueIntegers((int)(m*2)); //first half will be used for insertion, the others will be used for failed searches
        long begin = System.nanoTime();
        for (int i = 0; i < (m*maxLoadFactor)/2; i++) {
            table.insert(rnd[i],rnd[i]);
        }
        long end = System.nanoTime();
        Double avgInsertion = (end-begin)/((m*maxLoadFactor)-1);
        begin = System.nanoTime();
        for (int i = (int)(m*maxLoadFactor)-1; i >= (m*maxLoadFactor)/2; i--) { //failed search
            table.search(rnd[i]);
        }
        for (int i = 0; i < (m*maxLoadFactor)/2 ; i++) { //successful search
            table.search(rnd[i]);
        }
        end = System.nanoTime();
        Double avgSearch = (end-begin)/((m*maxLoadFactor)-1);
        return new Pair<>(avgInsertion,avgSearch);
    }

    public static Pair<Double, Double> measureLongOperations() {
        double maxLoadFactor = 1;
        HashFactory<Long> factory = new MultiplicativeShiftingHash();
        HashFunctor<Long> functor = factory.pickHash(k);
        HashingUtils utils = new HashingUtils();
        ChainedHashTable<Long,Long> table = new ChainedHashTable<>(factory, k, maxLoadFactor);
        int m = (int) Math.pow(2,16);
        Long[] rnd = utils.genUniqueLong((int)(m*2)); //first half will be used for insertion, the others will be used for failed searches
        long begin = System.nanoTime();
        for (int i = 0; i < (m*maxLoadFactor)/2; i++) {
            table.insert(rnd[i],rnd[i]);
        }
        long end = System.nanoTime();
        Double avgInsertion = (end-begin)/((m*maxLoadFactor)-1);
        begin = System.nanoTime();
        for (int i = (int)(m*maxLoadFactor)-1; i >= (m*maxLoadFactor)/2; i--) { //failed search
            table.search(rnd[i]);
        }
        for (int i = 0; i < (m*maxLoadFactor)/2 ; i++) { //successful search
            table.search(rnd[i]);
        }
        end = System.nanoTime();
        Double avgSearch = (end-begin)/((m*maxLoadFactor)-1);
        return new Pair<>(avgInsertion,avgSearch);
    }

    public static Pair<Double, Double> measureStringOperations() {
        double maxLoadFactor = 1;
        HashFactory<String> factory = new StringHash();
        HashFunctor<String> functor = factory.pickHash(k);
        HashingUtils utils = new HashingUtils();
        ChainedHashTable<String,String> table = new ChainedHashTable<>(factory, k, maxLoadFactor);
        int m = (int) Math.pow(2,16);
        List<String> rnd = utils.genUniqueStrings((int)(m*2),10,20); //first half will be used for insertion, the others will be used for failed searches
        long begin = System.nanoTime();
        for (int i = 0; i < (m*maxLoadFactor)/2; i++) {
            table.insert(rnd.get(i),rnd.get(i));
        }
        long end = System.nanoTime();
        Double avgInsertion = (end-begin)/((m*maxLoadFactor)-1);
        begin = System.nanoTime();
        for (int i = (int)(m*maxLoadFactor)-1; i >= (m*maxLoadFactor)/2; i--) { //failed search
            table.search(rnd.get(i));
        }
        for (int i = 0; i < (m*maxLoadFactor)/2 ; i++) { //successful search
            table.search(rnd.get(i));
        }
        end = System.nanoTime();
        Double avgSearch = (end-begin)/((m*maxLoadFactor)-1);
        return new Pair<>(avgInsertion,avgSearch);
    }

    public static void main(String[] args) {
        System.out.println("this checker was written by the Legendary Tomer Cohen.");
        System.out.println("what do you want to check?");
        System.out.println("1. probing");
        System.out.println("2. chaining");
        System.out.println("3. long and string");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input == 1) {
            double[] a = {0.5, 0.75, 7.0 / 8.0, 15.0 / 16.0};
            double[] insertionsTimes = new double[4];
            double[] searchesTimes = new double[4];
            for (int i = 0; i < 4; i++) {
                System.out.println("checking a=" + a[i]);
                double averageInsertion = 0;
                double averageSearch = 0;
                for (int j = 0; j < 30; j++) {
                    System.out.println("check #" + (j + 1));
                    Pair<Double, Double> pa = measureOperationsProbing(a[i]);
                    averageInsertion += pa.first();
                    averageSearch += pa.second();
                }
                insertionsTimes[i] = averageInsertion / 30;
                searchesTimes[i] = averageSearch / 30;
            }
            Printer(insertionsTimes, searchesTimes, a, "Linear Probing");

        }
        if (input == 2) {
            double[] a = {0.5, 0.75, 1, 3.0 / 2.0, 2};
            double[] insertionsTimes = new double[5];
            double[] searchesTimes = new double[5];
            for (int i = 0; i < 5; i++) {
                System.out.println("checking a=" + a[i]);
                double averageInsertion = 0;
                double averageSearch = 0;
                for (int j = 0; j < 30; j++) {
                    System.out.println("check #" + (j + 1));
                    Pair<Double, Double> pa = measureOperationsChained(a[i]);
                    averageInsertion += pa.first();
                    averageSearch += pa.second();
                }
                insertionsTimes[i] = averageInsertion / 30;
                searchesTimes[i] = averageSearch / 30;
            }
            Printer(insertionsTimes, searchesTimes, a, "Chaining");
        }
        if (input == 3) {
            System.out.println("checking long");
            double[] insertionsTimes = new double[2];
            double[] searchesTimes = new double[2];
            double averageInsertion = 0;
            double averageSearch = 0;
            for (int j = 0; j < 10; j++) {
                System.out.println("check #" + (j + 1));
                Pair<Double, Double> pa = measureLongOperations();
                averageInsertion += pa.first();
                averageSearch += pa.second();
            }
            insertionsTimes[0] = averageInsertion / 10;
            searchesTimes[0] = averageSearch / 10;

            System.out.println("checking String");
            averageInsertion = 0;
            averageSearch = 0;
            for (int j = 0; j < 10; j++) {
                System.out.println("check #" + (j + 1));
                Pair<Double, Double> pa = measureStringOperations();
                averageInsertion += pa.first();
                averageSearch += pa.second();
            }
            insertionsTimes[1] = averageInsertion / 10;
            searchesTimes[1] = averageSearch / 10;


            Printer2(insertionsTimes, searchesTimes);
        }

    }



    private static void Printer(double[] insert,double[] search,double[] a,String name){
        System.out.println("                                                                        "+name);
        for(int i=0;i<insert.length;i++) {
            System.out.println("|-------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|   a="+a[i]+"       |    Average Insertion="+insert[i]+"     |   Average Search="+search[i]);
        }
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------|");
    }
    private static void Printer2(double[] insert,double[] search){
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|   Long       |    Average Insertion="+insert[0]+"     |   Average Search="+search[0]);
        System.out.println("|-------------------------------------------------------------------------------------------------------------------------|");
        System.out.println("|   String       |    Average Insertion="+insert[1]+"     |   Average Search="+search[1]);

        System.out.println("|-------------------------------------------------------------------------------------------------------------------------|");
    }
}

