### TEH
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
	...

## annotation
	@TEH
	class MyTEHObject{
		public String toString() {
			return TEHUtils.toString(this);
		}
	
		public boolean equals(Object other) {
			return TEHUtils.equals(this, other);
		}
	
		public int hashCode() {
			return TEHUtils.hashCode(this);
		}
	...
