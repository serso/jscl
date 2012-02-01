package jscl2;

import jscl.AbstractJsclArithmeticException;
import jscl.text.msg.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 12/6/11
 * Time: 11:34 AM
 */
public class NumeralBaseException extends AbstractJsclArithmeticException {

	public NumeralBaseException(double value) {
		super(Messages.msg_17, value);
	}
}
