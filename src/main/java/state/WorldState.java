package state;

import org.apache.commons.lang3.SerializationUtils;

import account.Account;
import store.DataStore;
import store.MerkleTrie;
import store.Trie;

public class WorldState implements State{
    Trie stateTrie;
    DataStore db;

    public WorldState(DataStore db) {
        this.db = db;
        stateTrie = new MerkleTrie(db);
    }

    @Override
    public Account get(byte[] address) {
        byte[] account = stateTrie.get(address);
        if(account == null)
            return null;
        return SerializationUtils.deserialize(account);
    }

    @Override
    public void update(Account account) {
        stateTrie.put(account.getAddress(), account.serialize());
    }

    @Override
    public byte[] getRootHash() {
        return stateTrie.getRootHash();
    }

    @Override
    public DataStore getStateDatabase() {
        return db;
    }

    public void setRootHash(byte[] rootHash) {
        stateTrie.setRoot(rootHash);
    }
}
