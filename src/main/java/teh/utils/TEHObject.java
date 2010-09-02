package teh.utils;

import teh.annotations.TEH;

/**
 * Super class to use in order to use THE
 * If you don't want or cannot inherit this class, just override the 3 methods by using TEHUtils, as in this class.
 * @author f_wauquier
 *
 */
@TEH
public class TEHObject {

    /**
     * @see java.lang.Object#toString()
     * @see TEHUtils#toString(Object)
     */
    @Override
    public String toString() {
        return TEHUtils.toString(this);
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @see TEHUtils#equals(Object, Object)
     */
    @Override
    public boolean equals(Object other) {
        return TEHUtils.equals(this,other);
    }
    
    /**
     * @see java.lang.Object#hashCode()
     * @see TEHUtils#hashCode(Object)
     */
    @Override
    public int hashCode() {
        return TEHUtils.hashCode(this);
    }
    
}
