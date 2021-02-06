package tx;

import account.ContractAccount;
import contract.Message;
import contract.DataMessage;
import blockchain.StateTrie;

import java.lang.reflect.InvocationTargetException;

public class MessageCallTx extends Tx {
    private final byte[] recipient;
    private final Data data;

    public MessageCallTx(byte[] sender, int nonce, byte[] recipient, Data data) {
        super(sender, nonce);
        this.recipient = recipient;
        this.data = data;
    }

    @Override
    protected Object doUpdate(StateTrie stateTrie) {
        ContractAccount account = getContractAccount(stateTrie);
        byte[] before = account.getStorageRoot();

        Message message = new DataMessage(sender, data);
        Object result;
        try {
            result = account.execute(stateTrie.getStateDatabase(), message);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        if(account.getStorageRoot() != before)
            stateTrie.update(account);

        return result;
    }

    private ContractAccount getContractAccount(StateTrie stateTrie) {
        ContractAccount account = (ContractAccount) stateTrie.get(recipient);
        if (account == null)
            throw new IllegalStateException("No account with provided recipient address.");

        if(account.getStorageRoot() == null)
            throw new UnsupportedOperationException("Cannot make message call to EOA.");

        if(data.getMethodName().equals("constructor"))
            throw new UnsupportedOperationException("Cannot call to constructor method after deployment.");
        return account;
    }
}
