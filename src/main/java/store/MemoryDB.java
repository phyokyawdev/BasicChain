package store;

import java.util.HashMap;
import java.util.Map;

public class MemoryDB implements DataStore{
    Map<byte[], byte[]> db = new HashMap<>();

    @Override
    public byte[] get(byte[] key) {
        return db.get(key);
    }

    @Override
    public void put(byte[] key, byte[] value) {
        db.put(key, value);
    }

    @Override
    public void close() {

    }
}
