package store;

public interface DataStore {
    byte[] get(byte[] key);

    void put(byte[] key, byte[] value);

    void close();
}
