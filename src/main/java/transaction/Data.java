package transaction;

public class Data {
    private String methodName;
    private Class[] parameterTypes;
    private Object[] inputs;

    public Data(String methodName, Class[] parameterTypes, Object[] inputs) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.inputs = inputs;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getInputs() {
        return inputs;
    }
}
