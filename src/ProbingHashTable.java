import java.lang.constant.Constable;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Pair<K,V>[] arr;
    private int size;
    private int k;

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.arr = new Pair[capacity];
        this.size = 0;
        this.k = k;
    }

    public V search(K key) {
        int slot = hashFunc.hash(key);
        for (int i = 0; i < arr.length; i++) {
            if(arr[slot] == null){
                return null;
            }
            else if (arr[slot].first() == key) {
                return arr[slot].second();
            }
            else{
                slot = HashingUtils.mod(slot +1, capacity);
            }
        }
        return null;
    }
    private int searchIndex(K key) {
        int slot = hashFunc.hash(key);
        for (int i = 0; i < arr.length; i++) {
            if (arr[slot] == null) {
                return -1;
            }
            else if (arr[slot].first() == key) {
                return slot;
            }
            else {
                slot = HashingUtils.mod(slot + 1, capacity);
            }
        }
        return -1;
    }

    public void insert(K key, V value) {
        if((size+1)/capacity < maxLoadFactor){
            int slot = hashFunc.hash(key);
            for (int i = 0; i < arr.length; i++) {
                if(arr[slot] == null){
                    arr[slot] = new Pair<>(key,value);
                    size++;
                    return;
                }
                else{
                    slot = HashingUtils.mod(slot +1, capacity);
                }
            }
        }
        else{
            rehash(key, value);
        }
    }

    private void rehash(K key, V value){
        Pair<K,V>[] temp = arr;
        this.arr = new Pair[capacity * 2];
        this.capacity = capacity * 2;
        this.hashFunc = hashFactory.pickHash(k);
        for (Pair<K,V> pair : temp) {
            insert(pair.first(),pair.second());
        }
    }

    public boolean delete(K key) {
        int slot = searchIndex(key);
        if(slot == -1){
            return false;
        }
        else{
            arr[slot] = new Pair<>(null, null);
            return true;
        }
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
