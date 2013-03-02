package jscl;

import jscl.text.msg.JsclMessage;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.msg.Message;
import org.solovyev.common.msg.MessageLevel;
import org.solovyev.common.msg.MessageType;

import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: 12/6/11
 * Time: 11:32 AM
 */
public abstract class AbstractJsclArithmeticException extends ArithmeticException implements Message {

    @NotNull
    private final Message message;

    public AbstractJsclArithmeticException(@NotNull String messageCode, Object... parameters) {
        this.message = new JsclMessage(messageCode, MessageType.error, parameters);
    }

    public AbstractJsclArithmeticException(@NotNull Message message) {
        this.message = message;
    }

    @NotNull
    public String getMessageCode() {
        return this.message.getMessageCode();
    }

    @NotNull
    public List<Object> getParameters() {
        return this.message.getParameters();
    }

    @NotNull
    @Override
    public MessageLevel getMessageLevel() {
        return this.message.getMessageLevel();
    }

    @NotNull
    @Override
    public String getLocalizedMessage(@NotNull Locale locale) {
        return this.message.getLocalizedMessage(locale);
    }

    @NotNull
    @Override
    public String getLocalizedMessage() {
        return this.getLocalizedMessage(Locale.getDefault());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractJsclArithmeticException that = (AbstractJsclArithmeticException) o;

        if (!message.equals(that.message)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}
