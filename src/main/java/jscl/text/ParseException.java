package jscl.text;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.utils.CollectionsUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParseException extends Exception {

	@NotNull
	private final String messageId;

	@NotNull
	private final List<Object> parameters;

	private final int position;

	@NotNull
	private final String expression;

	public ParseException(@NotNull String messageId, int position, @NotNull String expression, Object... parameters) {
		this.messageId = messageId;
		this.position = position;
		this.expression = expression;

		if (CollectionsUtils.isEmpty(parameters)) {
			this.parameters = Collections.emptyList();
		} else {
			this.parameters = Arrays.asList(parameters);
		}
	}

	@NotNull
	public String getMessageId() {
		return messageId;
	}

	@NotNull
	public List<Object> getParameters() {
		return parameters;
	}

	public int getPosition() {
		return position;
	}

	@NotNull
	public String getExpression() {
		return expression;
	}
}
