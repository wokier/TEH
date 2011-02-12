### TEH
TEH stands for __T__oString __E__quals __H__ashCode
- - -
TEH uses annotations to implements toString, equals and hashCode, and forces these 2 rules :
* any attribute used for hashCode will be used for equals and toString
* any attribute used for equals will be used for toString