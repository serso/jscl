package jscl.math.numeric;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 5/14/12
 * Time: 2:24 PM
 */
public class ComplexTest {

    @Test
    public void testSmallImag() throws Exception {
        Assert.assertEquals("1+100E-18*i", Complex.valueOf(1, 0.0000000000000001).toString());
        Assert.assertEquals("1-100E-18*i", Complex.valueOf(1, -0.0000000000000001).toString());
    }
}
