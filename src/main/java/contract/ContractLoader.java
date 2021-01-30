package contract;

public class ContractLoader extends ClassLoader{
    public Class load(byte[] code){
        return defineClass(null, code, 0, code.length);
    }
}
