package teh.utils;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import teh.annotations.TEH;
import teh.annotations.ToString;
import teh.annotations.ToStringEquals;
import teh.annotations.ToStringEqualsHashCode;

public class TEHUtils {
    private static final int BUILDER_INITIAL_HASHCODE = 17;
    private static final Logger LOGGER = Logger.getLogger("TEH");

    public static String toString(Object object) {
	if (object == null) {
	    return "<null>";
	}
	Class<? extends Object> clazz = object.getClass();
	if (isTEHActivated(object, clazz)) {
	    ToStringBuilder builder = new ToStringBuilder(object);
	    do {
		for (Field declaredField : clazz.getDeclaredFields()) {
		    if (declaredField.isAnnotationPresent(ToString.class) || declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
			builder.append(declaredField.getName(), getToStringValue(object, declaredField));
		    }
		}
		clazz = clazz.getSuperclass();
	    } while (clazz != null);
	    return builder.toString();
	}
	return object.toString();
    }

    private static boolean isTEHActivated(Object object, Class<? extends Object> clazz) {
	return object instanceof TEHObject || clazz.isAnnotationPresent(TEH.class);
    }

    private static Object getToStringValue(Object object, Field declaredField) {
	try {
	    if (!declaredField.isAccessible()) {
		LOGGER.warning("The field " + declaredField.getName() + " on class " + object.getClass().getName()
			+ " has @ToStringEqualsHashCode or @ToStringEquals or @ToString annotation, but was not visible for toString.");
		declaredField.setAccessible(true);
	    }
	    return toString(declaredField.get(object));
	} catch (Exception e) {
	    return e;
	}
    }

    public static boolean equals(Object object, Object other) {
	if (object == null) {
	    return other == null;
	}
	if (object == other) {
	    return true;
	}
	Class<? extends Object> clazz = object.getClass();
	boolean atLeastOneTEHField = false;
	if (isTEHActivated(object, clazz)) {
	    do {
		for (Field declaredField : clazz.getDeclaredFields()) {
		    if (declaredField.isAnnotationPresent(ToStringEquals.class) || declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
			if (!getEqualsValue(object, other, declaredField)) {
			    return false;
			} else {
			    atLeastOneTEHField = atLeastOneTEHField || true;
			}

		    }
		}
		clazz = clazz.getSuperclass();
	    } while (clazz != null);
	    if (atLeastOneTEHField) {
		return true;
	    } else {
		return object == other;
	    }
	}
	return object.equals(other);
    }

    private static boolean getEqualsValue(Object object, Object other, Field declaredField) {
	try {
	    if (!declaredField.isAccessible()) {
		LOGGER.warning("The field " + declaredField.getName() + " on class " + object.getClass().getName()
			+ " has @ToStringEqualsHashCode or @ToStringEquals annotation, but was not visible for equals.");
		declaredField.setAccessible(true);
	    }
	    return equals(declaredField.get(object), declaredField.get(other));
	} catch (Exception e) {
	    return false;
	}
    }

    /**
     * @deprecated use {@link #hashCode(Object, int)} instead with
     *             super.hashCode()
     */
    protected static int hashCode(Object object) {
	if (object == null) {
	    return 0;
	}
	return hashCode(object, 0);
    }

    /**
     * @since 0.4
     */
    public static int hashCode(Object object, int nativeHashCode) {
	if (object == null) {
	    return 0;
	}
	Class<? extends Object> clazz = object.getClass();
	if (isTEHActivated(object, clazz)) {

	    HashCodeBuilder builder = new HashCodeBuilder();
	    do {
		for (Field declaredField : clazz.getDeclaredFields()) {
		    if (declaredField.isAnnotationPresent(ToStringEqualsHashCode.class)) {
			builder = addHashCodeValue(object, declaredField, builder);
		    }
		}
		clazz = clazz.getSuperclass();
	    } while (clazz != null);
	    if (builder.toHashCode() != BUILDER_INITIAL_HASHCODE) {
		return builder.toHashCode();
	    }
	}
	return nativeHashCode;
    }

    private static HashCodeBuilder addHashCodeValue(Object object, Field declaredField, HashCodeBuilder builder) {
	try {
	    if (!declaredField.isAccessible()) {
		LOGGER.warning("The field " + declaredField.getName() + " on class " + object.getClass().getName() + " has @ToStringEqualsHashCode annotation, but was not visible for hashCode.");
		declaredField.setAccessible(true);
	    }
	    Object objectFieldValue = declaredField.get(object);
	    return builder.append(hashCode(objectFieldValue, objectFieldValue.hashCode()));
	} catch (Exception e) {
	    return builder;
	}
    }

}
