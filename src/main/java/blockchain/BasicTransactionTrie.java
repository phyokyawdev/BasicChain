package blockchain;

import store.MemoryDB;
import store.MerkleTrie;
import store.Trie;
import transaction.Transaction;

public class BasicTransactionTrie implements TransactionTrie{
    Trie transactionTrie;

    public BasicTransactionTrie() {
        transactionTrie = new MerkleTrie(new MemoryDB());
    }

    @Override
    public void add(Transaction transaction) {
        transactionTrie.put(transaction.getTransactionId(), transaction.serialize());
    }

    @Override
    public byte[] getRootHash() {
        return transactionTrie.getRootHash();
    }
}
