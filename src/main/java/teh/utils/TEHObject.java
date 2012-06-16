package teh.utils;

import java.io.Serializable;

import teh.annotations.TEH;
import teh.fields.TEHFields;
import teh.fields.TEHFieldsFactory;

/**
 * Super class that provides default implementations for {@link #equals(Object)}
 * , {@link #toString()} and {@link #hashCode()}. If you don't want or cannot
 * inherit this class, just add the @TEH annotatiotn and override the 3 methods
 * by using TEHUtils, as in this class.<br>
 * 
 * Note that the @TEH annotation is not necessary when inheriting from this
 * class.
 */
@TEH
public class TEHObject implements Serializable {

    @Override
    public String toString() {
	return TEHUtils.toString(this, getTEHFields());
    }

    @Override
    public boolean equals(Object other) {
	return TEHUtils.equals(this, other, getTEHFields());
    }

    @Override
    public int hashCode() {
	return TEHUtils.hashCode(this, nativeHashCode(), getTEHFields());
    }

    /**
     * @see Object#hashCode()
     * @return
     */
    public final int nativeHashCode() {
	return super.hashCode();
    }

    /**
     * Gives the TEH fields Implementation to use
     * 
     * @return
     */
    protected TEHFields getTEHFields() {
	return TEHFieldsFactory.get();
    }

}
