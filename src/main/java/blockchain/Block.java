package blockchain;

import transaction.Transaction;

import java.io.Serializable;

public interface Block extends Serializable {
    boolean isValid(int difficulty, byte[] stateRoot);

    byte[] getBlockHash();

    byte[] getPreviousBlockHash();

    byte[] serialize();

    Transaction[] getTransactions();

    Block mine();
}
