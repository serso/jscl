package jscl.math.function;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.AbstractMathRegistry;

/**
 * User: serso
 * Date: 11/7/11
 * Time: 11:59 AM
 */
public class ConstantsRegistry extends AbstractMathRegistry<IConstant> {

	private final static ConstantsRegistry instance = new ConstantsRegistry();

	static {
		instance.add(new PiConstant());
		instance.add(new ExtendedConstant(Constant.INF_CONST, Double.POSITIVE_INFINITY, "JsclDouble.valueOf(Double.POSITIVE_INFINITY)"));
		instance.add(new ExtendedConstant(Constant.INF_CONST2, Double.POSITIVE_INFINITY, "JsclDouble.valueOf(Double.POSITIVE_INFINITY)"));
		instance.add(new ExtendedConstant(Constant.I_CONST, "√(-1)", null));
	}

	private ConstantsRegistry() {
	}

	@NotNull
	public static ConstantsRegistry getInstance() {
		return instance;
	}
}
