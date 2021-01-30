package transaction;

import account.CA;
import state.State;
import utils.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ContractCreationTransaction extends Transaction{
    private byte[] init;

    public ContractCreationTransaction(byte[] sender, byte[] init){
        super(sender);
        this.init = init;
    }

    @Override
    protected Object doUpdate(State state) {
        byte[] address = calculateContractAddress();
        byte[] codeHash = StringUtil.applyKeccak(init);

        state.getStateDatabase().put(codeHash, init);
        state.update(new CA(address, codeHash));

        return address;
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
