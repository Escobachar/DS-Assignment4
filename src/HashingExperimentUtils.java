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
    }
}

