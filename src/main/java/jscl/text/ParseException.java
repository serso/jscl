package jscl.text;

import jscl.text.msg.JsclMessage;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.msg.Message;
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
	public MessageType getMessageType() {
		return this.message.getMessageType();
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
}
