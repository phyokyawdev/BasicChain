package contract;

public class InitMessage implements Message{
    private final byte[] sender;
    private final String methodName;

    public InitMessage(byte[] sender) {
        this.sender = sender;
        methodName = "constructor";
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
        return new Class[0];
    }

    @Override
    public Object[] getInputs() {
        return new Object[0];
    }
}
