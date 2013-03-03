package jscl;

import jscl.math.function.CustomFunction;
import jscl.text.msg.Messages;
import javax.annotation.Nonnull;
import org.solovyev.common.msg.Message;

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
