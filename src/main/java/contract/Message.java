package contract;

public interface Message {
    byte[] getSender();
    String getMethodName();
    Class[] getParameterTypes();
    Object[] getInputs();
}
