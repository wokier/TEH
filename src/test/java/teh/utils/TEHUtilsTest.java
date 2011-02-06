package teh.utils;

import static org.hamcrest.text.pattern.Patterns.anyCharacter;
import static org.hamcrest.text.pattern.Patterns.oneOrMore;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.hamcrest.text.pattern.Patterns.text;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.hamcrest.MatcherAssert;
import org.hamcrest.text.pattern.PatternMatcher;
import org.junit.Test;

import teh.annotations.TEH;
import teh.annotations.ToString;
import teh.annotations.ToStringEquals;
import teh.annotations.ToStringEqualsHashCode;

public class TEHUtilsTest {

    @TEH
    class Pojo {

	public Pojo(int a, String b, Pojo c) {
	    this(a, b);
	    this.c = c;
	}

	public Pojo(int a, String b) {
	    this(a);
	    this.b = b;
	}

	public Pojo(int a) {
	    super();
	    this.a = a;
	}

	@ToStringEqualsHashCode
	int a;

	@ToStringEquals
	String b;

	@ToString
	Pojo c;

    }

    @TEH
    class SubPojo extends Pojo {
	public SubPojo(int a, int d) {
	    super(a);
	    this.d = d;
	}

	public SubPojo(int a, String b) {
	    super(a, b);
	}

	@ToStringEqualsHashCode
	int d;
    }

    @TEH
    class PojoWithoutAttribute {

    }

    @TEH
    class PojoWithPrivateAttribute {
	@ToStringEqualsHashCode
	private String priv;

	public PojoWithPrivateAttribute(String priv) {
	    super();
	    this.priv = priv;
	}

    }

    @Test
    public void testToStringObject() {
	String toString = TEHUtils.toString(new Pojo(1, "2"));
	MatcherAssert.assertThat(toString, new PatternMatcher(sequence(text("teh.utils.TEHUtilsTest$Pojo@"), oneOrMore(anyCharacter()), text("[a=1,b=2,c=<null>]"))));
    }

    @Test
    public void testToStringObjectRecursive() {
	String toString = TEHUtils.toString(new Pojo(1, "2", new Pojo(3, "4")));
	MatcherAssert.assertThat(
		toString,
		new PatternMatcher(sequence(text("teh.utils.TEHUtilsTest$Pojo@"), oneOrMore(anyCharacter()), text("[a=1,b=2,c=teh.utils.TEHUtilsTest$Pojo@"), oneOrMore(anyCharacter()),
			text("[a=3,b=4,c=<null>]]"))));
    }

    @Test
    public void testEqualsObjectObject() {
	assertTrue(TEHUtils.equals(new Pojo(1, "2"), new Pojo(1, "2")));
	assertFalse(TEHUtils.equals(new Pojo(1, "2"), new Pojo(1, "3")));
    }

    @Test
    public void testEqualsObjectObjectSubClass() {
	assertTrue(TEHUtils.equals(new SubPojo(1, "2"), new SubPojo(1, "2")));
	assertFalse(TEHUtils.equals(new SubPojo(1, "2"), new SubPojo(1, "3")));
    }

    @Test
    public void testHashCodeObject() {
	assertEquals(TEHUtils.hashCode(new Pojo(1)), TEHUtils.hashCode(new Pojo(1)));
	assertTrue(TEHUtils.hashCode(new Pojo(1)) != TEHUtils.hashCode(new Pojo(2)));
    }

    @Test
    public void testHashCodeObjectNull() {
	assertEquals(0, TEHUtils.hashCode(null));
    }

    @Test
    public void testHashCodePonderateFields() {
	assertTrue(TEHUtils.hashCode(new SubPojo(1, 2)) != TEHUtils.hashCode(new SubPojo(2, 1)));
    }

    @Test
    public void testHashCodeWithoutAttributeFallBackToDefaultImpl() {
	assertTrue(TEHUtils.hashCode(new PojoWithoutAttribute()) != TEHUtils.hashCode(new PojoWithoutAttribute()));
    }

    @Test
    public void testPrivateAttributeToStringWarn() {
	assertFalse(TEHUtils.toString(new PojoWithPrivateAttribute("priv")).contains("IllegalAccessException"));
    }

    @Test
    public void testPrivateAttributeEqualsWarn() {
	assertTrue(TEHUtils.equals(new PojoWithPrivateAttribute("priv"), new PojoWithPrivateAttribute("priv")));
    }

    @Test
    public void testPrivateAttributeHashCodeWarn() {
	assertEquals(TEHUtils.hashCode(new PojoWithPrivateAttribute("priv")), TEHUtils.hashCode(new PojoWithPrivateAttribute("priv")));
    }
}
