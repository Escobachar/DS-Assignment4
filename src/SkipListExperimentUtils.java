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
        AbstractSkipList DS = new IndexableSkipList(p);
        Integer[] arr = new Integer[size+1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 2*i;
        }
        //shuffle the array

        List<Integer> intList = Arrays.asList(arr);
        Collections.shuffle(intList);
        intList.toArray(arr);
        double totalTime = 0.0;

        //insertion
        for (int x:arr) {
            double startTime = System.nanoTime();
            DS.insert(x);
            double endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        //calculating the time
        double avgTime = totalTime / arr.length;
        return new Pair<>(DS,avgTime);
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
        double totalTime = 0.0;

        //search
        for (int x:arr) {
            double startTime = System.nanoTime();
            skipList.search(x);
            double endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        //calculating the time;
        double avgTime = totalTime/arr.length;
        return avgTime;
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        Integer[] arr = new Integer[size+1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 2*i;
        }

        //shuffle the array

        List<Integer> intList = Arrays.asList(arr);
        Collections.shuffle(intList);
        intList.toArray(arr);

        double totalTime = 0.0;

        //Deletion
        for (int i = 0 ; i < arr.length ; i++)
        {
            AbstractSkipList.Node toDelete = skipList.find(arr[i]);
            double startTime = System.nanoTime();
            skipList.delete(toDelete);
            double endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        //calculating the time;
        double avgTime = totalTime/arr.length;
        return avgTime;
    }



}
