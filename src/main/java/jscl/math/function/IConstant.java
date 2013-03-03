package jscl.math.function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.solovyev.common.math.MathEntity;

/**
 * User: serso
 * Date: 11/10/11
 * Time: 6:01 PM
 */
public interface IConstant extends MathEntity {

    @Nonnull
    Constant getConstant();

    @Nullable
    String getDescription();

    boolean isDefined();

    @Nullable
    String getValue();

    @Nullable
    Double getDoubleValue();

    @Nonnull
    String toJava();
}
