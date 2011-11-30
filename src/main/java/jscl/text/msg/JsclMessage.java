package jscl.text.msg;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.msg.AbstractMessage;
import org.solovyev.common.msg.MessageType;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: serso
 * Date: 11/26/11
 * Time: 11:20 AM
 */
public class JsclMessage extends AbstractMessage {

	public JsclMessage(@NotNull String messageCode,
						  @NotNull MessageType messageType,
						  @org.jetbrains.annotations.Nullable Object... parameters) {
		super(messageCode, messageType, parameters);
	}

	public JsclMessage(@NotNull String messageCode,
						  @NotNull MessageType messageType,
						  @NotNull List<?> parameters) {
		super(messageCode, messageType, parameters);
	}

	@Override
	protected String getMessagePattern(@NotNull Locale locale) {
		final ResourceBundle rb = ResourceBundle.getBundle("jscl/text/msg/messages", locale);
		return rb.getString(getMessageCode());
	}
}
