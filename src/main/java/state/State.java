package state;

import account.Account;
import store.DataStore;

public interface State {
    Account get(byte[] address);

    void update(Account account);

    byte[] getRootHash();

    DataStore getStateDatabase();
}
