package laborai.studijosktu;

import java.util.Arrays;

public class HashTable<K, V> implements MapADTp<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;

    // Maišos lentelė
    protected Entry<K, V>[] table;
    // Lentelėje esančių raktas-reikšmė porų kiekis
    protected int size = 0;
    // Apkrovimo faktorius
    protected float loadFactor;
    // Maišos metodas
    protected HashType ht;

    //--------------------------------------------------------------------------
    //  Maišos lentelės įvertinimo parametrai
    //--------------------------------------------------------------------------
    // Maksimalus suformuotos maišos lentelės grandinėlės ilgis
    protected int maxChainSize = 1;
    // Permaišymų kiekis
    protected int rehashesCounter = 0;
    // Paskutinės patalpintos poros grandinėlės indeksas maišos lentelėje
    protected int lastUpdatedChain = 0;
    // Lentelės grandinėlių skaičius
    protected int chainsCounter = 0;
    // Einamas poros indeksas maišos lentelėje
    protected int index = 0;
    protected Entry<K, V> dummy;

    protected int lastUpdatedIndex = 0;
    public HashTable() {
        this(DEFAULT_HASH_TYPE);
    }

    public HashTable(HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }
    public HashTable(int initialCapacity, HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public HashTable(float loadFactor, HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public HashTable(int initialCapacity, float loadFactor, HashType ht) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Entry[initialCapacity];
        this.loadFactor = loadFactor;
        this.ht = ht;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public int getMaxChainSize() {
        return maxChainSize;
    }
    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }
    @Override
    public int getTableCapacity() {
        return table.length;
    }
    public int getLastUpdatedChain() {
        return lastUpdatedChain;
    }
    public int getChainsCounter() {
        return chainsCounter;
    }
    @Override
    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        index = 0;
        lastUpdatedChain = 0;
        maxChainSize = 0;
        rehashesCounter = 0;
        chainsCounter = 0;
    }
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null in put(Key key, Value value)");
        }
        index = findPutPosition(key);
        if(table[index] == null || table[index].key.equals(dummy.key)){
            table[index] = new Entry<>(key, value);
            size++;
            if(size >= table.length*loadFactor){
                rehash(table[index]);
            }
            else{
                lastUpdatedIndex = index;
            }
        }
        else{
            lastUpdatedIndex = index;
            table[index].value = value;
        }

        return value;
    }
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in get(Key key)");
        }

        index = hash(key, ht);
        int index0 = index;
        int i = 0;
        for(int j = 0; j < table.length; j++){
            if(table[index] != null && table[index].key.equals(key)){
                return table[index].value;
            }
            i++;
            index = (index0 + i) % table.length;
        }
        return null;
    }
    private int hash(K key, HashType hashType) {
        int h = key.hashCode();
        switch (hashType) {
            case DIVISION:
                return Math.abs(h) % table.length;
            case MULTIPLICATION:
                double k = (Math.sqrt(5) - 1) / 2;
                return (int) (((k * Math.abs(h)) % 1) * table.length);
            case JCF7:
                h ^= (h >>> 20) ^ (h >>> 12);
                h = h ^ (h >>> 7) ^ (h >>> 4);
                return h & (table.length - 1);
            case JCF8:
                h = h ^ (h >>> 16);
                return h & (table.length - 1);
            default:
                return Math.abs(h) % table.length;
        }
    }
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in remove");
        }

        index = findPosition(key);

        if (index == -1) {
            throw new IllegalArgumentException("Key does not exist in hash table");
        }
        if(table[index] != null){
            table[index].key = dummy.key;
            size--;
        }
        return null;
    }
    private int removeCounter(K key, int index0){
        int i =0;
        int index1 = index0;
        for(int j = 0; j<table.length; j++){
            if(table[index0] == null){
                return i;
            }
            i++;
            index0 = (index1 +i) % table.length;
        }
        return -1;
    }
    public boolean containsValue(V value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && table[i].value.equals(value)) {
                return true;
            }
        }
        return false;
    }
    private int findPosition(K key){
        int index = hash(key, ht);
        int index0 = index;
        int i = 0;
        for(int j = 0; j < table.length; j++){
            if(table[index] == null || table[index].key.equals(key)){
                return index;
            }
            i++;
            index = (index0 + i) % table.length;
        }
        return -1;
    }
    private int findPutPosition(K key){
        int index = hash(key, ht);
        int temp = index;
        int i = 0;
        for(int j = 0; j < table.length; j++){
            if(table[index] == null || table[index].key.equals(key) || table[index].key.equals(dummy.key)){
                return index;
            }
            i++;
            index = (temp + i) % table.length;
        }
        return -1;
    }


    private void rehash(Entry<K, V> node) {
        HashTable mapMaisos = new HashTable(table.length*2, loadFactor, ht);
        mapMaisos.dummy = this.dummy;
        for(int i = 0; i < table.length; i++){
            if(table[i] != null){
                if(table[i].equals(node))
                    lastUpdatedIndex = i;
                mapMaisos.put(table[i].key, table[i].value);
            }
        }
        table = mapMaisos.table;
        rehashesCounter++;

    }
    public int getEmptyCount(){
        int n = 0;
        for (Entry<K, V> node : table){
            if(node == null){
                n++;
            }
        }
        return n;
    }
    @Override
    public String[][] toArray() {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Entry<K, V> n : table) {
            result[count][0] = n.toString();
            count++;
        }
        return result;
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Entry<K, V> node : table) {
            if (node != null) {

                result.append(node.toString()).append(System.lineSeparator());
            }
        }
        return result.toString();
    }
    protected class Entry<K, V>{

        // Raktas
        protected K key;
        // Reikšmė
        protected V value;
        // Rodyklė į sekantį grandinėlės mazgą


        protected Entry() {
        }

        protected Entry(K key, V value) {
            this.key = key;
            this.value = value;

        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
