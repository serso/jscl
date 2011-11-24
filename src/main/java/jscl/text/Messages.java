package jscl.text;

/**
 * User: serso
 * Date: 11/24/11
 * Time: 5:47 PM
 */
public final class Messages {


	// not intended for instantiation
	private Messages() {
		throw new AssertionError();
	}

	/** Premature end of processing */
	public static final String MSG_1 = "MSG_1";

	/** There is no operator with name: {0} */
	public static final String MSG_2 = "MSG_2";

	/** Operator name is not valid: {0} */
	public static final String 	MSG_3 = "MSG_3";

	/** Postfix function with name {0} doesn't exist */
	public static final String 	MSG_4 = "MSG_4";

	/** Constant name must start with character */
	public static final String 	MSG_5 = "MSG_5";

	/** Cannot be implicit function - usual function or operator with same name is defined: {0} */
	public static final String 	MSG_6 = "MSG_6";

	/** Digit is expected */
	public static final String 	MSG_7 = "MSG_7";

	/** Invalid number: {0} */
	public static final String 	MSG_8 = "MSG_8";

	/** First letter of number must be digit */
	public static final String 	MSG_9 = "MSG_9";

	/** Expected characters are {0} or {1} */
	public static final String 	MSG_10 = "MSG_10";

	/** Expected characters are {0} */
	public static final String 	MSG_11 = "MSG_11";

	/** Expected character is {0} */
	public static final String 	MSG_12 = "MSG_12";

	/** Function name is not valid: {0} */
	public static final String 	MSG_13 = "MSG_13";

	/** Expected number of parameters differs from actual {0} */
	public static final String MSG_14 = "MSG_14";
}
