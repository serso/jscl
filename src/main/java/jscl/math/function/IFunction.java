package jscl.math.function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.math.MathEntity;

import java.util.List;

public interface IFunction extends MathEntity {

	@NotNull
	String getContent();

	@Nullable
	String getDescription();

	String toJava();

	@NotNull
	List<String> getParameterNames();
}
