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
    private static final byte[] EMPTY_MERKLE_TRIE_ROOT = StringUtil.hexToBytes("C5D2460186F7233C927E7DB2DCC703C0E500B653CA82273B7BFAD8045D85A470");
    private byte[] address;
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
    public Object execute(DataStore db, Message message) {
        Trie storageTrie = new MerkleTrie(db, storageRoot);
        byte[] code = db.get(codeHash);

        ContractLoader contractLoader = new ContractLoader();
        Class contract = contractLoader.load(code);

        Object result = null;
        try {
            result = ContractExecutor.execute(storageTrie, contract, message);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // update account if storageRoot change
        if(storageTrie.getRootHash() != storageRoot)
            storageRoot = storageTrie.getRootHash();

        return result;
    }
}
