package account;

import org.apache.commons.lang3.SerializationUtils;

public class EOA implements Account{
    private static final long serialVersionUID = 1L;
    private final byte[] address;
    private int nonce;

    public EOA(byte[] address){
        this.address = address;
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
}
