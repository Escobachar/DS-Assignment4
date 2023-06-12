import java.util.Iterator;
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
        Iterator<Pair<K, V>> iter = arr[index].iterator();
        while(iter.hasNext()){
            Pair<K,V> temp = iter.next();
            if(temp.first() == key)
                return (V)temp.second();
        }
        return null;
    }

    private Pair<K,V> searchPair(K key){
        int index = hashFunc.hash(key);
        Iterator<Pair<K, V>> iter = arr[index].iterator();
        while(iter.hasNext()){
            Pair<K,V> temp = iter.next();
            if(temp.first() == key)
                return temp;
        }
        return null;
    }

    public void insert(K key, V value) {
        if((double) (size + 1) /capacity < maxLoadFactor) {
            arr[hashFunc.hash(key)].add(new Pair<K,V>(key, value));
            size++;
        }
        else{
            rehash(key, value);
        }

    }

    private void rehash(K key, V value){
        LinkedList<Pair<K,V>>[] temp = arr;
        arr = new LinkedList[capacity * 2];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new LinkedList<>();
        }
        this.k = k+1;
        this.capacity = capacity * 2;
        this.hashFunc = hashFactory.pickHash(k);
        for (LinkedList<Pair<K,V>> pairs : temp) {
            for (Pair pair : pairs) {
                arr[hashFunc.hash((K) pair.first())].add(pair);
            }
        }
        insert(key,value);
    }

    public boolean delete(K key) {
        int index = hashFunc.hash(key);
        for (Pair<K,V> pair : arr[index] ) {
            if(pair.first() == key){
                arr[index].remove(pair);
                return true;
            }
        }
        return false;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
}
