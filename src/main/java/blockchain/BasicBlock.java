package blockchain;

import org.apache.commons.lang3.SerializationUtils;
import transaction.Transaction;
import utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class BasicBlock implements Block{
    private byte[] blockHash;
    private int nonce;
    private final byte[] previousBlockHash;
    private final long timestamp;
    private final int difficulty;
    private final byte[] stateRoot;
    private final byte[] transactionRoot;
    private final Transaction[] transactions;

    public BasicBlock(byte[] previousBlockHash, byte[] stateRoot, int difficulty, Transaction[] transactions) {
        this.previousBlockHash = previousBlockHash;
        this.stateRoot = stateRoot;
        this.difficulty = difficulty;
        this.transactions = transactions;
        timestamp = new Date().getTime();
        transactionRoot = calculateTransactionRoot();
        blockHash = calculateBlockHash();
    }

    @Override
    public boolean isValid(int difficulty, byte[] stateRoot) {
        return this.difficulty >= difficulty
                && this.stateRoot == stateRoot
                && transactionRoot == calculateTransactionRoot()
                && blockHash == calculateBlockHash();
    }

    @Override
    public byte[] getBlockHash() {
        return blockHash;
    }

    @Override
    public byte[] getPreviousBlockHash() {
        return previousBlockHash;
    }

    @Override
    public byte[] serialize() {
        return SerializationUtils.serialize(this);
    }

    @Override
    public Transaction[] getTransactions() {
        return transactions;
    }

    @Override
    public Block mine() {
        while(!isDifficultyLevelValid()){
            nonce++;
            blockHash = calculateBlockHash();
        }
        return this;
    }

    private byte[] calculateTransactionRoot() {
        TransactionTrie transactionTrie = new BasicTransactionTrie();
        for(Transaction transaction: transactions)
            transactionTrie.add(transaction);

        return transactionTrie.getRootHash();
    }

    private byte[] calculateBlockHash() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            stream.write(previousBlockHash);
            stream.write((int) timestamp);
            stream.write(nonce);
            stream.write(difficulty);
            stream.write(stateRoot);
            stream.write(transactionRoot);

            return StringUtil.applyKeccak(stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private boolean isDifficultyLevelValid() {
        String target = new String(new char[difficulty]).replace('\0', '0');
        String blockHashHex = StringUtil.bytesToHex(blockHash);

        return blockHashHex.substring(0, difficulty).equals(target);
    }
}
