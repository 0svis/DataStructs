package laborai.studijosktu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HashTablex<K,V> extends HashTable<K, V> implements MapADTx<K, V>{

    private K baseKey;       // Bazinis objektas skirtas naujų kūrimui
    private V baseObj;

    public HashTablex(K baseKey, V baseObj) {
        this(baseKey, baseObj, DEFAULT_HASH_TYPE);
    }

    /**
     * Konstruktorius su bazinio objekto fiksacija
     *
     * @param baseKey
     * @param baseObj
     * @param ht
     */
    public HashTablex(K baseKey, V baseObj, HashType ht) {
        this(baseKey, baseObj, DEFAULT_INITIAL_CAPACITY, ht);
    }

    /**
     * Konstruktorius su bazinio objekto fiksacija
     *
     * @param baseKey
     * @param baseObj
     * @param initialCapacity
     * @param ht
     */
    public HashTablex(K baseKey, V baseObj, int initialCapacity, HashType ht) {
        this(baseKey, baseObj, initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }
    public HashTablex(K baseKey, V baseObj, int initialCapacity, float loadFactor, HashType ht) {
        super(initialCapacity, loadFactor, ht);
        this.baseKey = baseKey;     // fiksacija dėl naujų elementų kūrimo
        this.baseObj = baseObj;     // fiksacija dėl naujų elementų kūrimo
        dummy = new Entry<K, V>(baseKey, baseObj);
    }
    @Override
    public V put(String dataString) {
        return put((K) dataString, (V) dataString);
    }

    @Override
    public void load(String filePath) {
        clear();
        if (filePath == null || filePath.length() == 0) {
            return;
        }
        if ((baseKey == null) || (baseObj == null)) { // Elementų kūrimui reikalingas baseObj
            Ks.ern("Naudojant load-metodą, "
                    + "reikia taikyti konstruktorių = new MapKTU(new Data(),new Data())");
        }
        try {
            try (BufferedReader fReader = Files.newBufferedReader(Paths.get(filePath), Charset.defaultCharset())) {
                fReader.lines().filter(line -> line != null && !line.isEmpty()).forEach(line -> put(line));
            }
        } catch (FileNotFoundException e) {
            Ks.ern("Tinkamas duomenų failas " + filePath + " nerastas");
        } catch (IOException | UncheckedIOException e) {
            Ks.ern("Failo " + filePath + " skaitymo klaida:" + e.getLocalizedMessage());
        }
    }

    @Override
    public void save(String filePath) {
        throw new UnsupportedOperationException("save nepalaikomas");
    }
    @Override
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
    public void println() {
        if (super.isEmpty()) {
            Ks.oun("Atvaizdis yra tuščias");
        } else {
            String[][] data = getModelList("=");
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    String format = (j == 0 | j % 2 == 1) ? "%7s" : "%15s";
                    Object value = data[i][j];
                    Ks.ouf(format, (value == null ? "" : value));
                }
                Ks.oufln("");
            }
        }

        Ks.oufln("****** Bendras porų kiekis yra " + super.size());
    }

    @Override
    public void println(String title) {
        Ks.ounn("========" + title + "=======");
        println();
        Ks.ounn("======== Atvaizdžio pabaiga =======");
    }
    @Override
    public String[][] getModelList(String delimiter) {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Entry<K, V> n : table) {
            List<String> list = new ArrayList();
            list.add( "[ " + count + " ]");
            if (n != null) {
                list.add("-->");
                list.add(split(n.toString(), delimiter));

            }
            result[count] = list.toArray(new String[0]);
            count++;
        }
        return result;
    }
    private String split(String s, String delimiter) {
        int k = s.indexOf(delimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }
    public double getAvgLength(){
        return 1;
    }
}
