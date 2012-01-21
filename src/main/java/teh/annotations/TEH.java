package teh.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that the class with this annotation uses TEH.
 * <p>
 * TEH uses annotations to implements ToString, Equals and HashCode, and
 * enforces the following rules:
 * <li>any attribute used for hashCode will be used for equals and toString, and
 * </li>
 * <li>any attribute used for equals will be used for toString.</li>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TEH {
}
