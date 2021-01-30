package store;

public interface Trie {
    void put(byte[] key, byte[] value);

    byte[] get(byte[] key);

    byte[] getRootHash();

    void setRoot(byte[] root);
}
