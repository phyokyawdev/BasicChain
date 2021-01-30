package contract;

import transaction.Data;

public class ContractMessage implements Message{
    private byte[] sender;
    private String methodName;
    private Class[] parameterTypes;
    private Object[] inputs;

    public ContractMessage(byte[] sender, Data data) {
        this.sender = sender;
        this.methodName = data.getMethodName();
        this.parameterTypes = data.getParameterTypes();
        this.inputs = data.getInputs();
    }

    @Override
    public byte[] getSender() {
        return sender;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getInputs() {
        return inputs;
    }
}
