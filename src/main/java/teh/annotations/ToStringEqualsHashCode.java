package teh.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a field as used for toString, equals and hashCode
 * 
 * @see Object#toString()
 * @see Object#equals(Object)
 * @see Object#hashCode()
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ToStringEqualsHashCode {

}
