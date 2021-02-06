package transaction;

import java.io.Serializable;

public interface Transaction extends Serializable {
    void sign(byte[] privateKey);
    boolean verify();
    byte[] getSender();
    byte[] getTransactionId();
    byte[] serialize();
}
