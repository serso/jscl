package jscl;

import jscl.math.function.CustomFunction;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.msg.Message;

/**
 * User: serso
 * Date: 12/22/11
 * Time: 2:26 PM
 */
public class CustomFunctionCalculationException extends AbstractJsclArithmeticException {

	public CustomFunctionCalculationException(@NotNull CustomFunction function, @NotNull Message message) {
		super(Messages.msg_19, function.getName(), message);
	}
}
