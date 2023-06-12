import java.util.List;
import java.util.ArrayList;

public class MyDataStructure {
    /*
     * You may add any members that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low, high).
     */
    private final int N;
    private ChainedHashTable table;
    private IndexableSkipList skipList;
    private HashFactory<Integer> factory;

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items expected in the DS.
     */
    public MyDataStructure(int N) {
        this.N = N;
        this.factory = new ModularHash();
        this.table = new ChainedHashTable(factory);
        this.skipList = new IndexableSkipList(0.5);
    }
    /*
     * In the following functions,
     * you should REMOVE the place-holder return statements.
     */
    public boolean insert(int value) {
        if(skipList.insert(value) == null)
            return false;
        table.insert(value, value);
        return true;
    }

    public boolean delete(int value) {
        if(table.delete(value)){
            skipList.delete(skipList.find(value));
            return true;
        }
        return false;
    }

    public boolean contains(int value) {
        return table.search(value) != null;
    }

    public int rank(int value) {
        return skipList.rank(value);
    }

    public int select(int index) {
        return skipList.select(index);
    }

    public List<Integer> range(int low, int high) {

    }
}
