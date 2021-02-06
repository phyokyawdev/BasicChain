package account;

import contract.Message;
import store.DataStore;

import java.lang.reflect.InvocationTargetException;

public interface ContractAccount extends Account {
    byte[] getStorageRoot();
    Object execute(DataStore db, Message message) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}
