package jscl.math.function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.IBuilder;
import org.solovyev.common.math.MathEntity;

/**
 * User: serso
 * Date: 11/7/11
 * Time: 12:06 PM
 */
public class ExtendedConstant implements MathEntity {

	@NotNull
	private Constant constant;

	@Nullable
	private Double value;

	@Nullable
	private String javaString;

	public static final class Builder implements IBuilder<ExtendedConstant> {
		@NotNull
		private Constant constant;

		@Nullable
		private Double value;

		@Nullable
		private String javaString;

		public Builder(@NotNull Constant constant, @Nullable Double value) {
			this.constant = constant;
			this.value = value;
		}

		public Builder setJavaString(@Nullable String javaString) {
			this.javaString = javaString;
			return this;
		}

		@NotNull
		@Override
		public ExtendedConstant create() {
			final ExtendedConstant result = new ExtendedConstant();

			result.constant = constant;
			result.value = value;
			result.javaString = javaString;

			return result;
		}
	}

	ExtendedConstant() {
	}

	ExtendedConstant(@NotNull Constant constant,
					 @Nullable Double value,
					 @Nullable String javaString) {
		this.constant = constant;
		this.value = value;
		this.javaString = javaString;
	}

	@NotNull
	@Override
	public String getName() {
		return this.constant.getName();
	}

	@Override
	public boolean isSystem() {
		return this.constant.isSystem();
	}

	@Override
	public void copy(@NotNull MathEntity that) {
		this.constant.copy(that);
		if (that instanceof ExtendedConstant) {
			this.value = ((ExtendedConstant) that).value;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExtendedConstant that = (ExtendedConstant) o;

		if (!constant.equals(that.constant)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return constant.hashCode();
	}

	@NotNull
	public Constant getConstant() {
		return constant;
	}

	@Nullable
	public Double getValue() {
		return value;
	}

	@NotNull
	public String toJava() {
		if (javaString != null) {
			return javaString;
		} else if ( value != null ){
			return String.valueOf(value);
		} else {
			return constant.getName();
		}
	}
}
