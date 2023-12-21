package main.org.example.jdbc.dao.abs;

import main.org.example.model.Employee;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Set;

public interface AbstractDAO <T, K>{

    boolean create(T type);
    T findById(K key);
    boolean deleteById(K key);
    boolean update(T type);
    Set<T> all();

    default Method findSetMethod(T object, String methodName){

        Method[] methods = object.getClass().getDeclaredMethods();

        for (Method method : methods){
            if (method.getName().equals(methodName.replace("get", "set"))){
                return method;
            }
        }
        return null;
    }


    default T compareToReplace(T object, T objOld, T objNew) throws InvocationTargetException, IllegalAccessException {

        Method[] methods = object.getClass().getDeclaredMethods();

        for (Method method : methods){
            if (method.getName().equalsIgnoreCase("getid")){
                findSetMethod(object, method.getName()).invoke(object, method.invoke(objOld));
                continue;
            }
            if (method.getName().startsWith("get")){
                if (method.getReturnType().equals(int.class)){
                    if ((int)method.invoke(objOld) != 0 && (int)method.invoke(objNew) != 0){
                        if (!method.invoke(objOld).equals(method.invoke(objNew))){
                            findSetMethod(object, method.getName()).invoke(object, method.invoke(objNew));
                        }else {
                            findSetMethod(object, method.getName()).invoke(object, method.invoke(objOld));
                        }
                    }
                    else if ((int)method.invoke(objOld) == 0 && (int)method.invoke(objNew) != 0){
                        findSetMethod(object, method.getName()).invoke(object, method.invoke(objNew));
                    }else if ((int)method.invoke(objOld) != 0 && (int)method.invoke(objNew) == 0){
                        findSetMethod(object, method.getName()).invoke(object, method.invoke(objOld));
                    }
                } else if (method.getReturnType().equals(String.class)) {
                    if (!Objects.equals(method.invoke(objOld), "") && !Objects.equals(method.invoke(objNew), "")){
                        if (!method.invoke(objOld).equals(method.invoke(objNew))){
                            findSetMethod(object, method.getName()).invoke(object, method.invoke(objNew));
                        }else {
                            findSetMethod(object, method.getName()).invoke(object, method.invoke(objOld));
                        }
                    }
                    else if (Objects.equals(method.invoke(objOld), "") && !Objects.equals(method.invoke(objNew), "")){
                        findSetMethod(object, method.getName()).invoke(object, method.invoke(objNew));
                    }else if (!Objects.equals(method.invoke(objOld), "") && Objects.equals(method.invoke(objNew), "")){
                        findSetMethod(object, method.getName()).invoke(object, method.invoke(objOld));
                    }
                } else {
                    if (method.invoke(objOld) != null && method.invoke(objNew) != null){
                        if (!method.invoke(objOld).equals(method.invoke(objNew))){
                            findSetMethod(object, method.getName()).invoke(object, method.invoke(objNew));
                        }else {
                            findSetMethod(object, method.getName()).invoke(object, method.invoke(objOld));
                        }
                    }
                    else if (method.invoke(objOld) == null && method.invoke(objNew) != null){
                        findSetMethod(object, method.getName()).invoke(object, method.invoke(objNew));
                    }else if (method.invoke(objOld) != null && method.invoke(objNew) == null){
                        findSetMethod(object, method.getName()).invoke(object, method.invoke(objOld));
                    }
                }
            }
        }

        return object;
    }

    default T map(T t, ResultSet rs) {
        try {
            for (Field field : t.getClass().getDeclaredFields()){
                String name = field.getName();
                String type = field.getType().getSimpleName();

                Object value = null;

                switch (type) {
                    case "String":
                        value = rs.getString(name);
//                    try {
//                        field.set(this, value);
//                    } catch (IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
                        break;
                    case "Integer":
                        value = rs.getInt(name);
                        break;
                    case "int":
                        value = rs.getInt(name);
                        break;
                    case "Double":
                        value = rs.getDouble(name);
                        break;
                    case "double":
                        value = rs.getDouble(name);
                        break;
                    case "Timestamp":
                        value = rs.getTimestamp(name);
                        break;
                    case "Boolean":
                        value = rs.getBoolean(name);
                        break;
                    default:
                        System.out.println("No implements for type " + type);
                        break;
                }

                for (Method method : t.getClass().getDeclaredMethods()){
                    if (method.getName().toLowerCase().startsWith("set" + name.toLowerCase())){
                        method.invoke(t, value);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return t;
    }

}
