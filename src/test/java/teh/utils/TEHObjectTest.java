package teh.utils;

import static org.hamcrest.text.pattern.Patterns.anyCharacter;
import static org.hamcrest.text.pattern.Patterns.oneOrMore;
import static org.hamcrest.text.pattern.Patterns.sequence;
import static org.hamcrest.text.pattern.Patterns.text;
import static org.junit.Assert.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.text.pattern.PatternMatcher;
import org.junit.Test;

import teh.annotations.TEH;
import teh.annotations.ToStringEqualsHashCode;

public class TEHObjectTest {

    @TEH
    private class MyTEHObject extends TEHObject {
        
        public MyTEHObject(int a) {
            super();
            this.a = a;
        }

        @ToStringEqualsHashCode
        int a;
    }
    

    @Test
    public void testToString() {
        MatcherAssert.assertThat(new MyTEHObject(1).toString(), new PatternMatcher(sequence(text("teh.utils.TEHObjectTest$MyTEHObject@"),oneOrMore(anyCharacter()),text("[a=1]"))));
    }

    @Test
    public void testEqualsObject() {
        assertEquals(new MyTEHObject(1),new MyTEHObject(1));
        assertFalse(new MyTEHObject(1).equals(new MyTEHObject(2)));
    }

    @Test
    public void testHashCode() {
        assertEquals(new MyTEHObject(1).hashCode(),new MyTEHObject(1).hashCode());
        assertFalse(new MyTEHObject(1).hashCode() == new MyTEHObject(2).hashCode());
    }
}
