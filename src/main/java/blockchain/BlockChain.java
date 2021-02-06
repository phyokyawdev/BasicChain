package blockchain;

public interface BlockChain {
    void add(Block block);
    int getHeight();
    boolean isValid();
}
