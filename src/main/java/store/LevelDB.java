package store;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

import java.io.File;
import java.io.IOException;

public class LevelDB implements DataStore {
    private DB db;
    private final File file;

    public LevelDB(File file) {
        this.file = file;
        this.init();
    }

    private void init() {
        Options options = new Options();
        try {
            this.db = factory.open(this.file, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(byte[] key, byte[] value) {
        db.put(key, value);
    }

    @Override
    public byte[] get(byte[] key) {
        return db.get(key);
    }

    @Override
    public void close() {
        try {
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}