package transaction;

import account.Account;
import account.EOA;
import state.State;

public abstract class Transaction {
    protected byte[] sender;
    protected int nonce;

    public Transaction(byte[] sender) {
        this.sender = sender;
    }

    public Object update(State state){
        Account senderAccount = state.get(sender);
        if(senderAccount == null) {
            senderAccount = new EOA(sender);
            state.update(senderAccount);
        }

        senderAccount.increaseNonce();
        state.update(senderAccount);

        if(senderAccount.getNonce() != nonce)
            throw new IllegalStateException("Transaction nonce mismatch.");

        return doUpdate(state);
    }

    protected abstract Object doUpdate(State state);
}
