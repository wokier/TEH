package teh.utils;

import java.util.List;
import java.util.Map;

import teh.fields.TEHFields;
import teh.fields.TEHFields.TEHFieldValue;
import teh.fields.TEHFieldsFactory;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

/**
 * TEH core implementation
 * 
 */
public class TEHUtils {

    public static String toString(Object object) {
	return toString(object, TEHFieldsFactory.get());
    }

    protected static String toString(Object object, TEHFields tehFields) {
	if (tehFields.isTEHActivated(object)) {
	    ToStringHelper toStringHelper = Objects.toStringHelper(object.getClass().getName() + '@' + object.hashCode());
	    for (TEHFieldValue fieldValue : tehFields.extractToStringFieldValues(object)) {
		toStringHelper.add(fieldValue.fieldName, String.valueOf(fieldValue.value));
	    }
	    return toStringHelper.toString();
	}
	return String.valueOf(object);
    }

    public static boolean equals(Object object, Object other) {
	return equals(object, other, TEHFieldsFactory.get());
    }

    public static boolean equals(Object object, Object other, TEHFields tehFields) {
	if (tehFields.isTEHActivated(object) && tehFields.isTEHActivated(other)) {
	    Map<String, TEHFieldValue> objectEqualsFieldValues = tehFields.extractEqualsFieldValues(object);
	    if (objectEqualsFieldValues.isEmpty()) {
		return object == other;
	    }
	    Map<String, TEHFieldValue> otherEqualsFieldValues = tehFields.extractEqualsFieldValues(other);
	    for (TEHFieldValue objectFieldValue : objectEqualsFieldValues.values()) {
		TEHFieldValue otherFieldValue = otherEqualsFieldValues.get(objectFieldValue.fieldName);
		if (!Objects.equal(objectFieldValue.value, otherFieldValue.value)) {
		    return false;
		}
	    }
	    return true;
	}
	return Objects.equal(object, other);
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

    public static int hashCode(Object object, int nativeHashCode) {
	return hashCode(object, nativeHashCode, TEHFieldsFactory.get());
    }

    /**
     * @since 0.4
     */
    public static int hashCode(Object object, int nativeHashCode, TEHFields tehFields) {
	if (tehFields.isTEHActivated(object)) {
	    List<Object> hashCodeValues = tehFields.extractHashCodeValues(object);
	    if (!hashCodeValues.isEmpty()) {
		return Objects.hashCode(hashCodeValues);
	    }
	}
	return nativeHashCode;
    }
}
