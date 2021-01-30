package transaction;

import account.ContractAccount;
import contract.Message;
import contract.ContractMessage;
import state.State;

public class MessageCallTransaction extends Transaction{
    private byte[] recipient;
    private Data data;

    public MessageCallTransaction(byte[] sender, byte[] recipient, Data data) {
        super(sender);
        this.recipient = recipient;
        this.data = data;
    }

    @Override
    protected Object doUpdate(State state) {
        ContractAccount account = (ContractAccount) state.get(recipient);
        if (account == null)
            throw new IllegalStateException("No account with provided recipient address.");

        byte[] before = account.getStorageRoot();
        if(before == null)
            throw new UnsupportedOperationException("Cannot make message call to EOA.");

        Message message = new ContractMessage(sender, data);
        Object result = account.execute(state.getStateDatabase(), message);

        if(account.getStorageRoot() != before)
            state.update(account);

        return result;
    }
}
