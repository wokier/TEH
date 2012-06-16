package teh.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teh.annotations.TEH;
import teh.annotations.ToString;
import teh.annotations.ToStringEquals;
import teh.annotations.ToStringEqualsHashCode;
import teh.fields.TEHFields;
import teh.utils.TEHObject;

/**
 * TEHFields Implementation by reflection
 * 
 * @author francois wauquier
 * 
 */
public class TEHFieldsReflectionExtractor implements TEHFields {

    private static TEHFieldsReflectionExtractor instance;

    private TEHFieldsReflectionExtractor() {
	super();
    }

    /**
     * singleton
     * 
     * @return
     */
    public static synchronized TEHFields getInstance() {
	if (instance == null) {
	    instance = new TEHFieldsReflectionExtractor();
	}
	return instance;
    }

    public boolean isTEHActivated(Object object) {
	return object != null && (object instanceof TEHObject || object.getClass().isAnnotationPresent(TEH.class));
    }

    public List<TEHFieldValue> extractToStringFieldValues(Object object) {
	List<TEHFieldValue> toStringFieldValues = new ArrayList<TEHFieldValue>();
	if (isTEHActivated(object)) {
	    Class<? extends Object> clazz = object.getClass();
	    do {
		for (Field declaredField : clazz.getDeclaredFields()) {
		    if (declaredField.isAnnotationPresent(ToString.class) || declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
			try {
			    Object value = getAccessibleValue(object, declaredField);
			    toStringFieldValues.add(new TEHFieldValue(declaredField.getName(), value));
			} catch (Exception e) {
			    throw new RuntimeException("TEH toString", e);
			}
		    }
		}
		clazz = clazz.getSuperclass();
	    } while (clazz != null);
	}
	return toStringFieldValues;
    }

    public Map<String, TEHFieldValue> extractEqualsFieldValues(Object object) {
	Map<String, TEHFieldValue> equalsFieldValues = new HashMap<String, TEHFieldValue>();
	if (isTEHActivated(object)) {
	    Class<? extends Object> clazz = object.getClass();
	    do {
		for (Field declaredField : clazz.getDeclaredFields()) {
		    if (declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
			try {
			    Object value = getAccessibleValue(object, declaredField);
			    if (value != null) {
				equalsFieldValues.put(declaredField.getName(), new TEHFieldValue(declaredField.getName(), value));
			    }
			} catch (Exception e) {
			    throw new RuntimeException("TEH equals", e);
			}
		    }
		}
		clazz = clazz.getSuperclass();
	    } while (clazz != null);
	}
	return equalsFieldValues;
    }

    public List<Object> extractHashCodeValues(Object object) {
	List<Object> hashCodeFieldValues = new ArrayList<Object>();
	if (isTEHActivated(object)) {
	    Class<? extends Object> clazz = object.getClass();
	    do {
		for (Field declaredField : clazz.getDeclaredFields()) {
		    if (declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
			try {
			    Object value = getAccessibleValue(object, declaredField);
			    if (value != null) {
				hashCodeFieldValues.add(value);
			    }
			} catch (Exception e) {
			    throw new RuntimeException("TEH hashCode", e);
			}
		    }
		}
		clazz = clazz.getSuperclass();
	    } while (clazz != null);
	}
	return hashCodeFieldValues;
    }

    private Object getAccessibleValue(Object object, Field declaredField) throws IllegalAccessException {
	if (!declaredField.isAccessible()) {
	    declaredField.setAccessible(true);
	}
	return declaredField.get(object);
    }
}
