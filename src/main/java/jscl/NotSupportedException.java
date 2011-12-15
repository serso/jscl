package jscl;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 12/15/11
 * Time: 10:45 PM
 */
public class NotSupportedException extends AbstractJsclArithmeticException {

	public NotSupportedException(@NotNull String messageCode, Object... parameters) {
		super(messageCode, parameters);
	}
}
