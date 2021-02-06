import store.DataStore;
import store.LevelDB;
import store.MerkleTrie;
import store.Trie;
import utils.StringUtil;

import java.io.File;

public class Chain {
    public static void main(String[] args){
        DataStore db = new LevelDB(new File("test"));
        Trie trie = new MerkleTrie(db);
        System.out.println("empty trie: " +StringUtil.bytesToHex(trie.getRootHash()));

        System.out.println("empty keccak: " + StringUtil.bytesToHex(StringUtil.applyKeccak(new byte[0])));
    }
}
