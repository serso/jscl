package jscl.text;

import jscl.text.msg.JsclMessage;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.msg.Message;
import org.solovyev.common.msg.MessageLevel;
import org.solovyev.common.msg.MessageType;

import java.util.List;
import java.util.Locale;

public class ParseException extends Exception implements Message {

    @NotNull
    private final Message message;

    private final int position;

    @NotNull
    private final String expression;

    public ParseException(@NotNull String messageCode, int position, @NotNull String expression, Object... parameters) {
        this.message = new JsclMessage(messageCode, MessageType.error, parameters);
        this.position = position;
        this.expression = expression;
    }

    public ParseException(@NotNull Message message, int position, @NotNull String expression) {
        this.message = message;
        this.position = position;
        this.expression = expression;
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

    public int getPosition() {
        return position;
    }

    @NotNull
    public String getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParseException that = (ParseException) o;

        if (position != that.position) return false;
        if (!expression.equals(that.expression)) return false;
        if (!message.equals(that.message)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + position;
        result = 31 * result + expression.hashCode();
        return result;
    }
}
