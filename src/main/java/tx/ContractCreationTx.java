package tx;

import account.CA;
import account.ContractAccount;
import contract.InitMessage;
import contract.Message;
import blockchain.StateTrie;
import utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ContractCreationTx extends Tx {
    private final byte[] init;

    public ContractCreationTx(byte[] sender, int nonce, byte[] init){
        super(sender, nonce);
        this.init = init;
    }

    @Override
    protected Object doUpdate(StateTrie stateTrie) {
        ContractAccount account = createContractAccount(stateTrie);
        Message message = new InitMessage(sender);

        try {
            account.execute(stateTrie.getStateDatabase(), message);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        stateTrie.update(account);
        return account.getAddress();
    }

    private ContractAccount createContractAccount(StateTrie stateTrie) {
        byte[] address = calculateContractAddress();
        byte[] codeHash = StringUtil.applyKeccak(init);

        stateTrie.getStateDatabase().put(codeHash, init);
        return new CA(address, codeHash);
    }

    private byte[] calculateContractAddress() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            stream.write(sender);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stream.write(nonce);
        return StringUtil.applyKeccak(stream.toByteArray());
    }
}
