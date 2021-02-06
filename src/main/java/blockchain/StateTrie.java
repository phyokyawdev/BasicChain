package blockchain;

import account.Account;
import store.DataStore;

public interface StateTrie {
    Account get(byte[] address);

    void update(Account account);

    byte[] getRootHash();

    void setRootHash(byte[] rootHash);

    DataStore getStateDatabase();
}
