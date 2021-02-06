package account;

import contract.ContractExecutor;
import contract.ContractLoader;

import contract.Message;
import store.DataStore;
import store.MerkleTrie;
import store.Trie;
import utils.StringUtil;

import org.apache.commons.lang3.SerializationUtils;
import java.lang.reflect.InvocationTargetException;

public class CA implements ContractAccount{
    private static final long serialVersionUID = 1L;
    private static final byte[] EMPTY_MERKLE_TRIE_ROOT = StringUtil.applyKeccak(new byte[0]);

    private final byte[] address;
    private int nonce;
    private byte[] storageRoot;
    private final byte[] codeHash;

    public CA(byte[] address, byte[] codeHash) {
        this.address = address;
        this.codeHash = codeHash;
        storageRoot = EMPTY_MERKLE_TRIE_ROOT;
    }

    @Override
    public void increaseNonce() {
        nonce++;
    }

    @Override
    public byte[] serialize() {
        return SerializationUtils.serialize(this);
    }

    @Override
    public int getNonce() {
        return nonce;
    }

    @Override
    public byte[] getAddress() {
        return address;
    }

    @Override
    public byte[] getStorageRoot() {
        return storageRoot;
    }

    @Override
    public Object execute(DataStore db, Message message) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Trie storageTrie = new MerkleTrie(db, storageRoot);
        byte[] code = db.get(codeHash);

        ContractLoader contractLoader = new ContractLoader();
        Class<?> contract = contractLoader.load(code);

        Object result = ContractExecutor.execute(storageTrie, contract, message);

        // update account if storageRoot change
        if(storageTrie.getRootHash() != storageRoot)
            storageRoot = storageTrie.getRootHash();

        return result;
    }
}
