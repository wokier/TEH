package teh.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.pattern.Patterns.anyCharacter;
import static org.hamcrest.text.pattern.Patterns.oneOrMore;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.hamcrest.text.pattern.Patterns.text;

import org.hamcrest.text.pattern.PatternMatcher;
import org.junit.Test;

import teh.annotations.ToStringEquals;
import teh.annotations.ToStringEqualsHashCode;

public class TEHObjectTest {

	// @TEH is not necessary when you extends TEHObject
	private class MyTEHObject extends TEHObject {
		public MyTEHObject(int a) {
			this.a = a;
		}

		@SuppressWarnings("unused")
		@ToStringEqualsHashCode
		int a;
	}

	private class MyTEHObjectNoHashCode extends TEHObject {
		public MyTEHObjectNoHashCode(int a) {
			this.a = a;
		}

		@SuppressWarnings("unused")
		@ToStringEquals
		int a;

	}

	@Test
	public void testToString() {
		assertThat(new MyTEHObject(1).toString(), new PatternMatcher(sequence(text("teh.utils.TEHObjectTest$MyTEHObject@"), oneOrMore(anyCharacter()), text("[a=1]"))));
	}

	@Test
	public void testEqualsObject() {
		assertThat(new MyTEHObject(1), is(new MyTEHObject(1)));
		assertThat(new MyTEHObject(1), is(not(new MyTEHObject(2))));
	}

	@Test
	public void testHashCode() {
		assertThat(new MyTEHObject(1).hashCode(), is(new MyTEHObject(1).hashCode()));
		assertThat(new MyTEHObject(1).hashCode(), is(not(new MyTEHObject(2).hashCode())));
	}

	@Test
	public void testNoHashCode() {
		MyTEHObjectNoHashCode myTEHObjectNoHashCode = new MyTEHObjectNoHashCode(1);
		myTEHObjectNoHashCode.toString();
		myTEHObjectNoHashCode.hashCode();
	}
}
