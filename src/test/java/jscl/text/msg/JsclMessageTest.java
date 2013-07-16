package jscl.text.msg;

import org.junit.Test;

import java.util.Locale;

import static java.util.Locale.ENGLISH;
import static jscl.text.msg.Messages.msg_1;
import static org.junit.Assert.assertTrue;
import static org.solovyev.common.msg.MessageType.error;

/**
 * User: serso
 * Date: 11/30/11
 * Time: 9:53 PM
 */
public class JsclMessageTest {

    @Test
    public void testTranslation() throws Exception {
        String localizedMessage = new JsclMessage(msg_1, error).getLocalizedMessage(ENGLISH);
        assertTrue(localizedMessage.startsWith("Parsing error "));
    }

	@Test
	public void testShouldContainPolishStrings() throws Exception {
		String localizedMessage = new JsclMessage(msg_1, error).getLocalizedMessage(new Locale("pl", "PL"));
		assertTrue(localizedMessage.startsWith("Wystąpił błąd "));
	}
}
