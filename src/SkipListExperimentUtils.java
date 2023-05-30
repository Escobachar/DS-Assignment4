import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SkipListExperimentUtils {


    public static double measureLevels(double p, int x) {
        AbstractSkipList list = new IndexableSkipList(p);
        double sum = 0;
        for (int i = 0; i < x; i++) {
            int h = list.generateHeight();
            sum += h;
        }
        return (double) (sum/x)+1;
    }

    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */
    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        AbstractSkipList DS = new IndexableSkipList(size+1);
        Integer[] arr = new Integer[size+1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 2*i;
        }
        //shuffle the array

        List<Integer> intList = Arrays.asList(arr);
        Collections.shuffle(intList);
        intList.toArray(arr);

        double begin = System.nanoTime();

        //insertion
        for (int x:arr) {
            DS.insert(x);
        }
        double end = System.nanoTime();
        //calculating the time
        double seconds = end-begin;
        return new Pair<>(DS,seconds/(size+1));
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        Integer[] arr = new Integer[2*size+1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        //shuffle the array

        List<Integer> intList = Arrays.asList(arr);
        Collections.shuffle(intList);
        intList.toArray(arr);

        double begin = System.nanoTime();

        //search
        for (int x:arr) {
            skipList.search(x);
        }
        double end = System.nanoTime();
        //calculating the time
        double seconds = end-begin;
        double avg = seconds/(2*size+1);
        return avg;
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        Integer[] arr = new Integer[size+1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 2*i;
        }
        AbstractSkipList.Node[] arrPointers = new IndexableSkipList.Node[size+1];
        for (int i = 0; i < arr.length; i++) {
            arrPointers[i] = skipList.search(arr[i]);
        }

        //shuffle the array

        List<Integer> intList = Arrays.asList(arr);
        Collections.shuffle(intList);
        intList.toArray(arr);

        double begin = System.nanoTime();


        //Deletion
        for (int i = 0; i < arr.length; i++) {
            skipList.delete(arrPointers[i]);
        }
        double end = System.nanoTime();
        //calculating the time
        double seconds = end-begin;
        double avg = seconds/(2*size+1);
        return avg;
    }

    public static void main(String[] args) {
        //        test2:

        int size = 2500;
        double p = 0.33;
        double InsertionTime=0, SearchTime=0, DeletionTime=0;
        for (int n = 0; n <30; n++) {
            Pair pair = measureInsertions(p,size);
            AbstractSkipList skipList = (IndexableSkipList)pair.first();
            //System.out.println(skipList.toString());
            double AverageInsertionTime = (double)pair.second();
            double AverageSearchTime = measureSearch(skipList,size);
            double AverageDeletionTime = measureDeletions(skipList,size);
            //System.out.println(skipList.toString());
            InsertionTime+=AverageInsertionTime;
            SearchTime+=AverageSearchTime;
            DeletionTime+=AverageDeletionTime;
        }
        InsertionTime/=30;
        SearchTime/=30;
        DeletionTime/=30;
        System.out.println("InsertionTime: " + InsertionTime +
                "\nSearchTime: " + SearchTime +
                "\nDeletionTime: " + DeletionTime);


        //        test1:
        /*
        int[] x = {10,100,1000,10000};
        double[] p = {0.33,0.5,0.75,0.9};
            for (int i = 0; i < x.length ; i++) {
                for (int j = 0; j < p.length; j++) {
                    double avg = measureLevels(p[j],x[i]);
                    int expected = (int)(1/p[j]);
                    System.out.println("x = " + x[i] + " p = " + p[j] +
                            " average levels: " + avg +
                            " expected: " + expected +
                            " delta: " + Math.abs(avg-expected));
                }
                System.out.println();
            }
         */
    }
}
