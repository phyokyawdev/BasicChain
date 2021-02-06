package transaction;

public interface Wallet {
    void generatePrivateKey();
    Transaction sign(Transaction transaction);
}
