package account;

import java.io.Serializable;

public interface Account extends Serializable {
    void increaseNonce();
    byte[] serialize();
    int getNonce();
    byte[] getAddress();
}
