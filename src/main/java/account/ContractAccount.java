package account;

import contract.Message;
import store.DataStore;

public interface ContractAccount extends Account {
    byte[] getStorageRoot();
    Object execute(DataStore db, Message message);
}
