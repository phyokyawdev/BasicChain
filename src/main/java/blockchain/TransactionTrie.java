package blockchain;

import transaction.Transaction;

public interface TransactionTrie {
    void add(Transaction transaction);
    byte[] getRootHash();
}
