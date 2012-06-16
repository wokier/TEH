package teh.fields;

import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;

/**
 * Capable to extract TEH fields and values from a TEH Object.
 * 
 */
public interface TEHFields {

    /**
     * Field value
     * 
     */
    public static class TEHFieldValue {
	public final String fieldName;
	public final Object value;

	public TEHFieldValue(String fieldName, Object value) {
	    super();
	    this.fieldName = fieldName;
	    this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
	    if (obj instanceof TEHFieldValue) {
		return Objects.equal(fieldName, ((TEHFieldValue) obj).fieldName);
	    }
	    return false;
	}
    }

    /**
     * Detects TEH activation
     * 
     * @param object
     * @return
     */
    boolean isTEHActivated(Object object);

    /**
     * extract TEH toString values
     * 
     * @param object
     * @return
     */
    List<TEHFieldValue> extractToStringFieldValues(Object object);

    /**
     * extract TEH equals values
     * 
     * @param object
     * @return
     */
    Map<String, TEHFieldValue> extractEqualsFieldValues(Object object);

    /**
     * extract TEH hashcode values
     * 
     * @param object
     * @return
     */
    List<Object> extractHashCodeValues(Object object);

}
