package teh.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Indicate that this class uses TEH<br>
 * TEH uses annotations to implements ToString, Equals and hashCode, and forces these 2 rule:<br>
 * - any attribute used for hashCode will be used for equals and toString<br>
 * - any attribte used for equals will be used for toString
 *  
 * @author f_wauquier
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TEH {

}
