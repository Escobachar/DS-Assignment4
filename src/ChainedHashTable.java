import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;

    private LinkedList<Pair<K,V>>[] arr;
    private int size;
    private int k;

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.arr = new LinkedList[capacity];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new LinkedList<>();
        }
        this.size = 0;
        this.k = k;
    }

    public V search(K key) {
        int index = hashFunc.hash(key);
        Iterator iter = arr[index].iterator();
        while(iter.hasNext()){
            Pair temp = (Pair)iter.next();
            if(temp.first() == key)
                return (V)temp.second();
        }
        return null;
    }

    private Pair search(K key, int diff){ //diff is not used, just for a different signature for compilation
        int index = hashFunc.hash(key);
        Iterator iter = arr[index].iterator();
        while(iter.hasNext()){
            Pair temp = (Pair)iter.next();
            if(temp.first() == key)
                return temp;
        }
        return null;
    }

    public void insert(K key, V value) {
        if((size+1)/capacity < maxLoadFactor) {
            arr[hashFunc.hash(key)].add(new Pair(key, value));
            size++;
        }
        else{
            rehash(key, value);
        }

    }
    private void insert(Pair pair){
        if((size+1)/capacity < maxLoadFactor) {
            arr[hashFunc.hash((K)pair.first())].add(new Pair(pair.first(), pair.second()));
            size++;
        }
        else{
            rehash((K)pair.first(), (V)pair.second());
        }
    }

    private void rehash(K key, V value){
        LinkedList<Pair<K,V>>[] temp = arr;
        arr = new LinkedList[capacity * 2];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new LinkedList<>();
        }
        this.capacity = capacity * 2;
        this.hashFunc = hashFactory.pickHash(k);
        for (LinkedList<Pair<K,V>> pairs : temp) {
            for (Pair pair : pairs) {
                arr[hashFunc.hash((K) pair.first())].add(pair);
            }
        }
    }

    public boolean delete(K key) {
        Pair temp = search(key,0);
        if(temp != null)
            return arr[hashFunc.hash(key)].remove(temp);
        return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
