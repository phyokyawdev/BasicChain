package transaction;

import utils.Secp256k1;

public class UserWallet implements Wallet{
    private byte[] privateKey;

    @Override
    public void generatePrivateKey() {
        privateKey = Secp256k1.generatePrivateKey();
    }

    @Override
    public Transaction sign(Transaction transaction) {
        transaction.sign(privateKey);
        return transaction;
    }
}
