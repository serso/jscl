package jscl;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.msg.Message;

/**
 * User: serso
 * Date: 12/6/11
 * Time: 11:34 AM
 */
public class NumeralBaseException extends AbstractJsclArithmeticException {

	public NumeralBaseException(@NotNull Message message) {
		super(message);
	}

	public NumeralBaseException(@NotNull String messageCode, Object... parameters) {
		super(messageCode, parameters);
	}
}
