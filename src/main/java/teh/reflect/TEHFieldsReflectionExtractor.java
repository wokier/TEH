package teh.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<Class<? extends Object>, List<Class<? extends Object>>> classesMap = new ConcurrentHashMap<Class<? extends Object>, List<Class<? extends Object>>>();

    private Map<Class<? extends Object>, List<Field>> toStringFieldsMap = new ConcurrentHashMap<Class<? extends Object>, List<Field>>();
    private Map<Class<? extends Object>, List<Field>> equalsFieldsMap = new ConcurrentHashMap<Class<? extends Object>, List<Field>>();
    private Map<Class<? extends Object>, List<Field>> hahsCodeFieldsMap = new ConcurrentHashMap<Class<? extends Object>, List<Field>>();

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
	    for (Class<? extends Object> clazz : getClassHierarchy(object.getClass())) {
		for (Field field : extractToStringFields(clazz)) {
		    try {
			Object value = getAccessibleValue(object, field);
			toStringFieldValues.add(new TEHFieldValue(field.getName(), value));
		    } catch (Exception e) {
			throw new RuntimeException("TEH toString", e);
		    }
		}
	    }
	}
	return toStringFieldValues;
    }

    private List<Field> extractToStringFields(Class<? extends Object> objectClazz) {
	if (toStringFieldsMap.containsKey(objectClazz)) {
	    return toStringFieldsMap.get(objectClazz);
	}
	List<Field> toStringFields = new ArrayList<Field>();
	for (Field declaredField : objectClazz.getDeclaredFields()) {
	    if (declaredField.isAnnotationPresent(ToString.class) || declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
		toStringFields.add(declaredField);
	    }
	}
	toStringFieldsMap.put(objectClazz, toStringFields);
	return toStringFields;
    }

    public Map<String, TEHFieldValue> extractEqualsFieldValues(Object object) {
	Map<String, TEHFieldValue> equalsFieldValues = new HashMap<String, TEHFieldValue>();
	if (isTEHActivated(object)) {
	    for (Class<? extends Object> clazz : getClassHierarchy(object.getClass())) {
		for (Field field : extractEqualsFields(clazz)) {
		    try {
			Object value = getAccessibleValue(object, field);
			if (value != null) {
			    equalsFieldValues.put(field.getName(), new TEHFieldValue(field.getName(), value));
			}
		    } catch (Exception e) {
			throw new RuntimeException("TEH equals", e);
		    }
		}
	    }
	}
	return equalsFieldValues;
    }

    private List<Field> extractEqualsFields(Class<? extends Object> objectClazz) {
	if (equalsFieldsMap.containsKey(objectClazz)) {
	    return equalsFieldsMap.get(objectClazz);
	}
	List<Field> equalsFields = new ArrayList<Field>();
	for (Field declaredField : objectClazz.getDeclaredFields()) {
	    if (declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
		equalsFields.add(declaredField);
	    }
	}
	equalsFieldsMap.put(objectClazz, equalsFields);
	return equalsFields;
    }

    public List<Object> extractHashCodeValues(Object object) {
	List<Object> hashCodeFieldValues = new ArrayList<Object>();
	if (isTEHActivated(object)) {
	    for (Class<? extends Object> clazz : getClassHierarchy(object.getClass())) {
		for (Field field : extractHashCodeFields(clazz)) {
		    try {
			Object value = getAccessibleValue(object, field);
			if (value != null) {
			    hashCodeFieldValues.add(value);
			}
		    } catch (Exception e) {
			throw new RuntimeException("TEH hashCode", e);
		    }
		}
	    }
	}
	return hashCodeFieldValues;
    }

    private List<Field> extractHashCodeFields(Class<? extends Object> objectClazz) {
	if (hahsCodeFieldsMap.containsKey(objectClazz)) {
	    return hahsCodeFieldsMap.get(objectClazz);
	}
	List<Field> hashCodeFields = new ArrayList<Field>();
	for (Field declaredField : objectClazz.getDeclaredFields()) {
	    if (declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
		hashCodeFields.add(declaredField);
	    }
	}
	hahsCodeFieldsMap.put(objectClazz, hashCodeFields);
	return hashCodeFields;
    }

    private Object getAccessibleValue(Object object, Field declaredField) throws IllegalAccessException {
	if (!declaredField.isAccessible()) {
	    declaredField.setAccessible(true);
	}
	return declaredField.get(object);
    }

    private List<Class<? extends Object>> getClassHierarchy(Class<? extends Object> objectClazz) {
	if (classesMap.containsKey(objectClazz)) {
	    return classesMap.get(objectClazz);
	}
	List<Class<? extends Object>> classHierarchy = new ArrayList<Class<? extends Object>>();
	Class<? extends Object> clazz = objectClazz;
	do {
	    classHierarchy.add(clazz);
	    clazz = clazz.getSuperclass();
	} while (clazz != null);
	classesMap.put(objectClazz, classHierarchy);
	return classHierarchy;
    }
}
