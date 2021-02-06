package tx;

import account.Account;
import account.EOA;
import blockchain.StateTrie;

public abstract class Tx {
    protected byte[] sender;
    protected int nonce;

    public Tx(byte[] sender, int nonce) {
        this.sender = sender;
        this.nonce = nonce;
    }

    public Object update(StateTrie stateTrie) throws Exception{
        Account senderAccount = getAccount(stateTrie);

        senderAccount.increaseNonce();
        stateTrie.update(senderAccount);

        return doUpdate(stateTrie);
    }

    private Account getAccount(StateTrie stateTrie) {
        Account senderAccount = stateTrie.get(sender);
        if(senderAccount == null) {
            System.out.println("new account");
            senderAccount = new EOA(sender);
            stateTrie.update(senderAccount);
        }

        if(senderAccount.getNonce() != nonce)
            throw new IllegalStateException("Transaction nonce mismatch.");
        return senderAccount;
    }

    protected abstract Object doUpdate(StateTrie stateTrie);
}
