package teh.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.text.pattern.Patterns.anyCharacter;
import static org.hamcrest.text.pattern.Patterns.oneOrMore;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.hamcrest.text.pattern.Patterns.text;

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
		@SuppressWarnings("unused")
		@ToStringEqualsHashCode
		private String priv;

		public PojoWithPrivateAttribute(String priv) {
			this.priv = priv;
		}
	}

	@Test
	public void testToStringObject() {
		String toString = TEHUtils.toString(new Pojo(1, "2"));
		assertThat(
				toString,
				new PatternMatcher(sequence(
						text("teh.utils.TEHUtilsTest$Pojo@"),
						oneOrMore(anyCharacter()), text("[a=1,b=2,c=<null>]"))));
	}

	@Test
	public void testToStringObjectRecursive() {
		String toString = TEHUtils.toString(new Pojo(1, "2", new Pojo(3, "4")));
		assertThat(
				toString,
				new PatternMatcher(sequence(
						text("teh.utils.TEHUtilsTest$Pojo@"),
						oneOrMore(anyCharacter()),
						text("[a=1,b=2,c=teh.utils.TEHUtilsTest$Pojo@"),
						oneOrMore(anyCharacter()), text("[a=3,b=4,c=<null>]]"))));
	}

	@Test
	public void testEqualsObjectObject() {
		assertThat(TEHUtils.equals(new Pojo(1, "2"), new Pojo(1, "2")),
				is(true));
		assertThat(TEHUtils.equals(new Pojo(1, "2"), new Pojo(1, "3")),
				is(false));
	}

	@Test
	public void testEqualsObjectObjectSubClass() {
		assertThat(TEHUtils.equals(new SubPojo(1, "2"), new SubPojo(1, "2")),
				is(true));
		assertThat(TEHUtils.equals(new SubPojo(1, "2"), new SubPojo(1, "3")),
				is(false));
	}

	@Test
	public void testHashCodeObject() {
		assertThat(TEHUtils.hashCode(new Pojo(1)),
				is(TEHUtils.hashCode(new Pojo(1))));
		assertThat(TEHUtils.hashCode(new Pojo(1)),
				is(not(TEHUtils.hashCode(new Pojo(2)))));
	}

	@Test(expected = NullPointerException.class)
	public void testHashCodeForANullObjectBehavesLikeCallingTheMethodOnNull() {
		TEHUtils.hashCode(null);
	}

	@Test(expected = NullPointerException.class)
	public void testToStringForANullObjectBehavesLikeCallingTheMethodOnNull() {
		TEHUtils.toString(null);
	}

	@Test(expected = NullPointerException.class)
	public void testEqualsForANullObjectBehavesLikeCallingTheMethodOnNull() {
		TEHUtils.equals(null, new Object());
	}

	@Test
	public void testHashCodePonderateFields() {
		assertThat(TEHUtils.hashCode(new SubPojo(1, 2)),
				is(not(TEHUtils.hashCode(new SubPojo(2, 1)))));
	}

	@Test
	public void testHashCodeWithoutAttributeFallBackToDefaultImpl() {
		assertThat(TEHUtils.hashCode(new PojoWithoutAttribute()),
				is(not(TEHUtils.hashCode(new PojoWithoutAttribute()))));
	}

	@Test
	public void testPrivateAttributeToStringWarn() {
		assertThat(TEHUtils.toString(new PojoWithPrivateAttribute("priv")),
				not(containsString("IllegalAccessException")));
	}

	@Test
	public void testPrivateAttributeEqualsWarn() {
		assertThat(TEHUtils.equals(new PojoWithPrivateAttribute("priv"),
				new PojoWithPrivateAttribute("priv")), is(true));
	}

	@Test
	public void testPrivateAttributeHashCodeWarn() {
		assertThat(TEHUtils.hashCode(new PojoWithPrivateAttribute("priv")),
				is(TEHUtils.hashCode(new PojoWithPrivateAttribute("priv"))));
	}
}
