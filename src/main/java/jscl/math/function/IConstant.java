package jscl.math.function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.MathEntity;

/**
 * User: serso
 * Date: 11/10/11
 * Time: 6:01 PM
 */
public interface IConstant extends MathEntity {

	@NotNull
	Constant getConstant();

	@Nullable
	String getDescription();

	boolean isDefined();

	@Nullable
	String getValue();

	@Nullable
	Double getDoubleValue();

	@NotNull
	String toJava();
}
