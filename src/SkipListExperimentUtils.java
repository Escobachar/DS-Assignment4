public class SkipListExperimentUtils {

    public static double measureLevels(double p, int x) {
        AbstractSkipList list = new IndexableSkipList(p);
        int sum = 0;
        for (int i = 0; i < x; i++) {
            sum += list.generateHeight();
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
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static void main(String[] args) {
        int[] x = {10,100,1000,10000};
        double[] p = {0.33, 0.5, 0.75, 0.9};
        for (int i = 0; i < x.length ; i++) {
            for (int j = 0; j < p.length; j++) {
                System.out.println("x = " + x[i] + " p = " + p[j] + " average levels: " + measureLevels(p[j],x[i]) + " expected: " + (int)(1/p[j]));
            }
        }
    }
}
