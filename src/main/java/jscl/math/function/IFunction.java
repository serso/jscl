package jscl.math.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.solovyev.common.math.MathEntity;

import java.util.List;

public interface IFunction extends MathEntity {

	@Nonnull
	String getContent();

	@Nullable
	String getDescription();

	String toJava();

	@Nonnull
	List<String> getParameterNames();
}
