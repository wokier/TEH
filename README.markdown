# TEH
TEH stands for __T__oString __E__quals __H__ashCode

- - -
TEH uses annotations to implement toString, equals and hashCode, and enforces these 2 rules

 *	any attribute used for hashCode will be used for equals and toString
 *	any attribute used for equals will be used for toString

- - -
The annotations are 

 *	@ToString
 *	@ToStringEquals
 *	@ToStringEqualsHashCode

- - -
You can activate TEH on a given class by 2 ways
## inheritance
	class MyTEHObject extends TEHObject{
		
		@ToStringEqualsHashCode
		long id;

		@ToString
		String description;	
	...

## annotation
	@TEH
	class MyTEHObject{
		
		@ToStringEqualsHashCode
		long id;

		@ToString
		String description
	
		public String toString() {
			return TEHUtils.toString(this);
		}
	
		public boolean equals(Object other) {
			return TEHUtils.equals(this, other);
		}
	
		public int hashCode() {
			return TEHUtils.hashCode(this,super.hashCode());
		}
	...

- - -
## 2 unbreakable rules explanations

### toString javadoc
>Returns a string representation of the object. In general, the <code>toString</code> method returns a string that "textually represents" this object. The result should be a concise but informative representation that is easy for a person to read. It is recommended that all subclasses override this method. <p> The <code>toString</code> method for class <code>Object</code> returns a string consisting of the name of the class of which the object is an instance, the at-sign character `<code>@</code>', and the unsigned hexadecimal representation of the hash code of the object. In other words, this method returns a string equal to the value of: <blockquote> <pre> getClass().getName() + '@' + Integer.toHexString(hashCode())* </pre></blockquote>

### equals javadoc
>Indicates whether some other object is "equal to" this one. <p> The <code>equals</code> method implements an equivalence relation on non-null object references: <ul> <li>It is <i>reflexive</i>: for any non-null reference value <code>x</code>, <code>x.equals(x)</code> should return <code>true</code>. <li>It is <i>symmetric</i>: for any non-null reference values <code>x</code> and <code>y</code>, <code>x.equals(y)</code> should return <code>true</code> if and only if <code>y.equals(x)</code> returns <code>true</code>. <li>It is <i>transitive</i>: for any non-null reference values <code>x</code>, <code>y</code>, and <code>z</code>, if <code>x.equals(y)</code> returns <code>true</code> and <code>y.equals(z)</code> returns <code>true</code>, then <code>x.equals(z)</code> should return <code>true</code>. <li>It is <i>consistent</i>: for any non-null reference values <code>x</code> and <code>y</code>, multiple invocations of <tt>x.equals(y)</tt> consistently return <code>true</code> or consistently return <code>false</code>, provided no information used in <code>equals</code> comparisons on the objects is modified. <li>For any non-null reference value <code>x</code>, <code>x.equals(null)</code> should return <code>false</code>. </ul> <p> The <tt>equals</tt> method for class <code>Object</code> implements the most discriminating possible equivalence relation on objects; that is, for any non-null reference values <code>x</code> and <code>y</code>, this method returns <code>true</code> if and only if <code>x</code> and <code>y</code> refer to the same object (<code>x == y</code> has the value <code>true</code>). <p> Note that it is generally necessary to override the <tt>hashCode</tt> method whenever this method is overridden, so as to maintain the general contract for the <tt>hashCode</tt> method, which states that equal objects must have equal hash codes. 

### hashcode javadoc
> Returns a hash code value for the object. This method is supported for the benefit of hashtables such as those provided by <code>java.util.Hashtable</code>. <p> The general contract of <code>hashCode</code> is: <ul> <li>Whenever it is invoked on the same object more than once during an execution of a Java application, the <tt>hashCode</tt> method must consistently return the same integer, provided no information used in <tt>equals</tt> comparisons on the object is modified. This integer need not remain consistent from one execution of an application to another execution of the same application. <li>If two objects are equal according to the <tt>equals(Object)</tt> method, then calling the <code>hashCode</code> method on each of the two objects must produce the same integer result. <li>It is <em>not</em> required that if two objects are unequal according to the {@link java.lang.Object#equals(java.lang.Object)} method, then calling the <tt>hashCode</tt> method on each of the two objects must produce distinct integer results.  However, the programmer should be aware that producing distinct integer results for unequal objects may improve the performance of hashtables.</ul><p> As much as is reasonably practical, the hashCode method defined by class <tt>Object</tt> does return distinct integers for distinct objects. (This is typically implemented by converting the internal address of the object into an integer, but this implementation technique is not required by the Java<font size="-2"><sup>TM</sup></font> programming language.)


### Complements
 * toString is used for logs, debugger display, junit assertions failures, but rarely for user display
 * equals is used for object equality, based on strong business understanding, or technical unique identification, to compare objects on business rules and junit assertions
 * hashCode is used for internal structure optimisations and is a part of the default toString value

###Rules
 *  any attribute used for hashCode will be used for equals and toString
 > If two objects are equal according to the <tt>equals(Object)</tt> method, then calling the <code>hashCode</code> method on each of the two objects must produce the same integer result. (from hashcode javadoc)

 *	any attribute used for equals will be used for toString

> When a junit assertion like assertEquals is failing, it is preferable that the generated message gives the difference between objects.
- - -
## Release Notes
0.5 2012-06-16 gwt-teh