package jscl.text.msg;

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.msg.MessageType;

import java.util.Locale;

/**
 * User: serso
 * Date: 11/30/11
 * Time: 9:53 PM
 */
public class JsclMessageTest {

	@Test
	public void testTranslation() throws Exception {
		String localizedMessage = new JsclMessage(Messages.msg_1, MessageType.error).getLocalizedMessage(Locale.ENGLISH);
		//Assert.assertTrue(localizedMessage.startsWith("Parsing error "));
	}
}
