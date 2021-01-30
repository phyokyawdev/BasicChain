package contract;

import org.apache.commons.lang3.SerializationUtils;
import store.Trie;
import utils.StringUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ContractExecutor {
    public static Object execute(Trie storageTrie, Class contract, Message message) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Contract.message = message;

        // to check changes after
        ArrayList<byte[]> fieldsData = new ArrayList<>();

        // attach field values
        Field[] fields = contract.getDeclaredFields();
        setFieldValues(storageTrie, fieldsData, fields);

        // execute contract
        Method method = contract.getMethod(message.getMethodName(), message.getParameterTypes());
        Object result = method.invoke(null, message.getInputs());

        // store changed field values
        storeFieldValues(storageTrie, fieldsData, fields);

        Contract.message = null;

        return result;
    }

    private static void storeFieldValues(Trie storageTrie ,ArrayList<byte[]> fieldsData, Field[] fields) throws IllegalAccessException {
        for (int i = 0; i < fields.length; i++) {
            String formattedPath = String.format("%064X", i);
            fields[i].setAccessible(true);
            Object obj = fields[i].get(null);
            byte[] fieldValue = SerializationUtils.serialize((Serializable) obj);

            if(!Arrays.equals(fieldsData.get(i), fieldValue))
                storageTrie.put(StringUtil.hexToBytes(formattedPath), fieldValue);
        }
    }

    private static void setFieldValues(Trie storageTrie, ArrayList<byte[]> fieldsData, Field[] fields) throws IllegalAccessException {
        for (int i = 0; i < fields.length; i++) {
            String formattedPath = String.format("%064X", i);
            byte[] fieldValue = storageTrie.get(StringUtil.hexToBytes(formattedPath));
            fieldsData.add(i, fieldValue);

            // set field values if they are not null
            if(fieldValue != null) {
                fields[i].setAccessible(true);
                fields[i].set(null, SerializationUtils.deserialize(fieldValue));
            }
        }
    }
}