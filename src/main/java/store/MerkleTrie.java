package store;

import org.apache.commons.lang3.SerializationUtils;
import utils.StringUtil;

public class MerkleTrie implements Trie{
    private Node root;
    private DataStore db;

    public MerkleTrie(DataStore db){
        this.db = db;
        root = new Node();
        db.put(root.getHash(), root.serialize());
    }

    public MerkleTrie(DataStore db, byte[] rootHash) {
        this.db = db;
        root = getNode(rootHash);
    }

    private Node getNode(byte[] key) {
        byte[] branch = db.get(key);
        return SerializationUtils.deserialize(branch);
    }

    private int getIndex(String path, int i) {
        return Integer.parseInt(String.valueOf(path.charAt(i)), 16);
    }

    @Override
    public void put(byte[] key, byte[] value) {
        String path = StringUtil.bytesToHex(key);
        System.out.println("path : " + path);

        if(path.length() != 64)
            throw new IllegalArgumentException("key must be able to transform to 32 bytes hex string");

        root = put(value, root, path, 0);
    }

    private Node put(byte[] value, Node root, String path, int pointer) {
        // if this loop is end of input string,
        if(pointer > 63) {
            // create leaf
            System.out.println("creating leaf");
            Node node = new Node(value);
            db.put(node.getHash(), node.serialize());
            System.out.println("added leaf to db: " + node);
            return node;
        }

        int index = getIndex(path, pointer);

        if(root.getChild(index) == null) {
            // create new branch if not exist
            Node node = new Node();
            db.put(node.getHash(), node.serialize());

            root.addChild(index, node.getHash());
        }

        // if branch exist, retrieve node from db and do recursive
        Node node = getNode(root.getChild(index));
        Node node1 = put(value, node, path, pointer + 1);

        // update state and db recursively after updating state
        root.addChild(index, node1.getHash());
        db.put(root.getHash(), root.serialize());

        return root;
    }

    @Override
    public byte[] get(byte[] key) {
        String path = StringUtil.bytesToHex(key);
        Node node = root;

        if(path.length() != 64)
            throw new IllegalArgumentException("key must be able to transform to 32 bytes hex string");

        for (int i = 0; i < 64; i++) {
            int index = getIndex(path, i);

            if(node.getChild(index) == null){
                return null;
            }
            node = getNode(node.getChild(index));
        }

        return node.getValue();
    }

    @Override
    public byte[] getRootHash() {
        return root.getHash();
    }

    @Override
    public void setRoot(byte[] root) {
        this.root = getNode(root);
    }
}
