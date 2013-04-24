package teh.perf;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import teh.annotations.ToString;
import teh.annotations.ToStringEquals;
import teh.annotations.ToStringEqualsHashCode;
import teh.utils.TEHObject;

public class PerfTest {

    private class MyTEHObject extends TEHObject {

	public MyTEHObject(Long a, String b, int c) {
	    super();
	    this.a = a;
	    this.b = b;
	    this.c = c;
	}

	@ToStringEqualsHashCode
	Long a;

	@ToStringEquals
	String b;

	@ToString
	int c;

    }

    @Test(timeout = 10000)
    public void testTEH_sameObject() throws Exception {
	MyTEHObject teh = new MyTEHObject(1L, "2", 3);
	MyTEHObject other = new MyTEHObject(4L, "5", 6);
	for (int i = 0; i < 1000000; i++) {
	    teh.toString();
	    teh.equals(other);
	    teh.hashCode();
	}
    }

    @Test(timeout = 10000)
    public void testTEH_newObject() throws Exception {
	for (int i = 0; i < 1000000; i++) {
	    MyTEHObject teh = new MyTEHObject(1L, "2", 3);
	    MyTEHObject other = new MyTEHObject(4L, "5", 6);
	    teh.toString();
	    teh.equals(other);
	    teh.hashCode();
	}
    }

    @Test(timeout = 10000)
    public void testTEH_Concurrency() throws Exception {
	ExecutorService executor = Executors.newFixedThreadPool(10);
	for (int i = 0; i < 1000000; i++) {
	    executor.submit(new Callable<String>() {
		@Override
		public String call() throws Exception {
		    // System.out.println("Thread#" +
		    // Thread.currentThread().getId());
		    MyTEHObject teh = new MyTEHObject(1L, "2", 3);
		    MyTEHObject other = new MyTEHObject(4L, "5", 6);
		    teh.hashCode();
		    teh.equals(other);
		    return teh.toString();
		}
	    });
	}
	executor.shutdown();
	assertTrue(executor.awaitTermination(10000, TimeUnit.MILLISECONDS));
    }

}
