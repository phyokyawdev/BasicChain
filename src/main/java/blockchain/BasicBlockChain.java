package blockchain;

import store.DataStore;

public class BasicBlockChain implements BlockChain{
    private int height;
    private final DataStore db;

    public BasicBlockChain(DataStore db) {
        this.db = db;
    }

    @Override
    public void add(Block block) {
        db.put(block.getBlockHash(), block.serialize());
        height++;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
