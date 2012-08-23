package teh.perf;

import org.junit.Test;

import teh.annotations.ToString;
import teh.annotations.ToStringEquals;
import teh.annotations.ToStringEqualsHashCode;
import teh.utils.TEHObject;

public class PerfTest {

	private class MyTEHObject extends TEHObject {

		@ToStringEqualsHashCode
		Long a;

		@ToStringEquals
		String b;

		@ToString
		int c;

		public MyTEHObject(Long a, String b, int c) {

			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}

	}

	@Test(timeout = 10000)
	public void testTEH_newObject() throws Exception {

		for (int i = 0; i < 100000; i++) {
			MyTEHObject teh = new MyTEHObject(1L, "2", 3);
			MyTEHObject other = new MyTEHObject(4L, "5", 6);
			teh.toString();
			teh.equals(other);
			teh.hashCode();
		}
	}

	@Test(timeout = 10000)
	public void testTEH_sameObject() throws Exception {

		MyTEHObject teh = new MyTEHObject(1L, "2", 3);
		MyTEHObject other = new MyTEHObject(4L, "5", 6);
		for (int i = 0; i < 100000; i++) {
			teh.toString();
			teh.equals(other);
			teh.hashCode();
		}
	}

}
