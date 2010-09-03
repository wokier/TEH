package teh.utils;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import teh.annotations.TEH;
import teh.annotations.ToString;
import teh.annotations.ToStringEquals;
import teh.annotations.ToStringEqualsHashCode;

/**
 * TEH Utils
 * @author f_wauquier
 * 
 */
public class TEHUtils {

    /**
     * @see Object#toString()
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if(object == null){
            return "<null>";
        }
        Class<? extends Object> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(TEH.class)) {
            return object.toString();
        }
        ToStringBuilder builder = new ToStringBuilder(object);
        do{
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(ToString.class) || declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
                    builder.append(declaredField.getName(), getToStringValue(object, declaredField));
                }
            }
            clazz= clazz.getSuperclass();
        }while(clazz != null);
        return builder.toString();
    }

    private static Object getToStringValue(Object object, Field declaredField) {
        try {
            return toString(declaredField.get(object));
        } catch (Exception e) {
            return e;
        }
    }

    /**
     * @see Object#equals(Object)
     * @param object
     * @param other
     * @return
     */
    public static boolean equals(Object object, Object other) {
        if(object == null){
            return other == null;
        }
        if(object == other){
            return true;
        }
        Class<? extends Object> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(TEH.class)) {
            return object.equals(other);
        }
        do{
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
                    if(!getEqualsValue(object, other,declaredField)){
                        return false;
                    }
                }
            }
            clazz= clazz.getSuperclass();
        }while(clazz != null);
        return true;
    }
    
    private static boolean getEqualsValue(Object object,Object other, Field declaredField) {
        try {
            return equals(declaredField.get(object),declaredField.get(other));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @see Object#hashCode()
     * @param object
     * @return
     */
    public static int hashCode(Object object) {
        if(object == null){
            return 0;
        }
        Class<? extends Object> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(TEH.class)) {
            return object.hashCode();
        }
        HashCodeBuilder builder= new HashCodeBuilder();
        do {
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
                    builder = getHashCodeValue(object, declaredField,builder);
                }
            }
            clazz= clazz.getSuperclass();
        }while(clazz != null);
        return builder.toHashCode();
    }
    
    private static HashCodeBuilder getHashCodeValue(Object object, Field declaredField, HashCodeBuilder builder) {
        try {
            return builder.append(hashCode(declaredField.get(object)));
        } catch (Exception e) {
            return builder;
        }
    }

}
