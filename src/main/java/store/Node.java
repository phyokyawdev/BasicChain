package store;

import org.apache.commons.lang3.SerializationUtils;
import utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Node implements Serializable {
    private static final long serialVersionUID = 5559685098267757690L;
    private byte[] hash;
    private byte[][] children;
    private byte[] value;

    // branch
    public Node() {
        children = new byte[16][];
        calculateHash();
    }

    // leaf
    public Node(byte[] value) {
        this.value = value;
        calculateHash();
    }

    public void addChild(int index, byte[] child) {
        children[index] = child;
        calculateHash();
    }

    private void calculateHash() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try{
            if(children != null) {
                for (byte[] child : children) {
                    if (child != null)
                        stream.write(child);
                }
            }
            if(value != null)
                stream.write(value);
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        hash = StringUtil.applyKeccak(stream.toByteArray());
    }

    public byte[] serialize() {
        return SerializationUtils.serialize(this);
    }

    public byte[] getChild(int index) {
        return children[index];
    }

    public byte[] getHash(){
        return hash;
    };

    public byte[] getValue() {
        return value;
    };
}
