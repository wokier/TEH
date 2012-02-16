package teh.utils;

import teh.annotations.TEH;

/**
 * Super class that provides default implementations for {@link #equals(Object)}
 * , {@link #toString()} and {@link #hashCode()}. If you don't want or cannot
 * inherit this class, just override the 3 methods by using TEHUtils, as in this
 * class.<br>
 * 
 * Note that the @TEH annotation is not necessary when inheriting from this
 * class.
 */
@TEH
public class TEHObject {
	@Override
	public String toString() {
		return TEHUtils.toString(this);
	}

	@Override
	public boolean equals(Object other) {
		return TEHUtils.equals(this, other);
	}

	@Override
	public int hashCode() {
		return TEHUtils.hashCode(this, super.hashCode());
	}

}
