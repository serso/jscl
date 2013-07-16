package jscl;

import jscl.math.function.CustomFunction;
import jscl.text.msg.Messages;
import org.solovyev.common.msg.Message;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 12/22/11
 * Time: 2:26 PM
 */
public class CustomFunctionCalculationException extends AbstractJsclArithmeticException {

	public CustomFunctionCalculationException(@Nonnull CustomFunction function, @Nonnull Message message) {
		super(Messages.msg_19, function.getName(), message);
	}
}
