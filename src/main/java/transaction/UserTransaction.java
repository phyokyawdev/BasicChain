package transaction;

import org.apache.commons.lang3.SerializationUtils;
import utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static utils.Secp256k1.*;

public class UserTransaction implements Transaction{
    private byte[] transactionId;
    private final int nonce;
    private final byte[] to; // new byte[0](empty string) if contract creation
    private final byte[] data;
    private byte[][] rsv;

    public UserTransaction(int nonce, byte[] to, byte[] data) {
        this.nonce = nonce;
        this.to = to;
        this.data = data;
        transactionId = getDataHash();
    }

    @Override
    public void sign(byte[] privateKey) {
        rsv = signTransaction(getDataHash(), privateKey);
    }

    @Override
    public boolean verify(){
        if(rsv == null)
            throw new IllegalStateException("Transaction is not signed.");

        return verifyTransaction(getDataHash(), rsv[0], rsv[1]);
    }

    @Override
    public byte[] getSender() {
        if(!verify())
            throw new IllegalStateException("Transaction is not verified.");

        return recoverPublicKey(rsv[0], rsv[1], rsv[2], getDataHash());
    }

    @Override
    public byte[] getTransactionId() {
        return transactionId;
    }

    @Override
    public byte[] serialize() {
        return SerializationUtils.serialize(this);
    }

    private byte[] getDataHash() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(nonce);
        try {
            stream.write(to);
            stream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return StringUtil.applyKeccak(stream.toByteArray());
    }
}
