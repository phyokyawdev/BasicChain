package blockchain;

import org.apache.commons.lang3.SerializationUtils;

import account.Account;
import store.DataStore;
import store.MerkleTrie;
import store.Trie;

public class BasicStateTrie implements StateTrie {
    Trie stateTrie;
    DataStore db;

    public BasicStateTrie(DataStore db) {
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
    public void setRootHash(byte[] rootHash) {
        stateTrie.setRoot(rootHash);
    }

    @Override
    public DataStore getStateDatabase() {
        return db;
    }
}
